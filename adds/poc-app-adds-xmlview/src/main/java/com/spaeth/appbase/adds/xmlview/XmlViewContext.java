package com.spaeth.appbase.adds.xmlview;

import java.util.Map;

import org.w3c.dom.Element;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.model.Action;

public interface XmlViewContext {

	void register(String name, Object component);
	
	Object get(String name);
	
	<M> M get(String name, Class<M> expectedClass);
	
	boolean has(String name);
	
	Map<String, Object> getRegisterMap();
	
	Action getAction(String name);
	
	DataSource getDataSource(String dataSourcePath);
	
	Object translate(Element element);
	
	<T> T translate(String resourceName, Class<T> expectedResultClass);
	
	Object translate(Element element, XmlViewContext context);
	
	<T> T translate(String resourceName, Class<T> expectedResultClass, XmlViewContext context);
	
}
