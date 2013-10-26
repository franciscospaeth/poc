package com.spaeth.appbase.core.service;

import com.spaeth.appbase.service.ConfigurationService;

public class SystemPropertyConfigurationService implements ConfigurationService {

	@Override
	public <M> M getValue(String name, Class<M> expectedType) {
		String value = System.getProperty(name);
		if (value == null) {
			return null;
		}
		return expectedType.cast(value);
	}

}
