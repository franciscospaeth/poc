package com.spaeth.appbase.adds.tapestry.services.customized;

import org.apache.tapestry5.ioc.ObjectCreator;
import org.apache.tapestry5.ioc.ServiceLifecycle2;
import org.apache.tapestry5.ioc.ServiceResources;
import org.apache.tapestry5.ioc.services.PlasticProxyFactory;

import com.spaeth.appbase.core.service.Repository;
import com.spaeth.appbase.core.service.StateHolder;

public class TapestrySessionScope implements ServiceLifecycle2 {

	private final Repository repository;

	public TapestrySessionScope(final Repository repository) {
		super();
		this.repository = repository;
	}

	private StateHolder getStateHolder() {
		return repository.getInstance(StateHolder.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object createService(final ServiceResources resources, @SuppressWarnings("rawtypes") final ObjectCreator creator) {

		Class<?> serviceInterface = resources.getServiceInterface();
		PlasticProxyFactory proxyFactory = repository.getInstance(PlasticProxyFactory.class);
		Object created = proxyFactory.createProxy(serviceInterface, new SessionObjectCreator(resources, creator, getStateHolder()),
				String.format("<PerThread Proxy for %s(%s)>", resources.getServiceId(), serviceInterface.getName()));
		return created;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	public boolean requiresProxy() {
		return true;
	}

	@SuppressWarnings("rawtypes")
	private static class SessionObjectCreator implements ObjectCreator {

		private final ServiceResources resources;
		private final ObjectCreator<?> creator;
		private final StateHolder stateHolder;

		public SessionObjectCreator(final ServiceResources resources, final ObjectCreator creator, final StateHolder stateHolder) {
			super();
			this.resources = resources;
			this.creator = creator;
			this.stateHolder = stateHolder;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object createObject() {
			Object service = stateHolder.getObject(resources.getServiceInterface());

			if (service != null) {
				return service;
			} else {
				Object created = creator.createObject();
				stateHolder.addObject(created, resources.getServiceInterface());
				return created;
			}
		}
	}

}