package com.spaeth.appbase.adds.xmlview;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.exception.XmlViewComponentNameAlreadyRegisteredException;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewComponentNotFoundException;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewComponentTypeNotExpectedException;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewException;
import com.spaeth.appbase.adds.xmlview.service.FrontXmlTranslator;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.model.Action;
import com.spaeth.appbase.model.ViewModel;
import com.spaeth.appbase.service.ActionProvider;
import com.spaeth.appbase.service.ResourceLoader;

public class DefaultViewXmlContext implements XmlViewContext {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Map<String, Object> componentsRegistry = new HashMap<String, Object>();
	private final ActionProvider actionProvider;
	private final ViewModel viewModel;
	private final FrontXmlTranslator frontXmlTranslator;
	private final ResourceLoader resourceLoader;

	public DefaultViewXmlContext(final FrontXmlTranslator frontXmlTranslator, final ActionProvider actionProvider,
			final ViewModel viewModel, final ResourceLoader resourceLoader) {
		super();
		this.actionProvider = actionProvider;
		this.viewModel = viewModel;
		this.frontXmlTranslator = frontXmlTranslator;
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void register(final String name, final Object component) {

		if (StringUtils.isEmpty(name)) {
			throw new XmlViewException(String.format("name of component '%s' should not be empty", component));
		}

		if (componentsRegistry.containsKey(name)) {
			throw new XmlViewComponentNameAlreadyRegisteredException(name);
		}

		logger.debug("register to '{}' object named '{}'", this, name);

		componentsRegistry.put(name, component);
	}

	@Override
	public Object get(String name) {
		if (name.startsWith("#")) {
			name = name.substring(1);
		}

		Object result = componentsRegistry.get(name);

		if (result == null) {
			throw new XmlViewComponentNotFoundException(name);
		}

		return result;
	}

	@Override
	public <M> M get(final String name, final Class<M> expectedComponentClass) {
		Object result = get(name);

		if (expectedComponentClass.isInstance(result)) {
			return expectedComponentClass.cast(result);
		}

		throw new XmlViewComponentTypeNotExpectedException(name, expectedComponentClass, result.getClass());
	}

	@Override
	public boolean has(final String name) {
		return componentsRegistry.containsKey(name);
	}

	@Override
	public Map<String, Object> getRegisterMap() {
		return Collections.unmodifiableMap(componentsRegistry);
	}

	@Override
	public Action getAction(final String name) {
		if (componentsRegistry.containsKey(name)) {
			Object suposeAction = componentsRegistry.get(name);
			if (suposeAction instanceof Action) {
				return (Action) suposeAction;
			}
		}

		if (actionProvider == null) {
			return null;
		}

		return actionProvider.getAction(name);
	}

	@Override
	public DataSource getDataSource(final String dataSourcePath) {
		if (viewModel == null) {
			logger.warn("no model was bounded to {}, so getDataSource needs to return null for {}", this, dataSourcePath);
			return null;
		}

		DataSource ds = viewModel.getDataSource();
		String[] split = StringUtils.splitByWholeSeparatorPreserveAllTokens(dataSourcePath, ".");
		return ds.getDataSource(split);
	}

	@Override
	public Object translate(final Element element) {
		return translate(element, this);
	}

	@Override
	public <T> T translate(final String resourceName, final Class<T> expectedResultClass) {
		return translate(resourceName, expectedResultClass, this);
	}

	@Override
	public Object translate(final Element element, final XmlViewContext context) {
		return frontXmlTranslator.translate(element, context);
	}

	@Override
	public <T> T translate(final String resourceName, final Class<T> expectedResultClass, final XmlViewContext context) {
		InputStream resource = resourceLoader.getResource(resourceName);

		if (resource == null) {
			throw new IllegalStateException("view resource could not be loaded");
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setCoalescing(true);

		Document doc = null;
		try {
			// to load the resource
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(resource);
		} catch (Exception e) {
			throw new XmlViewException(String.format("not able to load resource '%s' due to: %s", resource, e.getMessage()), e);
		}

		Element rootElement = doc.getDocumentElement();
		rootElement.normalize();

		Object component = translate(rootElement, context);

		if (!(expectedResultClass.isInstance(component))) {
			XmlViewComponentTypeNotExpectedException exception = new XmlViewComponentTypeNotExpectedException(rootElement.getNodeName(),
					expectedResultClass, component.getClass());

			logger.error(exception.getMessage(), exception);

			throw exception;
		}

		return expectedResultClass.cast(component);
	}

}
