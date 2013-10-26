package com.spaeth.appbase.core.security.service;

import com.spaeth.appbase.core.security.model.Authenticable;

public interface SecurityServiceFacade {

	Authenticable authenticate(AuthenticationParameter authentication);

	Authenticable getAuthenticatedUser();

	void logOut();

	boolean hasPermission(String securityConstraintIdentifier);

	boolean hasPermission(String securityConstraintIdentifier, Object context);

}
