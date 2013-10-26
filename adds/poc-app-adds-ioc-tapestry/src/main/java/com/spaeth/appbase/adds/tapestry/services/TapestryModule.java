package com.spaeth.appbase.adds.tapestry.services;

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

public class TapestryModule implements ModuleDef, ServiceDefAccumulator {

	private final Map<String, ServiceDef> serviceDefs = new HashMap<String, ServiceDef>();
	private final Set<ContributionDef> contributions = new HashSet<ContributionDef>();
	private final Set<DecoratorDef> decorators = new HashSet<DecoratorDef>();
	private final Class<? extends Module> module;

	public TapestryModule(final Class<? extends Module> module) {
		this.module = module;
	}

	@Override
	public Set<String> getServiceIds() {
		return this.serviceDefs.keySet();
	}

	@Override
	public ServiceDef getServiceDef(final String serviceId) {
		return this.serviceDefs.get(serviceId);
	}

	@Override
	public Set<DecoratorDef> getDecoratorDefs() {
		return this.decorators;
	}

	@Override
	public Set<ContributionDef> getContributionDefs() {
		return this.contributions;
	}

	@Override
	public Class<?> getBuilderClass() {
		return null;
	}

	@Override
	public String getLoggerName() {
		return String.valueOf(this.module.getClass());
	}

	@Override
	public void addServiceDef(final ServiceDef serviceDef) {
		this.serviceDefs.put(serviceDef.getServiceId(), serviceDef);
	}

}
