package com.spaeth.appbase.core.service;

import java.util.Map;

import com.spaeth.appbase.service.ConfigurationService;

public class DefaultConfigurationService implements ConfigurationService {

	private Map<String, Object> values;

	public DefaultConfigurationService(Map<String, Object> values) {
		super();
		this.values = values;
	}

	@Override
	public <M> M getValue(String name, Class<M> expectedType) {
		Object value = values.get(name);
		if (value == null) {
			return null;
		}
		return expectedType.cast(value);
	}

}
