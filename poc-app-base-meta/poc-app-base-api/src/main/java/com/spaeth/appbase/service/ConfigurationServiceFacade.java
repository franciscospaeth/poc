package com.spaeth.appbase.service;

public interface ConfigurationServiceFacade {

	<M> M getValue(String name, Class<M> expectedType);
	
	<M> M getValue(String name, M defaultValue);
	
}
