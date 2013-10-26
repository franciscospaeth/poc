package com.spaeth.reflection;

import java.util.List;

import com.spaeth.appbase.core.annotations.BuilderMethod;
import com.spaeth.appbase.core.annotations.ConfigurationMethod;
import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.appbase.core.service.Configurator.OrderedConfiguration;
import com.spaeth.sample.service.ServiceImplementation;
import com.spaeth.sample.service.ServiceInterface;

public class ModuleWithConfiguration implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.new Binding(ServiceImplementation.class);
	}

	@BuilderMethod
	@Principal
	public ServiceInterface buildMyService(final List<String> list) {
		System.out.println(list);
		return new ServiceImplementation();
	}

	@ConfigurationMethod(ServiceInterface.class)
	@Principal
	public void contribute(final OrderedConfiguration<String> listOf) {
		listOf.add("primeira", "valor");
	}

	@ConfigurationMethod(ServiceInterface.class)
	@Principal
	public void contribute2(final OrderedConfiguration<String> listOf, final ServiceImplementation impl) {
		listOf.add("outro", impl.method("testEcho"));
	}

}
