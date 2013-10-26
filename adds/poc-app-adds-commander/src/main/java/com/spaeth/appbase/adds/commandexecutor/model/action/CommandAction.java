package com.spaeth.appbase.adds.commandexecutor.model.action;

import com.spaeth.appbase.adds.commandexecutor.service.CommandFacade;
import com.spaeth.appbase.core.model.action.AbstractAction;
import com.spaeth.appbase.event.ActionUpdateEvent;
import com.spaeth.appbase.model.ActionParameters;

public class CommandAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final CommandFacade commandFacade;
	private final String command;

	public CommandAction(final CommandFacade commandFacade, final String command) {
		super();
		this.commandFacade = commandFacade;
		this.command = command;
	}

	@Override
	protected void internalExecute(final ActionParameters parameters) {
		this.commandFacade.executeCommand(this.command);
	}

	@Override
	protected ActionUpdateEvent createActionEvent() {
		return new ActionUpdateEvent(this, this);
	}
}
