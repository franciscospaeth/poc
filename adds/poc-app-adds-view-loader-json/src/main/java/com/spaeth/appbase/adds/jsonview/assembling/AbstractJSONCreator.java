package com.spaeth.appbase.adds.jsonview.assembling;

import static com.spaeth.appbase.adds.jsonview.assembling.JSONPropertyNameConvention.TYPE_PROPERTY;

import org.json.JSONException;
import org.json.JSONObject;

import com.spaeth.appbase.core.view.assembling.AbstractCreator;
import com.spaeth.appbase.core.view.assembling.Context;

public abstract class AbstractJSONCreator extends AbstractCreator<JSONObject> {

	public AbstractJSONCreator() {
		super();
	}

	public AbstractJSONCreator(final AbstractJSONCreator nextCreator) {
		super(nextCreator);
	}

	@Override
	public Object executeCreation(final JSONObject representation, final Context<JSONObject> creatorContext) {
		if (!representation.has(TYPE_PROPERTY)) {
			throw new IllegalArgumentException("No type defined for representation " + representation);
		}
		try {
			final String type = representation.getString(TYPE_PROPERTY);
			Class<?> classUse = creatorContext.getUsedClass(type);
			if (classUse == null) {
				classUse = Class.forName(type);
			}
			if (handle(classUse)) {
				return executeCreation(classUse, representation, creatorContext);
			}
			return null;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract boolean handle(Class<?> classToHandle);

	protected abstract Object executeCreation(Class<?> classToCreate, final JSONObject representation,
			final Context<JSONObject> creatorContext) throws JSONException;

}