package com.spaeth.appbase.core.datasource;

import com.spaeth.appbase.core.security.model.AccessPolicy;

/**
 * The interface is implemented by something that is affected indirectly by
 * other elements which {@link AccessPolicy} could change.
 * 
 * This allows the binding from the implementor of this interface to elements
 * that could in a certain way change the implementor's {@link AccessPolicy}.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 */
public interface AccessPolicyManaged {

	/**
	 * Invoked in order to refresh the {@link AccessPolicy}.
	 */
	void refreshAccessPolicy();

}
