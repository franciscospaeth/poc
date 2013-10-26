package com.spaeth.appbase.core.security.service;

/**
 * Executed before user logout.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 *
 */
public interface LogoutHook {

	public static final LogoutHook DUMMY = new LogoutHook() {
		@Override
		public void logout() {
			// dummy implementation
		}
	}; 
	
	public void logout();
	
}
