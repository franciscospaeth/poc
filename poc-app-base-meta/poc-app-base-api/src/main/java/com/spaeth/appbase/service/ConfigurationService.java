package com.spaeth.appbase.service;

public interface ConfigurationService {

	<M> M getValue(String name, Class<M> expectedType);
	
}
