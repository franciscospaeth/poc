package com.spaeth.appbase.adds.security.service;

import com.spaeth.appbase.core.security.model.Authenticable;
import com.spaeth.appbase.core.security.service.SecurityStateRegistry;
import com.spaeth.appbase.core.service.StateHolder;

public class StateHolderSecurityStateRegistry implements SecurityStateRegistry {

	private final StateHolder stateHolder;

	public StateHolderSecurityStateRegistry(final StateHolder stateHolder) {
		this.stateHolder = stateHolder;
	}

	@Override
	public void unregister() {
		stateHolder.removeObject(Authenticable.class);
	}

	@Override
	public Authenticable getRegistered() {
		return stateHolder.getObject(Authenticable.class);
	}

	@Override
	public void register(final Authenticable authenticatedUser) {
		stateHolder.addObject(authenticatedUser, Authenticable.class);
	}

}
