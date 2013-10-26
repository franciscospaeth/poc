package com.spaeth.appbase.adds.tapestry.services.processor;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.spaeth.appbase.core.annotations.Requires;
import com.spaeth.appbase.core.service.Module;

public class ModuleRequiresProcessor {

	public Set<Module> process(final Collection<Class<? extends Module>> modules) {

		// create a class collection to check which modules are really needed
		Set<Class<? extends Module>> required = processRequirements(modules);

		// result collection
		Set<Module> result = new HashSet<Module>();

		// instantiate each module
		for (final Class<? extends Module> mc : required) {
			if (mc == null) {
				throw new IllegalStateException("no way to load a null module");
			}
			try {
				result.add(mc.newInstance());
			} catch (Exception e) {
				throw new RuntimeException("not able to instantiate module " + mc + " due to: " + e.getMessage()
						+ ", be sure you have a default constructor for this module", e);
			}
		}

		return result;

	}

	protected Set<Class<? extends Module>> processRequirements(final Collection<Class<? extends Module>> modules) {
		Set<Class<? extends Module>> required = new HashSet<Class<? extends Module>>(modules);

		for (final Class<? extends Module> mc : modules) {
			Requires requires = mc.getAnnotation(Requires.class);
			if (requires != null) {
				required.addAll(processRequirements(Arrays.asList(requires.value())));
			}
		}
		return required;
	}

}
