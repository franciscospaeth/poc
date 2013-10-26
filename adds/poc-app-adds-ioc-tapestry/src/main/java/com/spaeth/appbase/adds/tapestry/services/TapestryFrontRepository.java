package com.spaeth.appbase.adds.tapestry.services;

import org.apache.tapestry5.ioc.Registry;

import com.spaeth.appbase.adds.tapestry.services.customized.TapestryRepository;
import com.spaeth.appbase.core.service.FrontRepository;

public class TapestryFrontRepository extends TapestryRepository implements FrontRepository {

	private final Registry registry;

	public TapestryFrontRepository(final Registry registry) {
		super(registry);
		this.registry = registry;
	}

	@Override
	public void startup() {
		this.registry.performRegistryStartup();
	}

	@Override
	public void shutdown() {
		this.registry.shutdown();
	}

}
