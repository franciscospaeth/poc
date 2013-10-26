package com.spaeth.reflection;

import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.sample.service.ServiceImplementation;
import com.spaeth.sample.service.ServiceInterface;

public class ModuleWithBinding implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.new Binding(ServiceImplementation.class);
		binder.new Binding(ServiceInterface.class, ServiceImplementation.class).withMarker(Principal.class);
	}

}
