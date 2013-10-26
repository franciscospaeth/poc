package com.spaeth.appbase.adds.security.service;

import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import com.spaeth.appbase.core.security.model.Authenticable;
import com.spaeth.appbase.core.security.service.AuthenticationParameter;
import com.spaeth.appbase.core.security.service.AuthenticationProvider;
import com.spaeth.appbase.core.security.service.UserNamePasswordAuthenticationParameter;

public class UserPasswordMapAuthenticationProvider implements AuthenticationProvider {

	private final Map<String, String> userPasswordMap;
	private final AuthenticableProvider authenticableProvider;

	public UserPasswordMapAuthenticationProvider(final AuthenticableProvider authenticableProvider,
			final Map<String, String> userPasswordMap) {
		this.userPasswordMap = userPasswordMap;
		this.authenticableProvider = authenticableProvider;
	}

	@Override
	public Authenticable authenticate(final AuthenticationParameter authentication) {

		if (authentication instanceof UserNamePasswordAuthenticationParameter) {
			final UserNamePasswordAuthenticationParameter auth = (UserNamePasswordAuthenticationParameter) authentication;
			if (ObjectUtils.equals(userPasswordMap.get(auth.getUserName()), auth.getPassword())) {
				return authenticableProvider.getAuthenticable(auth.getUserName());
			}
		}
		return null;
	}

}
