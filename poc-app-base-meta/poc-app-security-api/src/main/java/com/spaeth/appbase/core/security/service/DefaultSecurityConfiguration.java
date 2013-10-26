package com.spaeth.appbase.core.security.service;

import com.spaeth.appbase.core.security.model.Authenticable;

public class DefaultSecurityConfiguration implements SecurityService {

	private LogoutHook logoutHook = LogoutHook.DUMMY;
	private final AuthenticationProvider authenticationProvider;
	private final SecurityStateRegistry securityStateRegistry;

	public DefaultSecurityConfiguration(final AuthenticationProvider authenticationProvider,
			final SecurityStateRegistry securityStateRegistry, final LogoutHook logoutHook) {
		if (logoutHook == null) {
			throw new IllegalArgumentException("logoutHook should be not null");
		}
		this.logoutHook = logoutHook;

		if (authenticationProvider == null) {
			throw new IllegalArgumentException("authenticationProvider should be not null");
		}
		this.authenticationProvider = authenticationProvider;
		this.securityStateRegistry = securityStateRegistry;
	}

	@Override
	public Authenticable authenticate(final AuthenticationParameter authentication) {
		Authenticable authenticatedUser = getAuthenticated();

		if (authenticatedUser != null) {
			logOut();
		}

		Authenticable authenticated = authenticationProvider.authenticate(authentication);

		if (authenticated != null) {
			securityStateRegistry.register(authenticated);
		}

		return authenticated;
	}

	@Override
	public Authenticable getAuthenticated() {
		return securityStateRegistry.getRegistered();
	}

	@Override
	public void logOut() {
		securityStateRegistry.unregister();
		logoutHook.logout();
	}

}
