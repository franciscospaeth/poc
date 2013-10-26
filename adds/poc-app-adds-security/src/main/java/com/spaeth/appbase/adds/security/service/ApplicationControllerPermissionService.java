package com.spaeth.appbase.adds.security.service;

import com.spaeth.appbase.model.StartupInfo;

/**
 * This service intend to control application controller startupInfo processing
 * permissions.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 */
public interface ApplicationControllerPermissionService {

	public static String ACTION_PERMISSION_NAME = "appbase.applicationController.execute";

	/**
	 * Receives startupInfo being processed, in order to define if it is allowed
	 * or not to handle it.
	 * 
	 * @param startup
	 * @return if the action could or not be accessed
	 */
	boolean hasPermission(StartupInfo startup);

}
