package com.spaeth.appbase.component.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentFactoryProvider {

	private static Logger logger = LoggerFactory.getLogger(ComponentFactoryProvider.class);
	
	private static ComponentFactory componentFactory;

	private ComponentFactoryProvider() {
	}

	public static void configure(ComponentFactory componentFactory) {
		if (ComponentFactoryProvider.componentFactory != null) {
			IllegalStateException ex = new IllegalStateException(String.format(
					"a component factory (%s) was already registered while trying to set %s",
					ComponentFactoryProvider.componentFactory.getClass(), componentFactory.getClass()));
			logger.error(ex.getMessage(), ex);
			throw ex;
		}

		if (componentFactory == null) {
			throw new IllegalArgumentException("component factory should not be set to null");
		}
		
		logger.info("registered component factory: '{}'", componentFactory.getClass());
		ComponentFactoryProvider.componentFactory = componentFactory;
	}

	public static ComponentFactory getComponentFactory() {
		if (componentFactory == null) {
			throw new IllegalArgumentException("no component factory was configured");
		}
		return componentFactory;
	}

}
