package com.spaeth.appbase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.spaeth.appbase.adds.swing.SwingModule;
import com.spaeth.appbase.core.AppBaseModule;
import com.spaeth.appbase.core.service.FrontRepository;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.appbase.core.service.RepositoryBuilder;

public class SwingExecutor {

	public static void main(final String[] args) {
		// load repository based on modules provided as parameter
		FrontRepository repo = RepositoryBuilder.build(parseModulesClasses(System.getProperty("poc.modules")));

		// initialize application
		repo.getInstance(Application.class).initialize();
	}

	@SuppressWarnings("unchecked")
	private static Collection<Class<? extends Module>> parseModulesClasses(final String modules) {
		if (modules == null) {
			throw new IllegalStateException("no module to be load, are you sure modules parameter was configured?");
		}

		final String[] classes = modules.split(",");

		final List<Class<? extends Module>> result = new ArrayList<Class<? extends Module>>();
		result.add(AppBaseModule.class);
		result.add(SwingModule.class);

		for (final String str : classes) {
			try {
				result.add((Class<? extends Module>) Class.forName(str.trim()));
			} catch (final ClassNotFoundException e) {
				throw new IllegalArgumentException("not able to load configured module '" + str.trim() + "' due to " + e.getMessage(), e);
			}
		}

		return result;
	}

}
