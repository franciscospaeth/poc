package com.spaeth.appbase.adds.security.service;

import java.io.Serializable;

import com.spaeth.appbase.core.security.model.Authenticable;

/**
 * This service intends to provide authenticables after authentication. Normally
 * used in order to load the user object from persistent layer or security
 * repository.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 */
public interface AuthenticableProvider {

	Authenticable getAuthenticable(Serializable identification);
	
}
