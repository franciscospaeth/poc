package com.spaeth.appbase.adds.security.service;

import java.io.Serializable;

import com.spaeth.appbase.core.security.model.Authenticable;

public class DefaultAuthenticableProvider implements AuthenticableProvider {

	@Override
	public Authenticable getAuthenticable(final Serializable identification) {
		return Authenticable.DUMMY;
	}

}
