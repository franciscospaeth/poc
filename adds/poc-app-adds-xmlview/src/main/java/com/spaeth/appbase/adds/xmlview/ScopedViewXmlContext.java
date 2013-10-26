package com.spaeth.appbase.adds.xmlview;

import java.util.Map;

import org.w3c.dom.Element;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.model.Action;

public class ScopedViewXmlContext implements XmlViewContext {

	private String scopeName;
	private XmlViewContext context;

	public ScopedViewXmlContext(String scopeName, XmlViewContext context) {
		super();
		this.scopeName = scopeName;
		this.context = context;
	}

	public void register(String name, Object component) {
		context.register(scopeName + "." + name, component);
	}

	public Object get(String name) {
		String scopedName = scopeName + "." + name;

		if (context.getRegisterMap().containsKey(scopedName)) {

			return context.get(scopedName);

		}

		return context.get(name);
	}

	public <M> M get(String name, Class<M> expectedComponentClass) {
		return context.get(name, expectedComponentClass);
	}

	public boolean has(String name) {
		return context.has(name);
	}

	public Map<String, Object> getRegisterMap() {
		return context.getRegisterMap();
	}

	public Action getAction(String name) {
		return context.getAction(name);
	}

	public DataSource getDataSource(String dataSourcePath) {
		return context.getDataSource(dataSourcePath);
	}

	public Object translate(Element element) {
		return translate(element, this);
	}

	public <T> T translate(String resourceName, Class<T> expectedResultClass) {
		return translate(resourceName, expectedResultClass, this);
	}

	public Object translate(Element element, XmlViewContext context) {
		return this.context.translate(element, context);
	}

	public <T> T translate(String resourceName, Class<T> expectedResultClass, XmlViewContext context) {
		return this.context.translate(resourceName, expectedResultClass, context);
	}

}
