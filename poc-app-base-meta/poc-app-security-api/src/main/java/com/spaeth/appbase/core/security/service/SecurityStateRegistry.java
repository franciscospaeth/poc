package com.spaeth.appbase.core.security.service;

import com.spaeth.appbase.core.security.model.Authenticable;

public interface SecurityStateRegistry {

	/**
	 * Remove the registered {@link Authenticable} from the state holder.
	 */
	void unregister();

	/**
	 * Add the {@link Authenticable} to the state holder.
	 * 
	 * @param authenticated
	 *            the one to be added to the state holder
	 */
	void register(Authenticable authenticated);

	/**
	 * Retrieves the registered {@link Authenticable}.
	 * 
	 * @return the {@link Authenticable} registered to the state holder
	 */
	Authenticable getRegistered();

}
