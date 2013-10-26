// Copyright 2006, 2007, 2008, 2009, 2010, 2011 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.spaeth.appbase.adds.tapestry.services.customized;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ModuleBuilderSource;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceResources;
import org.apache.tapestry5.ioc.def.ContributionDef2;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.ioc.internal.util.DelegatingInjectionResources;
import org.apache.tapestry5.ioc.internal.util.InjectionResources;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.internal.util.MapInjectionResources;
import org.apache.tapestry5.ioc.internal.util.WrongConfigurationTypeGuard;
import org.apache.tapestry5.ioc.services.PlasticProxyFactory;
import org.slf4j.Logger;

import com.spaeth.appbase.core.service.Configurator.UnorderedConfiguration;
import com.spaeth.appbase.core.service.Module;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ContributionDefImpl implements ContributionDef2 {

	private final Method contributorMethod;

	private final PlasticProxyFactory proxyFactory;

	private final Set<Class> markers;

	private final Class serviceInterface;

	private final Module module;

	private static final Class[] CONFIGURATION_TYPES = new Class[] { Configuration.class, MappedConfiguration.class,
			OrderedConfiguration.class };

	public ContributionDefImpl(final Module module, final Method contributorMethod, final PlasticProxyFactory proxyFactory,
			final Class serviceInterface, final Set<Class> markers) {
		this.module = module;
		this.contributorMethod = contributorMethod;
		this.proxyFactory = proxyFactory;
		this.serviceInterface = serviceInterface;
		this.markers = markers;
	}

	@Override
	public String toString() {
		return InternalUtils.asString(contributorMethod, proxyFactory);
	}

	@Override
	public String getServiceId() {
		return null;
	}

	@Override
	public void contribute(final ModuleBuilderSource moduleSource, final ServiceResources resources, final Configuration configuration) {
		invokeMethod(resources, UnorderedConfiguration.class, new TapestryUnorderedConfigurator(configuration));
	}

	@Override
	public void contribute(final ModuleBuilderSource moduleSource, final ServiceResources resources,
			final OrderedConfiguration configuration) {
		invokeMethod(resources, com.spaeth.appbase.core.service.Configurator.OrderedConfiguration.class, new TapestryOrderedConfigurator(
				configuration));
	}

	@Override
	public void contribute(final ModuleBuilderSource moduleSource, final ServiceResources resources, final MappedConfiguration configuration) {
		invokeMethod(resources, com.spaeth.appbase.core.service.Configurator.MappedConfiguration.class, new TapestryMappedConfigurator(
				configuration));
	}

	private <T> void invokeMethod(final ServiceResources resources, final Class<T> parameterType, final T parameterValue) {
		Map<Class, Object> resourceMap = CollectionFactory.newMap();

		resourceMap.put(parameterType, parameterValue);
		resourceMap.put(ObjectLocator.class, resources);
		resourceMap.put(Logger.class, resources.getLogger());

		InjectionResources injectionResources = new MapInjectionResources(resourceMap);

		// For each of the other configuration types that is not expected, add a
		// guard.

		for (Class t : CONFIGURATION_TYPES) {
			if (parameterType != t) {
				injectionResources = new DelegatingInjectionResources(new WrongConfigurationTypeGuard(resources.getServiceId(), t,
						parameterType), injectionResources);
			}
		}

		Throwable fail = null;

		try {
			Object[] parameters = InternalUtils.calculateParametersForMethod(contributorMethod, resources, injectionResources,
					resources.getTracker());

			contributorMethod.invoke(module, parameters);
		} catch (InvocationTargetException ex) {
			fail = ex.getTargetException();
		} catch (Exception ex) {
			fail = ex;
		}

		if (fail != null) {
			throw new RuntimeException("not able to perform contribution due to " + fail.getMessage(), fail);
		}
	}

	@Override
	public Set<Class> getMarkers() {
		return markers;
	}

	@Override
	public Class getServiceInterface() {
		return serviceInterface;
	}
}
