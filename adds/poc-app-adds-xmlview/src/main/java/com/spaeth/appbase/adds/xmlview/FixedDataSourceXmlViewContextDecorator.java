package com.spaeth.appbase.adds.xmlview;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.model.Action;

public class FixedDataSourceXmlViewContextDecorator implements XmlViewContext {

	private XmlViewContext delegated;
	private DataSource dataSource;

	public FixedDataSourceXmlViewContextDecorator(XmlViewContext delegated,
			DataSource dataSource) {
		super();
		this.delegated = delegated;
		this.dataSource = dataSource;
	}

	public void register(String name, Object component) {
		delegated.register(name, component);
	}

	public Object get(String name) {
		return delegated.get(name);
	}

	public <M> M get(String name, Class<M> expectedClass) {
		return delegated.get(name, expectedClass);
	}

	public boolean has(String name) {
		return delegated.has(name);
	}

	public Map<String, Object> getRegisterMap() {
		return delegated.getRegisterMap();
	}

	public Action getAction(String name) {
		return delegated.getAction(name);
	}

	public DataSource getDataSource(String dataSourcePath) {
		return dataSource.getDataSource(StringUtils.splitByWholeSeparatorPreserveAllTokens(dataSourcePath, "."));
	}

	public Object translate(Element element) {
		return delegated.translate(element, this);
	}

	public <T> T translate(String resourceName, Class<T> expectedResultClass) {
		return delegated.translate(resourceName, expectedResultClass);
	}

	public Object translate(Element element, XmlViewContext context) {
		return delegated.translate(element, context);
	}

	public <T> T translate(String resourceName, Class<T> expectedResultClass,
			XmlViewContext context) {
		return delegated.translate(resourceName, expectedResultClass, context);
	}

}
