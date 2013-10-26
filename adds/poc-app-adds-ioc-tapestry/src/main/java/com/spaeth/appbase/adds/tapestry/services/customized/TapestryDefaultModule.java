package com.spaeth.appbase.adds.tapestry.services.customized;

import java.util.List;

import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ServiceLifecycle;
import org.apache.tapestry5.ioc.services.MasterObjectProvider;
import org.apache.tapestry5.ioc.services.ServiceLifecycleSource;

import com.spaeth.appbase.core.annotations.BuilderMethod;
import com.spaeth.appbase.core.annotations.ConfigurationMethod;
import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.ChainBuilder;
import com.spaeth.appbase.core.service.Configurator.MappedConfiguration;
import com.spaeth.appbase.core.service.Configurator.OrderedConfiguration;
import com.spaeth.appbase.core.service.IoCThreadManager;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.appbase.core.service.ObjectProvider;
import com.spaeth.appbase.core.service.Repository;
import com.spaeth.appbase.core.service.ScopeConstants;

public class TapestryDefaultModule implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.new Binding(ChainBuilder.class, TapestryChainBuilder.class);
		binder.new Binding(IoCThreadManager.class, TapestryIoCThreadManager.class);
	}

	@BuilderMethod
	public Repository buildRepository(final ObjectLocator objectLocator) {
		return new TapestryRepository(objectLocator);
	}

	@BuilderMethod
	@Principal
	public ObjectProvider buildPocObjectProvider(final List<ObjectProvider> c) {
		return new ObjectProvider() {
			@Override
			public <T> T provide(final Class<T> requiredClass, final Repository p) {
				for (ObjectProvider op : c) {
					T provided = op.provide(requiredClass, p);
					if (provided != null) {
						return provided;
					}
				}
				return null;
			}

		};
	}

	@ConfigurationMethod(ServiceLifecycleSource.class)
	public void configureServiceLifecycleSource(final MappedConfiguration<String, ServiceLifecycle> configuration,
			final Repository repository) {
		configuration.add(ScopeConstants.SESSION_SCOPE, new TapestrySessionScope(repository));
	}

	@ConfigurationMethod(MasterObjectProvider.class)
	public void configureMasterObjectProvider(final OrderedConfiguration<org.apache.tapestry5.ioc.ObjectProvider> configurator,
			@Principal final ObjectProvider objectProvider) {
		configurator.add("POC_OBJECT_PROVIDER", new org.apache.tapestry5.ioc.ObjectProvider() {
			@Override
			public <T> T provide(final Class<T> objectType, final AnnotationProvider annotationProvider, final ObjectLocator locator) {
				return objectProvider.provide(objectType, new TapestryRepository(locator));
			}

		}, "after:AnnotationBasedContributions", "before:ServiceOverride");
	}

}
