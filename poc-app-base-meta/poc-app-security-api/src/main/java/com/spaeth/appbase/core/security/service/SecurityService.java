package com.spaeth.appbase.core.security.service;

import com.spaeth.appbase.core.security.model.Authenticable;

public interface SecurityService {

	/**
	 * Request authentication and if succeed, userObject is returned.
	 * {@link UserService} will return same user after authentication.
	 * 
	 * @param authentication
	 *            authentication parameter
	 * @return
	 */
	Authenticable authenticate(AuthenticationParameter authentication);

	/**
	 * Retrieves the authenticated.
	 * 
	 * @param c
	 * @return
	 */
	Authenticable getAuthenticated();

	/**
	 * Executes user logout.
	 */
	void logOut();

}
