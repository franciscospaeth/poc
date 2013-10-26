package com.spaeth.appbase.services.tapestry;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.spaeth.appbase.core.service.Module;
import com.spaeth.appbase.core.service.Repository;
import com.spaeth.appbase.core.service.RepositoryBuilder;
import com.spaeth.reflection.ModuleWithBinding;

public class RepositoryBuilderTest {

	@Test
	public void testRepositoryBuilder() {
		final List<Class<? extends Module>> modules = new ArrayList<Class<? extends Module>>();
		modules.add(ModuleWithBinding.class);
		final Repository repository = RepositoryBuilder.build(modules);
		Assert.assertNotNull(repository);
	}

}
