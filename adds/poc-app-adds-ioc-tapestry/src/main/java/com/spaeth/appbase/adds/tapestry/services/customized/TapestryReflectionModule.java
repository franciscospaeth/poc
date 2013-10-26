package com.spaeth.appbase.adds.tapestry.services.customized;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.tapestry5.ioc.def.ContributionDef;
import org.apache.tapestry5.ioc.def.DecoratorDef;
import org.apache.tapestry5.ioc.def.ModuleDef;
import org.apache.tapestry5.ioc.def.ServiceDef;
import org.apache.tapestry5.ioc.internal.ServiceDefAccumulator;

import com.spaeth.appbase.core.service.Module;

public class TapestryReflectionModule implements ModuleDef, ServiceDefAccumulator {

	private final Map<String, ServiceDef> serviceDefs = new HashMap<String, ServiceDef>();
	private final Class<? extends Module> moduleClass;
	private final Set<ContributionDef> contributions = new HashSet<ContributionDef>();

	public TapestryReflectionModule(final Class<? extends Module> moduleClass) {
		super();
		this.moduleClass = moduleClass;
	}

	@Override
	public void addServiceDef(final ServiceDef serviceDef) {
		this.serviceDefs.put(serviceDef.getServiceId(), serviceDef);
	}

	@Override
	public Set<String> getServiceIds() {
		return serviceDefs.keySet();
	}

	@Override
	public ServiceDef getServiceDef(final String serviceId) {
		return serviceDefs.get(serviceId);
	}

	@Override
	public Set<DecoratorDef> getDecoratorDefs() {
		return Collections.emptySet();
	}

	@Override
	public Set<ContributionDef> getContributionDefs() {
		return contributions;
	}

	@Override
	public Class<?> getBuilderClass() {
		return moduleClass;
	}

	@Override
	public String getLoggerName() {
		return String.valueOf(this.moduleClass.getClass());
	}

	public void addContributionDef(final ContributionDef def) {
		this.contributions.add(def);
	}

}
