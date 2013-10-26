package com.spaeth.appbase.core.security.service;

import com.spaeth.appbase.core.security.model.Authenticable;

public class DefaultSecurityServiceFacade implements SecurityServiceFacade {

	private SecurityPermissionService permissionService;
	private SecurityService securityConfiguration;

	public DefaultSecurityServiceFacade(SecurityPermissionService permissionService, SecurityService securityConfiguration) {
		super();
		this.permissionService = permissionService;
		this.securityConfiguration = securityConfiguration;
	}

	public boolean hasPermission(String securityConstraintIdentifier) {
		return permissionService.hasPermission(securityConstraintIdentifier);
	}

	public boolean hasPermission(String securityConstraintIdentifier, Object context) {
		return permissionService.hasPermission(securityConstraintIdentifier, context);
	}

	public Authenticable authenticate(AuthenticationParameter authentication) {
		return securityConfiguration.authenticate(authentication);
	}

	public Authenticable getAuthenticatedUser() {
		return securityConfiguration.getAuthenticated();
	}

	public void logOut() {
		securityConfiguration.logOut();
	}

}
