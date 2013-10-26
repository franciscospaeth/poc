package com.spaeth.appbase.adds.commandexecutor.service;

public class DefaultCommandFacade implements CommandFacade {

	private final CommandExecutor commandExecutor;
	private final CommandParser commandParser;

	public DefaultCommandFacade(final CommandExecutor commandExecutor, final CommandParser commandParser) {
		super();
		this.commandExecutor = commandExecutor;
		this.commandParser = commandParser;
	}

	@Override
	public boolean executeCommand(final String command) {
		return this.commandExecutor.executeCommand(this.commandParser.parse(command));
	}

}
