package com.spaeth.appbase.adds.tapestry.services.processor;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.ObjectCreator;
import org.apache.tapestry5.ioc.OperationTracker;
import org.apache.tapestry5.ioc.ServiceBuilderResources;
import org.apache.tapestry5.ioc.internal.ObjectCreatorSource;
import org.apache.tapestry5.ioc.internal.ServiceBuilderMethodInvoker;
import org.slf4j.Logger;

import com.spaeth.appbase.adds.tapestry.services.customized.TapestryReflectionModule;
import com.spaeth.appbase.adds.tapestry.services.customized.TapestryServiceDefImpl;
import com.spaeth.appbase.core.annotations.BuilderMethod;
import com.spaeth.appbase.core.annotations.Scope;
import com.spaeth.appbase.core.marker.MarkerUtils;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.appbase.core.service.ScopeConstants;

public class BuildMethodProcessor extends AbstractMethodsProcessor {

	private final Module module;
	private final TapestryReflectionModule result;

	public BuildMethodProcessor(final Module module, final TapestryReflectionModule result) {
		super(BuilderMethod.class);
		this.module = module;
		this.result = result;
	}

	@Override
	protected void processInternal(final Method method) {

		Class<?> returnType = method.getReturnType();

		if (returnType.isPrimitive() || returnType.isArray()) {
			throw new RuntimeException("not able to create builder for type: " + returnType);
		}

		final ObjectCreatorSource ocs = new ObjectCreatorSource() {

			@Override
			public ObjectCreator<?> constructCreator(final ServiceBuilderResources resources) {
				ServiceBuilderResourcesFixedModule serviceBuilder = new ServiceBuilderResourcesFixedModule(resources, module);
				return new ServiceBuilderMethodInvoker(serviceBuilder, getDescription(), method);
			}

			@Override
			public String getDescription() {
				return String.valueOf(method);
			}

		};

		String scope = ScopeConstants.SINGLETON_SCOPE;
		if (method.getAnnotation(Scope.class) != null) {
			scope = method.getAnnotation(Scope.class).value();
		}

		BuilderMethod bma = method.getAnnotation(BuilderMethod.class);

		result.addServiceDef(new TapestryServiceDefImpl(returnType, scope, ocs, bma.eagerlyLoaded(), MarkerUtils.getFirstMarker(method)));
	}

	private class ServiceBuilderResourcesFixedModule implements ServiceBuilderResources {

		private final ServiceBuilderResources resources;
		private final Module module;

		public ServiceBuilderResourcesFixedModule(final ServiceBuilderResources resources, final Module module) {
			super();
			this.resources = resources;
			this.module = module;
		}

		// decorated method (return original module)
		@Override
		public Object getModuleBuilder() {
			return module;
		}

		// delegated methods
		@Override
		public String getServiceId() {
			return resources.getServiceId();
		}

		@Override
		public AnnotationProvider getClassAnnotationProvider() {
			return resources.getClassAnnotationProvider();
		}

		@Override
		public Class<?> getServiceInterface() {
			return resources.getServiceInterface();
		}

		@Override
		public <T> Collection<T> getUnorderedConfiguration(final Class<T> valueType) {
			return resources.getUnorderedConfiguration(valueType);
		}

		@Override
		public <T> List<T> getOrderedConfiguration(final Class<T> valueType) {
			return resources.getOrderedConfiguration(valueType);
		}

		@Override
		public <T> T getService(final String serviceId, final Class<T> serviceInterface) {
			return resources.getService(serviceId, serviceInterface);
		}

		@Override
		public Logger getLogger() {
			return resources.getLogger();
		}

		@Override
		public <K, V> Map<K, V> getMappedConfiguration(final Class<K> keyType, final Class<V> valueType) {
			return resources.getMappedConfiguration(keyType, valueType);
		}

		@Override
		public AnnotationProvider getMethodAnnotationProvider(final String methodName,
				@SuppressWarnings("rawtypes") final Class... parameterTypes) {
			return resources.getMethodAnnotationProvider(methodName, parameterTypes);
		}

		@Override
		public OperationTracker getTracker() {
			return resources.getTracker();
		}

		@Override
		public Class<?> getImplementationClass() {
			return null;
		}

		@Override
		public <T> T getService(final Class<T> serviceInterface) {
			return resources.getService(serviceInterface);
		}

		@Override
		public <T> T getObject(final Class<T> objectType, final AnnotationProvider annotationProvider) {
			return resources.getObject(objectType, annotationProvider);
		}

		@Override
		public <T> T autobuild(final Class<T> clazz) {
			return resources.autobuild(clazz);
		}

		@Override
		public <T> T autobuild(final String description, final Class<T> clazz) {
			return resources.autobuild(description, clazz);
		}

		@Override
		public <T> T proxy(final Class<T> interfaceClass, final Class<? extends T> implementationClass) {
			return resources.proxy(interfaceClass, implementationClass);
		}

	}

}
