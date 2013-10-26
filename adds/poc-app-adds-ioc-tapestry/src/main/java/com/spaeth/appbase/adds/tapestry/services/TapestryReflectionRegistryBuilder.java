package com.spaeth.appbase.adds.tapestry.services;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.LoggerSource;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.def.ModuleDef;
import org.apache.tapestry5.ioc.internal.DefaultModuleDefImpl;
import org.apache.tapestry5.ioc.internal.LoggerSourceImpl;
import org.apache.tapestry5.ioc.internal.RegistryImpl;
import org.apache.tapestry5.ioc.internal.RegistryWrapper;
import org.apache.tapestry5.ioc.internal.ServiceBinderImpl;
import org.apache.tapestry5.ioc.internal.services.PlasticProxyFactoryImpl;
import org.apache.tapestry5.ioc.internal.util.OneShotLock;
import org.apache.tapestry5.ioc.services.PlasticProxyFactory;
import org.apache.tapestry5.ioc.services.TapestryIOCModule;
import org.slf4j.Logger;

import com.spaeth.appbase.adds.tapestry.services.customized.TapestryDefaultModule;
import com.spaeth.appbase.adds.tapestry.services.customized.TapestryReflectionModule;
import com.spaeth.appbase.adds.tapestry.services.processor.BinderFlushingVisitor;
import com.spaeth.appbase.adds.tapestry.services.processor.BuildMethodProcessor;
import com.spaeth.appbase.adds.tapestry.services.processor.ConfigurationMethodProcessor;
import com.spaeth.appbase.adds.tapestry.services.processor.ModuleRequiresProcessor;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.Module;

public class TapestryReflectionRegistryBuilder {

	private final OneShotLock lock = new OneShotLock();
	private final Collection<Module> modules = new HashSet<Module>();
	private final PlasticProxyFactory proxyFactory;
	private final LoggerSource loggerSource;
	private final ClassLoader classLoader;
	private final ModuleDef initialModule;

	public TapestryReflectionRegistryBuilder() {
		this(Thread.currentThread().getContextClassLoader());
	}

	public TapestryReflectionRegistryBuilder(final ClassLoader classLoader) {
		this(classLoader, new LoggerSourceImpl());
	}

	public TapestryReflectionRegistryBuilder(final ClassLoader classLoader, final LoggerSource ls) {
		this.classLoader = classLoader;
		this.loggerSource = ls;

		// final Logger classFactoryLogger =
		// ls.getLogger(TapestryIOCModule.class.getName() + ".ClassFactory");
		// this.classFactory = new ClassFactoryImpl(this.classLoader,
		// classFactoryLogger);

		final Logger proxyFactoryLogger = ls.getLogger(TapestryIOCModule.class.getName() + ".PlasticProxyFactory");
		this.proxyFactory = new PlasticProxyFactoryImpl(this.classLoader, proxyFactoryLogger);

		this.initialModule = new DefaultModuleDefImpl(TapestryIOCModule.class, proxyFactoryLogger, this.proxyFactory);
	}

	private Registry build() {
		this.lock.lock();

		final DeferedObjectLocator objectLocator = new DeferedObjectLocator();

		final List<ModuleDef> defs = getDefinitions(this.modules, this.proxyFactory, objectLocator);
		final RegistryImpl r = new RegistryImpl(defs, null, this.proxyFactory, this.loggerSource);
		final RegistryWrapper result = new RegistryWrapper(r);

		objectLocator.setDelegated(result);

		return result;
	}

	public void addModule(final Module module) {
		this.modules.add(module);
	}

	public static Registry buildAndStartup(final Collection<Class<? extends Module>> modules) {
		final TapestryReflectionRegistryBuilder builder = new TapestryReflectionRegistryBuilder();

		final Collection<Class<? extends Module>> modulesToBeAdded = new HashSet<Class<? extends Module>>();

		modulesToBeAdded.add(TapestryDefaultModule.class);
		modulesToBeAdded.addAll(modules);

		builder.modules.addAll(new ModuleRequiresProcessor().process(modulesToBeAdded));

		final Registry registry = builder.build();
		registry.performRegistryStartup();
		return registry;

	}

	private List<ModuleDef> getDefinitions(final Collection<Module> modules, final PlasticProxyFactory proxyFactory,
			final ObjectLocator objectLocator) {
		final ArrayList<ModuleDef> result = new ArrayList<ModuleDef>();

		result.add(this.initialModule);

		for (final Module m : modules) {
			result.add(convertToModuleDef(m, proxyFactory, objectLocator));
		}
		
		return result;
	}

	private ModuleDef convertToModuleDef(final Module module, final PlasticProxyFactory proxyFactory, final ObjectLocator objectLocator) {
		final TapestryReflectionModule result = new TapestryReflectionModule(module.getClass());

		processBindings(module, result);
		processMethods(module, result);

		return result;
	}

	private void processMethods(final Module module, final TapestryReflectionModule result) {

		// processors for builder methods and configuration methods
		BuildMethodProcessor bmp = new BuildMethodProcessor(module, result);
		ConfigurationMethodProcessor cmp = new ConfigurationMethodProcessor(module, result, proxyFactory);

		// for each method execute the processors
		for (final Method method : module.getClass().getDeclaredMethods()) {
			bmp.process(method);
			cmp.process(method);
		}

	}

	private void processBindings(final Module module, final TapestryReflectionModule result) {
		final Binder b = new Binder();

		module.configure(b);

		try {
			// tapestry tracking
			Method method = module.getClass().getMethod("configure", Binder.class);

			@SuppressWarnings("rawtypes")
			ServiceBinderImpl serviceBinder = new ServiceBinderImpl(result, method, proxyFactory, Collections.<Class> emptySet(), false);

			// execute all bindings
			b.accept(new BinderFlushingVisitor(serviceBinder));

			// commit them
			serviceBinder.finish();
		} catch (Exception e) {
			throw new RuntimeException("not able to perform bindings due to: " + e.getMessage(), e);
		}
	}

	private class DeferedObjectLocator implements ObjectLocator {

		private final OneShotLock lock = new OneShotLock();
		private ObjectLocator delegated;

		private DeferedObjectLocator() {
		}

		public void setDelegated(final ObjectLocator delegated) {
			this.lock.lock();
			this.delegated = delegated;
		}

		@Override
		public <T> T getService(final String serviceId, final Class<T> serviceInterface) {
			return this.delegated.getService(serviceId, serviceInterface);
		}

		@Override
		public <T> T getService(final Class<T> serviceInterface) {
			return this.delegated.getService(serviceInterface);
		}

		@Override
		public <T> T getObject(final Class<T> objectType, final AnnotationProvider annotationProvider) {
			return null;
		}

		@Override
		public <T> T autobuild(final Class<T> clazz) {
			return null;
		}

		@Override
		public <T> T autobuild(final String description, final Class<T> clazz) {
			return null;
		}

		@Override
		public <T> T proxy(final Class<T> interfaceClass, final Class<? extends T> implementationClass) {
			return null;
		}

	}

}
