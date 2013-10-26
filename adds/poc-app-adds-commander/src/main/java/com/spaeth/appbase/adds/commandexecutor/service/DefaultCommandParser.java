package com.spaeth.appbase.adds.commandexecutor.service;

import java.util.Arrays;

public class DefaultCommandParser implements CommandParser {

	private static final String SEPARATOR = " ";

	private final CommandParameterConverter converter;

	public DefaultCommandParser(final CommandParameterConverter converter) {
		super();
		this.converter = converter;
	}

	@Override
	public Command parse(final String command) {
		final String[] commandTiles = command.split(SEPARATOR);

		final String[] parameters = Arrays.copyOfRange(commandTiles, 1, commandTiles.length);

		return new DefaultCommand(this.converter, commandTiles[0], Arrays.asList(parameters));
	}

}
