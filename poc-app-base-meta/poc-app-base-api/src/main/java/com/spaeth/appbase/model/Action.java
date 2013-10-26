package com.spaeth.appbase.model;

import java.io.Serializable;

import com.spaeth.appbase.core.security.SecurityManaged;
import com.spaeth.appbase.event.ActionUpdateEventListener;

public interface Action extends SecurityManaged, Serializable {

	/**
	 * If at the moment such action shall be enabled or not.
	 * 
	 * @return
	 */
	boolean isEnabled();

	void setEnabled(boolean enable);

	/**
	 * Executes action based on the given parameters.
	 * 
	 * @param parameters
	 */
	void execute(ActionParameters parameters);

	/**
	 * Caption that shall be exposed in interface for this action.
	 * 
	 * @return
	 */
	// String getCaption();

	// void setCaption(String caption);

	/**
	 * Icon that shall be exposed in interface for this action.
	 * 
	 * @return
	 */
	// String getIcon();

	// void setIcon(String icon);

	/**
	 * Listeners that will be informed about changes to this action.
	 * 
	 * @param listener
	 */
	void addUpdateListener(ActionUpdateEventListener listener);

	void removeUpdateListener(ActionUpdateEventListener listener);

	public abstract void removeSecurityConstraint(final ActionSecurityConstraint securityConstraint);

	public abstract void addSecurityConstraint(final ActionSecurityConstraint securityConstraint);

}