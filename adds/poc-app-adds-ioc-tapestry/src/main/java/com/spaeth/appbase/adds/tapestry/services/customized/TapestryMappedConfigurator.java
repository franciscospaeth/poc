package com.spaeth.appbase.adds.tapestry.services.customized;

import org.apache.tapestry5.ioc.MappedConfiguration;

public class TapestryMappedConfigurator<K, V> implements com.spaeth.appbase.core.service.Configurator.MappedConfiguration<K, V> {

	private final MappedConfiguration<K, V> mappedConfiguration;

	public TapestryMappedConfigurator(final MappedConfiguration<K, V> mappedConfiguration) {
		super();
		this.mappedConfiguration = mappedConfiguration;
	}

	@Override
	public void add(final K key, final V value) {
		this.mappedConfiguration.add(key, value);
	}

	@Override
	public void override(final K key, final V value) {
		this.mappedConfiguration.override(key, value);
	}

	@Override
	public void addInstance(final K key, final Class<? extends V> clazz) {
		this.mappedConfiguration.addInstance(key, clazz);
	}

	@Override
	public void overrideInstance(final K key, final Class<? extends V> clazz) {
		this.mappedConfiguration.overrideInstance(key, clazz);
	}

}
