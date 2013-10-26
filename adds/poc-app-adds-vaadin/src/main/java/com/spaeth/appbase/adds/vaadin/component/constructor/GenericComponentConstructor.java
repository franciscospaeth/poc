package com.spaeth.appbase.adds.vaadin.component.constructor;

import org.apache.commons.lang3.reflect.ConstructorUtils;

public class GenericComponentConstructor implements ComponentConstructor {

	private Class<?> supportedClass;
	
	public GenericComponentConstructor(Class<?> supportedClass) {
		this.supportedClass = supportedClass;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <M> M create(Object... parameters) {
		try {
			return (M) ConstructorUtils.invokeConstructor(supportedClass, parameters);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("%s could not be created due to: %s", supportedClass, e.getMessage()), e);
		}
	}
	
}
