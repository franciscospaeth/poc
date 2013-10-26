package com.spaeth.appbase.core.security.event;

import com.spaeth.appbase.core.security.model.Authenticable;

public interface SecurityStateRegistryListener {

	void onRegister(Authenticable authenticable);

	void onUnregister(Authenticable authenticable);

	void onUpdated(Authenticable authenticable);

}
