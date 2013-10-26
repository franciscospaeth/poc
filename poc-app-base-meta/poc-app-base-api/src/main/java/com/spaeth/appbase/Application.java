package com.spaeth.appbase;

import com.spaeth.appbase.model.Message;

public interface Application<InstanceType extends Object> {

	/**
	 * Initialization.
	 */
	void initialize();

	/**
	 * Returns application instance, used to abstract the engine from the
	 * methodology applied to serve an application if it is in session,
	 * database, stand alone ...
	 * 
	 * @return
	 */
	InstanceType getInstance();

	/**
	 * Method invoked in order to finish the application execution.
	 */
	void exit();

	/**
	 * Show a message for the user.
	 * 
	 * @param message
	 *            message object to be shown to the user
	 */
	void showMessage(Message message);

}
