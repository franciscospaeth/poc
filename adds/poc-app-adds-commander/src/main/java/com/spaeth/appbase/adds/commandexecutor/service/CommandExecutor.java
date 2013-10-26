package com.spaeth.appbase.adds.commandexecutor.service;

public interface CommandExecutor {

	/**
	 * Executes the given command.
	 * 
	 * @param command
	 */
	boolean executeCommand(Command command);

	/**
	 * Returns if the command may be compatible with this command executor.
	 * 
	 * @param command
	 * @return
	 */
	boolean isAppropriated(Command command);

	/**
	 * Used in order to inform how to use this command, parameters and logic.
	 * 
	 * @return
	 */
	String getDocumentation();

}
