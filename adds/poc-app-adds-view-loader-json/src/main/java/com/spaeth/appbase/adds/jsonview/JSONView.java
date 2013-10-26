package com.spaeth.appbase.adds.jsonview;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.adds.jsonview.assembling.JSONContextBuilder;
import com.spaeth.appbase.adds.jsonview.assembling.JSONPropertyNameConvention;
import com.spaeth.appbase.adds.jsonview.assembling.JSONSourceIOException;
import com.spaeth.appbase.adds.jsonview.assembling.JSONViewException;
import com.spaeth.appbase.core.ViewPort;
import com.spaeth.appbase.core.model.View;
import com.spaeth.appbase.core.model.ViewModel;
import com.spaeth.appbase.core.view.assembling.Context;

public abstract class JSONView<ResultContentType> implements View<ResultContentType> {

	private static final String SELF = "self";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private JSONContextBuilder contextBuilder;
	private Context<JSONObject> context = null;
	private JSONObject representation;
	private ResultContentType content;
	private InputStream inputStream;

	@Override
	public void initialize(final ViewModel viewModel) {
		// loads the given view
		try {
			this.representation = loadRepresentation();
		} catch (final JSONException e) {
			throw new JSONViewException("Not able to load json resource for " + this);
		}

		// create the view
		createContentIfNeeded(viewModel);

		// now load the properties personalized for each view / application
		try {
			loadPersonalizedProperties(this.representation);
		} catch (final JSONException e) {
			throw new JSONViewException("Not able to load json resource for " + this);
		}
	}

	private JSONObject loadRepresentation() throws JSONException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int read = 0;
		try {
			this.inputStream = getRepresentationInputStream();
			while ((read = this.inputStream.read()) != -1) {
				baos.write(read);
			}
		} catch (final IOException e) {
			throw new JSONSourceIOException("Not able to read json source.");
		}
		return new JSONObject(baos.toString());
	}

	protected InputStream getRepresentationInputStream() {
		InputStream result = null;

		final JSONViewResourceName annotation = getClass().getAnnotation(JSONViewResourceName.class);
		if (annotation != null) {
			result = JSONResourceLoader.getResource(annotation.value());
		}

		if (result == null) {
			throw new IllegalStateException("not able to get representation for class " + getClass().getSimpleName());
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private void createContentIfNeeded(final ViewModel viewModel) {
		if (this.content == null) {
			if (this.context == null) {
				this.context = this.contextBuilder.build(this.representation, viewModel);
			}

			// add local properties to context
			Collection<String> presetsName = addPresetsToContext();
			
			// loads the context objects
			if (this.representation.has(JSONPropertyNameConvention.REFERENCEABLE_OBJECTS)) {
				loadReferenceableObjects();
			}
			
			// interpret json to build view
			this.content = (ResultContentType) this.context.interpret(this.representation);
			
			// cnfigures properties of view with interpreted json result
			loadPropertiesBasedOnJson(presetsName);
			
		}
	}

	private void loadPropertiesBasedOnJson(Collection<String> presetsName) {
		for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(this)) {
			String name = pd.getName();
			if (pd.getWriteMethod() == null || pd.getWriteMethod().getDeclaringClass() == JSONView.class) {
				continue;
			}

			Object value = context.getContextObjects().get(name);
			
			try {
				PropertyUtils.setProperty(this, name, value);
			} catch (Exception e) {
				logger.error(String.format("error configuring property of name '%s' in view '%s': %s", name, this.getClass(), e.getMessage()));
			}
		}
	}

	private Collection<String> addPresetsToContext() {
		Set<String> configuredNames = new HashSet<String>();
		for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(this)) {
			String name = pd.getName();
			if (pd.getReadMethod() == null) {
				continue;
			}
			try {
				context.addContextObject(name, PropertyUtils.getProperty(this, name));
			} catch (Exception e) {
				logger.warn(String.format("not able to add property '%s' from view to context objects", name));
			}
			configuredNames.add(name);
		}
		context.addContextObject(SELF, this);
		configuredNames.add(SELF);
		return configuredNames;
	}

	protected void loadPersonalizedProperties(final JSONObject representation) throws JSONException {
	}

	@Override
	public ResultContentType getContent() {
		return this.content;
	}

	private void loadReferenceableObjects() {
		try {
			final JSONArray objectCollection = this.representation
					.getJSONArray(JSONPropertyNameConvention.REFERENCEABLE_OBJECTS);
			for (int i = 0; i < objectCollection.length(); i++) {
				final JSONObject jsonObject = objectCollection.getJSONObject(i);
				if (!jsonObject.has(JSONPropertyNameConvention.NAME_PROPERTY)) {
					throw new IllegalArgumentException("Referenceable objects should present always an name.");
				}
				final Object object = this.context.interpret(jsonObject);
				final String name = jsonObject.getString(JSONPropertyNameConvention.NAME_PROPERTY);
				this.context.addContextObject(name, object);
			}
		} catch (final Exception e) {
			throw new JSONViewException(e);
		}
	}

	@Override
	public void onShown(final ViewPort shownBy) {
	}

	public void setContextBuilder(final JSONContextBuilder contextBuilder) {
		this.contextBuilder = contextBuilder;
	}

}
