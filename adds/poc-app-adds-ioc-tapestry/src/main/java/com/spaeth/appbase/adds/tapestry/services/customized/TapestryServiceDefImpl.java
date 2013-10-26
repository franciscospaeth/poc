// Copyright 2006, 2007, 2008, 2011 The Apache Software Foundation
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

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Flow;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.ObjectCreator;
import org.apache.tapestry5.ioc.ServiceBuilderResources;
import org.apache.tapestry5.ioc.def.ServiceDef3;
import org.apache.tapestry5.ioc.internal.ObjectCreatorSource;
import org.apache.tapestry5.ioc.internal.services.AnnotationProviderChain;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;

@SuppressWarnings("rawtypes")
public class TapestryServiceDefImpl implements ServiceDef3 {
	private final Class serviceInterface;

	private final String serviceId;

	private final String scope;

	private final ObjectCreatorSource source;

	private final Class marker;

	private final boolean eager;

	public TapestryServiceDefImpl(final Class serviceInterface, final String scope, final ObjectCreatorSource source, final boolean eager,
			final Class<?> marker) {
		this.serviceInterface = serviceInterface;
		this.scope = scope;
		this.serviceId = serviceInterface.getSimpleName() + String.format("(%s)", UUID.randomUUID().toString());
		this.source = source;
		this.eager = eager;
		this.marker = marker;
	}

	@Override
	public String toString() {
		return this.source.getDescription();
	}

	@Override
	public ObjectCreator createServiceCreator(final ServiceBuilderResources resources) {
		return this.source.constructCreator(resources);
	}

	@Override
	public String getServiceId() {
		return serviceId;
	}

	@Override
	public Class getServiceInterface() {
		return this.serviceInterface;
	}

	@Override
	public String getServiceScope() {
		return this.scope;
	}

	@Override
	public boolean isEagerLoad() {
		return eager;
	}

	@Override
	public Set<Class> getMarkers() {
		if (marker == null) {
			return Collections.emptySet();
		}
		return Collections.singleton(marker);
	}

	@Override
	public boolean isPreventDecoration() {
		return false;
	}

	private Flow<Class> searchPath() {
		return F.flow(this.serviceInterface).removeNulls();
	}

	@Override
	public AnnotationProvider getClassAnnotationProvider() {
		return AnnotationProviderChain.create(searchPath().map(InternalUtils.CLASS_TO_AP_MAPPER).toList());
	}

	@Override
	public AnnotationProvider getMethodAnnotationProvider(final String methodName, final Class... argumentTypes) {
		return AnnotationProviderChain.create(searchPath().map(new Mapper<Class, Method>() {
			@Override
			public Method map(final Class element) {
				return InternalUtils.findMethod(element, methodName, argumentTypes);
			}
		}).map(InternalUtils.METHOD_TO_AP_MAPPER).toList());
	}
}
