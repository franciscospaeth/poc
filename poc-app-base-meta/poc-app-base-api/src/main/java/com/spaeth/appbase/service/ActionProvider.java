package com.spaeth.appbase.service;

import com.spaeth.appbase.model.Action;

public interface ActionProvider {

	/**
	 * Provides an action based on a name.
	 * 
	 * @param actionName
	 *            action's name
	 * @return action object or null when action not found
	 */
	Action getAction(String actionName);

}
