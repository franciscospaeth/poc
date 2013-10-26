package com.spaeth.appbase.adds.commandexecutor.service;

public abstract class AbstractCommandExecutor implements CommandExecutor {

	@Override
	public final boolean executeCommand(final Command command) {

		// check if command is expectedd by this commandExecutor
		if (!isAppropriated(command)) {
			return false;
		}

		// proceed with execution
		try {
			return execute(command);
		} catch (final RuntimeException e) {
			throw e;
		} catch (final Exception e) {
			throw new RuntimeException("Error executing '" + command + "' by " + this.getClass().getName());
		}
	}

	@Override
	public abstract boolean isAppropriated(final Command command);

	protected abstract boolean execute(Command command) throws Exception;

}
