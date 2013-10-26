package com.spaeth.appbase.core.service;

import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.service.ConfigurationService;
import com.spaeth.appbase.service.ConfigurationServiceFacade;

public class DefaultConfigurationServiceFacade implements ConfigurationServiceFacade {

	private final ConfigurationService configurationService;

	public DefaultConfigurationServiceFacade(@Principal final ConfigurationService configurationService) {
		super();
		this.configurationService = configurationService;
	}

	@Override
	public <M> M getValue(final String name, final Class<M> expectedType) {
		return configurationService.getValue(name, expectedType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <M> M getValue(final String name, final M defaultValue) {
		if (defaultValue == null) {
			throw new IllegalArgumentException("defaultValue should not be null");
		}
		Object value = getValue(name, defaultValue.getClass());

		if (value == null) {
			return defaultValue;
		}

		return (M) value;
	}

}
