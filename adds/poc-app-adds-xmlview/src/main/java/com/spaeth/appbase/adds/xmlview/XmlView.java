package com.spaeth.appbase.adds.xmlview;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.ON_INITIALIZE_ACTION_ELEMENT_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.ON_SHOW_ACTION_ELEMENT_NAME;

import java.lang.reflect.Field;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.adds.xmlview.annotation.ShareFromContext;
import com.spaeth.appbase.adds.xmlview.annotation.ShareToContext;
import com.spaeth.appbase.adds.xmlview.annotation.ViewResourceLocation;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewException;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewInitializationException;
import com.spaeth.appbase.adds.xmlview.service.XmlViewContextFactory;
import com.spaeth.appbase.component.ComponentContainer;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.core.model.DefaultActionParameters;
import com.spaeth.appbase.core.view.AbstractView;
import com.spaeth.appbase.event.ViewShowEvent;
import com.spaeth.appbase.model.Action;
import com.spaeth.appbase.model.StartupInfo;
import com.spaeth.appbase.model.ViewModel;

public class XmlView extends AbstractView<ComponentContainer> {

	private static final long serialVersionUID = 1L;

	private static final String LOGGER_NAME = "logger";
	private static final String VIEW_NAME = "view";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private XmlViewContext xmlViewContext;

	@Inject
	private XmlViewContextFactory xmlViewContextFactory;

	private ViewLayout component;

	private final String viewLocation;

	public XmlView() {
		ViewResourceLocation annotation = this.getClass().getAnnotation(ViewResourceLocation.class);

		if (annotation == null) {
			throw new NullPointerException("no view resource location defined for class " + this.getClass());
		}

		viewLocation = annotation.value();

	}

	public XmlView(final String viewLocation) {
		this.viewLocation = viewLocation;
	}

	@Override
	public ComponentContainer getContent() {
		return component;
	}

	private void translateContent(final String viewLocation) {

		if (this.component != null) {
			return;
		}

		try {

			this.component = xmlViewContext.translate(viewLocation, ViewLayout.class);

		} catch (Exception e) {
			XmlViewInitializationException exception = new XmlViewInitializationException(String.format(
					"not able to load xml view from '%s'", viewLocation), e);
			logger.error(exception.getMessage(), exception);
			throw exception;
		}

		if (xmlViewContext.has(ON_INITIALIZE_ACTION_ELEMENT_NAME)) {

			try {

				Action onInit = xmlViewContext.get(ON_INITIALIZE_ACTION_ELEMENT_NAME, Action.class);

				if (onInit != null) {
					onInit.execute(new DefaultActionParameters.Builder(this.component).build());
				}

			} catch (Exception e) {
				XmlViewInitializationException exception = new XmlViewInitializationException("error executing initialization action", e);
				logger.error(exception.getMessage(), exception);
				throw exception;
			}

		}

		sharePossibleFieldsFromContext();

	}

	@Override
	public void initialize(final ViewModel viewModel, final StartupInfo startupInfo) {
		super.initialize(viewModel, startupInfo);

		xmlViewContext = xmlViewContextFactory.createContext(viewModel);

		registerProvidedComponents();

		translateContent(viewLocation);
	}

	private void registerProvidedComponents() {
		xmlViewContext.register(LOGGER_NAME, LoggerFactory.getLogger(String.format("JSViewLogger[%s]", this)));
		xmlViewContext.register(VIEW_NAME, this);
		registerPersonalizedProvidedComponents();
	}

	private void sharePossibleFieldsFromContext() {
		Class<?> c = this.getClass();
		while (c != XmlView.class) {
			sharePossibleFieldsFromContext(c);
			c = c.getSuperclass();
		}
	}

	private void sharePossibleFieldsFromContext(final Class<?> c) {
		for (Field f : c.getDeclaredFields()) {
			ShareFromContext ann = f.getAnnotation(ShareFromContext.class);
			if (ann == null) {
				logger.debug("field '{}' from class '{}' has no annotation", f.getName(), c);
				continue;
			}
			if (!f.isAccessible()) {
				f.setAccessible(true);
			}
			String name = StringUtils.trimToNull(ann.value());
			name = StringUtils.defaultString(name, f.getName());
			try {
				f.set(this, xmlViewContext.get(name));
			} catch (Exception e) {
				logger.error("error sharing field '{}' from context '{}' due to: {}", new Object[] { f, name, e.getMessage() }, e);
				throw new XmlViewException(String.format("not able to share field '%s' from context '%s'", f.getName(), name), e);
			}
		}
	}

	protected void registerPersonalizedProvidedComponents() {
		Class<?> c = this.getClass();
		while (c != XmlView.class) {
			logger.debug("sharing fields for class '{}'", c);
			sharePossibleFieldsToContext(c);
			c = c.getSuperclass();
		}
		logger.debug("shared all fields");
	}

	private void sharePossibleFieldsToContext(final Class<?> c) {
		for (Field f : c.getDeclaredFields()) {
			ShareToContext ann = f.getAnnotation(ShareToContext.class);
			if (ann == null) {
				logger.debug("field '{}' from class '{}' has no annotation", f.getName(), c);
				continue;
			}
			if (!f.isAccessible()) {
				f.setAccessible(true);
			}
			String name = StringUtils.trimToNull(ann.value());
			name = StringUtils.defaultString(name, f.getName());
			try {
				xmlViewContext.register(name, f.get(this));
			} catch (Exception e) {
				logger.error("error registering field '{}' from class '{}' to context due to: {}",
						new Object[] { name, f, e.getMessage() }, e);
				throw new XmlViewException(String.format("not able to share field '%s' from class '%s' to context", f.getName(), c), e);
			}
		}
	}

	@Override
	public void onShown(final ViewShowEvent event) {
		super.onShown(event);
		if (xmlViewContext.has(ON_SHOW_ACTION_ELEMENT_NAME)) {

			try {

				Action onShow = xmlViewContext.get(ON_SHOW_ACTION_ELEMENT_NAME, Action.class);

				if (onShow != null) {
					onShow.execute(new DefaultActionParameters.Builder(this.component).build());
				}

			} catch (Exception e) {
				logger.error(String.format("error when executing on show action of view due to ", e.getMessage()), e);
			}

		}
	}

	protected XmlViewContext decorateBuiltXmlContext(final XmlViewContext viewXmlContext) {
		return viewXmlContext;
	}

	public String getTitle() {
		return component.getTitle();
	}

	public boolean isClosable() {
		return component.isClosable();
	}

	public StreamProvider getIcon() {
		return component.getIcon();
	}

	public void setXmlViewContextFactory(final XmlViewContextFactory xmlViewContextFactory) {
		this.xmlViewContextFactory = xmlViewContextFactory;
	}

}
