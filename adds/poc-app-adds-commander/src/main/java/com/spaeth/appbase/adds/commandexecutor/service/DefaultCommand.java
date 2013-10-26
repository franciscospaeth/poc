package com.spaeth.appbase.adds.commandexecutor.service;

import java.io.Serializable;
import java.util.List;

public class DefaultCommand implements Command {

	private final List<String> parameters;
	private final String commandName;
	private final CommandParameterConverter converter;

	public DefaultCommand(final CommandParameterConverter converter, final String commandName,
			final List<String> parameters) {
		super();
		this.converter = converter;
		this.commandName = commandName;
		this.parameters = parameters;
	}

	@Override
	public String getName() {
		return this.commandName;
	}

	@Override
	public String getParameterAsString(final int i) {
		if (i < 0) {
			throw new IllegalArgumentException("parameter index needs to be greater than 0");
		}
		if (i >= this.parameters.size()) {
			return null;
		}
		return this.parameters.get(i);
	}

	@Override
	public int getParameterAsInt(final int i) {
		return getParameter(i, Integer.class);
	}

	@Override
	public double getParameterAsDouble(final int i) {
		return getParameter(i, Double.class);
	}

	@Override
	public <M extends Serializable> M getParameter(final int i, final Class<M> clazz) {
		return clazz.cast(this.converter.convert(this.parameters.get(i), clazz));
	}

}
