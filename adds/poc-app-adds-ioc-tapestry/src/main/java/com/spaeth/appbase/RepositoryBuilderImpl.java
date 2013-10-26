package com.spaeth.appbase;

import java.util.Collection;

import com.spaeth.appbase.adds.tapestry.services.TapestryFrontRepository;
import com.spaeth.appbase.adds.tapestry.services.TapestryReflectionRegistryBuilder;
import com.spaeth.appbase.core.service.FrontRepository;
import com.spaeth.appbase.core.service.Module;

public class RepositoryBuilderImpl {

	public static FrontRepository build(final Collection<Class<? extends Module>> modules) {
		return new TapestryFrontRepository(TapestryReflectionRegistryBuilder.buildAndStartup(modules));
	}

}
