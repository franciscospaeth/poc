package com.spaeth.appbase.adds.security.service;

import com.spaeth.appbase.adds.security.model.ActionSecurityContext;

/**
 * This service intend to control action access permissions.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 */
public interface ActionPermissionService {

	public static String ACTION_PERMISSION_NAME = "appbase.actionProvider.execute";

	/**
	 * Receives all information regarding the action is being verified, in order
	 * to define if it is accessible or not.
	 * 
	 * @param actionSecurityContext
	 *            the context and action that verification is being executed,
	 *            this gives the exact action instance being checked against
	 *            loaded permissions
	 * @return if the action could or not be accessed
	 */
	boolean hasPermission(ActionSecurityContext actionSecurityContext);

}
