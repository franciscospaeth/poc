package com.spaeth.appbase.adds.security;

import java.util.List;

import com.spaeth.appbase.adds.security.service.ActionPermissionService;
import com.spaeth.appbase.adds.security.service.ActionPermissionServicePermissionServiceAdapter;
import com.spaeth.appbase.adds.security.service.ApplicationControllerPermissionService;
import com.spaeth.appbase.adds.security.service.ApplicationControllerPermissionServicePermissionServiceAdapter;
import com.spaeth.appbase.adds.security.service.StateHolderSecurityStateRegistry;
import com.spaeth.appbase.core.annotations.BuilderMethod;
import com.spaeth.appbase.core.annotations.ConfigurationMethod;
import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.core.security.service.AuthenticationProvider;
import com.spaeth.appbase.core.security.service.DefaultSecurityConfiguration;
import com.spaeth.appbase.core.security.service.DefaultSecurityServiceFacade;
import com.spaeth.appbase.core.security.service.LogoutHook;
import com.spaeth.appbase.core.security.service.SecurityPermissionService;
import com.spaeth.appbase.core.security.service.SecurityService;
import com.spaeth.appbase.core.security.service.SecurityServiceFacade;
import com.spaeth.appbase.core.security.service.SecurityStateRegistry;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.ChainBuilder;
import com.spaeth.appbase.core.service.Configurator.OrderedConfiguration;
import com.spaeth.appbase.core.service.Module;

public class SecurityModule implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.new Binding(SecurityServiceFacade.class, DefaultSecurityServiceFacade.class);
		binder.new Binding(SecurityStateRegistry.class, StateHolderSecurityStateRegistry.class);
		binder.new Binding(SecurityService.class, DefaultSecurityConfiguration.class);
	}

	@BuilderMethod
	public LogoutHook buildLogoutHook(final List<LogoutHook> logoutHookList, final ChainBuilder chainBuilder) {
		return chainBuilder.build(LogoutHook.class, logoutHookList);
	}

	@BuilderMethod
	@Principal
	public SecurityPermissionService buildSecurityPermissionService(final List<SecurityPermissionService> logoutHookList,
			final ChainBuilder chainBuilder) {
		return chainBuilder.build(SecurityPermissionService.class, logoutHookList);
	}

	@BuilderMethod
	@Principal
	public AuthenticationProvider buildAuthenticationProvider(final List<AuthenticationProvider> authenticationProvider,
			final ChainBuilder chainBuilder) {
		return chainBuilder.build(AuthenticationProvider.class, authenticationProvider);
	}

	@BuilderMethod
	public ActionPermissionService buildActionPermissionService(final List<ActionPermissionService> logoutHookList,
			final ChainBuilder chainBuilder) {
		return chainBuilder.build(ActionPermissionService.class, logoutHookList);
	}

	@BuilderMethod
	public ApplicationControllerPermissionService buildApplicationControllerPermissionService(
			final List<ApplicationControllerPermissionService> logoutHookList, final ChainBuilder chainBuilder) {
		return chainBuilder.build(ApplicationControllerPermissionService.class, logoutHookList);
	}

	@ConfigurationMethod(SecurityPermissionService.class)
	@Principal
	public void configureSecurityPermissionService(final OrderedConfiguration<SecurityPermissionService> configurator) {
		configurator.addInstance("actionPermissionService", ActionPermissionServicePermissionServiceAdapter.class);
		configurator.addInstance("applicationControllPermissionService",
				ApplicationControllerPermissionServicePermissionServiceAdapter.class);

	}

}
