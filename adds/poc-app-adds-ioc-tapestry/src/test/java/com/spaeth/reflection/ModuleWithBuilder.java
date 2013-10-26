package com.spaeth.reflection;

import com.spaeth.appbase.core.annotations.BuilderMethod;
import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.sample.service.ServiceImplementation;
import com.spaeth.sample.service.ServiceImplementation2;
import com.spaeth.sample.service.ServiceInterface;

public class ModuleWithBuilder implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.new Binding(ServiceImplementation.class);
	}

	@BuilderMethod
	@Principal
	public ServiceInterface buildMyService(final ServiceImplementation impl) {
		System.out.println(impl + " was provided");
		return new ServiceImplementation2();
	}

}
