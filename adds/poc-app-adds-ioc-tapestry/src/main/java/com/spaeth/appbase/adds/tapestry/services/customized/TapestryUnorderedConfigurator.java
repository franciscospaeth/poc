package com.spaeth.appbase.adds.tapestry.services.customized;

import org.apache.tapestry5.ioc.Configuration;

import com.spaeth.appbase.core.service.Configurator.UnorderedConfiguration;

public class TapestryUnorderedConfigurator<T> implements UnorderedConfiguration<T> {

	private final Configuration<T> delegated;

	public TapestryUnorderedConfigurator(final Configuration<T> delegated) {
		super();
		this.delegated = delegated;
	}

	@Override
	public void add(final T object) {
		this.delegated.add(object);
	}

	@Override
	public void addInstance(final Class<? extends T> clazz) {
		this.delegated.addInstance(clazz);
	}

}
