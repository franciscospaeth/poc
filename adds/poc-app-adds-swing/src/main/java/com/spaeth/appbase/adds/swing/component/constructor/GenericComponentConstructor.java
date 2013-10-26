package com.spaeth.appbase.adds.swing.component.constructor;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import com.spaeth.appbase.adds.swing.component.constructor.ComponentConstructor;

public class GenericComponentConstructor implements ComponentConstructor {

	private final Class<?> supportedClass;

	public GenericComponentConstructor(final Class<?> supportedClass) {
		this.supportedClass = supportedClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <M> M create(final Object... parameters) {
		try {
			return (M) ConstructorUtils.invokeConstructor(supportedClass, parameters);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("%s could not be created due to: %s", supportedClass,
					e.getMessage()), e);
		}
	}

}
