package com.spaeth.appbase.core;

import java.util.List;
import java.util.Map;

import com.spaeth.appbase.AppBaseConstants;
import com.spaeth.appbase.ViewPort;
import com.spaeth.appbase.core.annotations.BuilderMethod;
import com.spaeth.appbase.core.annotations.ConfigurationMethod;
import com.spaeth.appbase.core.marker.Application;
import com.spaeth.appbase.core.marker.Default;
import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.core.model.action.ObjectMethodActionProvider;
import com.spaeth.appbase.core.service.ActionProviderFacade;
import com.spaeth.appbase.core.service.ApplicationControllerImpl;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.ChainBuilder;
import com.spaeth.appbase.core.service.ClassBasedRepositoryViewModelProvider;
import com.spaeth.appbase.core.service.ClassBasedRepositoryViewProvider;
import com.spaeth.appbase.core.service.Configurator.MappedConfiguration;
import com.spaeth.appbase.core.service.Configurator.OrderedConfiguration;
import com.spaeth.appbase.core.service.DefaultActionProviderFacade;
import com.spaeth.appbase.core.service.DefaultConfigurationService;
import com.spaeth.appbase.core.service.DefaultConfigurationServiceFacade;
import com.spaeth.appbase.core.service.DefaultI18NSupport;
import com.spaeth.appbase.core.service.DefaultResourceLoader;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.appbase.core.service.PropertiesConfigurationService;
import com.spaeth.appbase.core.service.Repository;
import com.spaeth.appbase.core.service.RepositoryViewContextBuilderFactory;
import com.spaeth.appbase.core.service.SystemPropertyConfigurationService;
import com.spaeth.appbase.core.service.ViewContextBuilderFactory;
import com.spaeth.appbase.core.startupinfo.QuestionStartupInfo;
import com.spaeth.appbase.core.view.QuestionDialog;
import com.spaeth.appbase.event.ApplicationInitListener;
import com.spaeth.appbase.service.ActionProvider;
import com.spaeth.appbase.service.ApplicationController;
import com.spaeth.appbase.service.ConfigurationService;
import com.spaeth.appbase.service.ConfigurationServiceFacade;
import com.spaeth.appbase.service.I18NSupport;
import com.spaeth.appbase.service.ResourceLoader;
import com.spaeth.appbase.service.ViewModelProvider;
import com.spaeth.appbase.service.ViewProvider;

/**
 * @author "Francisco Spaeth (francisco.spaeth@gmail.com)"
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AppBaseModule implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.new Binding(ActionProviderFacade.class, DefaultActionProviderFacade.class);
		binder.new Binding(ConfigurationServiceFacade.class, DefaultConfigurationServiceFacade.class);
		binder.new Binding(I18NSupport.class, DefaultI18NSupport.class);
		binder.new Binding(ApplicationController.class, ApplicationControllerImpl.class);
		binder.new Binding(ViewContextBuilderFactory.class, RepositoryViewContextBuilderFactory.class);
	}

	@BuilderMethod
	public ResourceLoader buildResourceLoader() {
		return new DefaultResourceLoader();
	}

	@BuilderMethod
	@Default
	public ConfigurationService buildDefaultConfigurationService(final Map<String, Object> config) {
		return new DefaultConfigurationService(config);
	}

	@BuilderMethod
	@Application
	public ConfigurationService buildApplicationConfigurationService(final Map<String, Object> config) {
		return new DefaultConfigurationService(config);
	}

	@BuilderMethod
	@Principal
	public ViewPort buildViewPort(final List<ViewPort> config, final ChainBuilder chainBuilder) {
		return chainBuilder.build(ViewPort.class, config);
	}

	@BuilderMethod
	@Principal
	public ViewModelProvider buildViewModelProvider(final List<ViewModelProvider> config, final ChainBuilder chainBuilder) {
		return chainBuilder.build(ViewModelProvider.class, config);
	}

	@BuilderMethod
	@Principal
	public ActionProvider buildActionProvider(final List<ActionProvider> config, final ChainBuilder chainBuilder) {
		return chainBuilder.build(ActionProvider.class, config);
	}

	@BuilderMethod
	@Principal
	public ViewProvider<?> buildViewProvider(final List<ViewProvider> config, final ChainBuilder chainBuilder) {
		return chainBuilder.build(ViewProvider.class, config);
	}

	@BuilderMethod
	@Principal
	public ConfigurationService buildConfigurationService(final List<ConfigurationService> config, final ChainBuilder chainBuilder) {
		return chainBuilder.build(ConfigurationService.class, config);
	}

	@BuilderMethod
	@Principal
	public ApplicationInitListener buildApplicationInitListener(final List<ApplicationInitListener> config, final ChainBuilder chainBuilder) {
		return chainBuilder.build(ApplicationInitListener.class, config);
	}

	@BuilderMethod
	public ClassBasedRepositoryViewProvider buildClassBasedRepositoryViewProvider(final Map<Class, Class> config,
			final Repository repository) {
		return new ClassBasedRepositoryViewProvider(repository, (Map) config);
	}

	@BuilderMethod
	public ClassBasedRepositoryViewModelProvider buildClassBasedRepositoryViewModelProvider(final Map<Class, Class> config,
			final Repository repository) {
		return new ClassBasedRepositoryViewModelProvider(repository, (Map) config);
	}

	@ConfigurationMethod(ConfigurationService.class)
	@Default
	public void configureDefaultConfigurationService(final MappedConfiguration<String, Object> config) {
		config.add(AppBaseConstants.EXECUTION_MODE, AppBaseConstants.EXECUTION_MODE_PRODUCTION);
	}

	@ConfigurationMethod(ConfigurationService.class)
	@Principal
	public void configureConfigurationService(final OrderedConfiguration<ConfigurationService> config,
			@Default final ConfigurationService defCS, @com.spaeth.appbase.core.marker.Application final ConfigurationService appCS) {
		config.add("external", new PropertiesConfigurationService(System.getProperty("poc.externalPropertiesFile")));
		config.add("instance", new SystemPropertyConfigurationService());
		config.add("application", appCS);
		config.add("default", defCS);
	}

	@ConfigurationMethod(ViewProvider.class)
	@Principal
	public void configureViewProvider(final OrderedConfiguration<ViewProvider<?>> config,
			final ClassBasedRepositoryViewProvider viewProvider) {
		config.add("classBasedViewProvider", viewProvider);
	}

	@ConfigurationMethod(ViewModelProvider.class)
	@Principal
	public void configureViewModelProvider(final OrderedConfiguration<ViewModelProvider> config,
			final ClassBasedRepositoryViewModelProvider modelProvider) {
		config.add("classBasedViewModelProvider", modelProvider);
	}

	@ConfigurationMethod(ActionProvider.class)
	@Principal
	public void configureActionProvider(final OrderedConfiguration<ActionProvider> config, final Repository r) {
		config.add("appBaseActions", new ObjectMethodActionProvider(r.newInstance(AppBaseActions.class)));
	}

	@ConfigurationMethod(ClassBasedRepositoryViewProvider.class)
	public void configureClassBasedRepositoryViewProvider(final MappedConfiguration<Class, Class> config) {
		config.add(QuestionStartupInfo.class, QuestionDialog.class);
	}

}