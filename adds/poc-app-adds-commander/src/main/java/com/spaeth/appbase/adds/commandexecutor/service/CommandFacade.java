package com.spaeth.appbase.adds.commandexecutor.service;

public interface CommandFacade {

	/**
	 * Executes the given command.
	 * 
	 * @param command
	 */
	boolean executeCommand(String command);

}
