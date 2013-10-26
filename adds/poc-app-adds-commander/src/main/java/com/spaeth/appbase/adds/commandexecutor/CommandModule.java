package com.spaeth.appbase.adds.commandexecutor;

import java.util.List;

import com.spaeth.appbase.adds.commandexecutor.service.CommandExecutor;
import com.spaeth.appbase.adds.commandexecutor.service.CommandFacade;
import com.spaeth.appbase.adds.commandexecutor.service.CommandParameterConverter;
import com.spaeth.appbase.adds.commandexecutor.service.CommandParser;
import com.spaeth.appbase.adds.commandexecutor.service.DefaultCommandFacade;
import com.spaeth.appbase.adds.commandexecutor.service.DefaultCommandParser;
import com.spaeth.appbase.adds.commandexecutor.service.converter.DoubleCommandParameterConverter;
import com.spaeth.appbase.adds.commandexecutor.service.converter.IntegerCommandParameterConverter;
import com.spaeth.appbase.core.annotations.BuilderMethod;
import com.spaeth.appbase.core.annotations.ConfigurationMethod;
import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.ChainBuilder;
import com.spaeth.appbase.core.service.Configurator.OrderedConfiguration;
import com.spaeth.appbase.core.service.Module;

public class CommandModule implements Module {

	@Override
	public void configure(final Binder binder) {
	}

	@BuilderMethod
	@Principal
	public CommandParameterConverter buildCommandParameterConverter(final ChainBuilder chainBuilder,
			final List<CommandParameterConverter> commandParameterConverter) {
		return chainBuilder.build(CommandParameterConverter.class, commandParameterConverter);
	}

	@BuilderMethod
	@Principal
	public CommandExecutor buildCommandExecutor(final ChainBuilder chainBuilder, final List<CommandExecutor> commandExecutors) {
		return chainBuilder.build(CommandExecutor.class, commandExecutors);
	}

	@BuilderMethod
	public CommandFacade buildCommandFacade(final CommandParser commandParser, @Principal final CommandExecutor commandExecutor) {
		return new DefaultCommandFacade(commandExecutor, commandParser);
	}

	@BuilderMethod
	public CommandParser buildCommandParser(@Principal final CommandParameterConverter commandParameterConverter) {
		return new DefaultCommandParser(commandParameterConverter);
	}

	@ConfigurationMethod(CommandParameterConverter.class)
	@Principal
	public void configureCommandParameterConverter(final OrderedConfiguration<CommandParameterConverter> config) {
		config.addInstance("IntegerConverter", IntegerCommandParameterConverter.class);
		config.addInstance("DoubleConverter", DoubleCommandParameterConverter.class);
	}

}