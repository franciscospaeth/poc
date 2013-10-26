package com.spaeth.appbase.adds.tapestry.services.customized;

import org.apache.tapestry5.ioc.OrderedConfiguration;

public class TapestryOrderedConfigurator<T> implements com.spaeth.appbase.core.service.Configurator.OrderedConfiguration<T> {

	private final OrderedConfiguration<T> delegated;

	public TapestryOrderedConfigurator(final OrderedConfiguration<T> delegated) {
		super();
		this.delegated = delegated;
	}

	@Override
	public void add(final String id, final T object, final String... constraints) {
		this.delegated.add(id, object, constraints);
	}

	@Override
	public void override(final String id, final T object, final String... constraints) {
		this.delegated.override(id, object, constraints);
	}

	@Override
	public void addInstance(final String id, final Class<? extends T> clazz, final String... constraints) {
		this.delegated.addInstance(id, clazz, constraints);
	}

	@Override
	public void overrideInstance(final String id, final Class<? extends T> clazz, final String... constraints) {
		this.delegated.overrideInstance(id, clazz, constraints);
	}

}
