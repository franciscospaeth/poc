package com.spaeth.appbase.adds.commandexecutor.service;

import com.spaeth.appbase.model.StartupInfo;
import com.spaeth.appbase.service.ApplicationController;

public abstract class AbstractApplicationControllerCommandExecutorAdapter extends AbstractCommandExecutor {

	private final ApplicationController applicationController;

	public AbstractApplicationControllerCommandExecutorAdapter(final ApplicationController applicationView) {
		super();
		this.applicationController = applicationView;
	}

	@Override
	public boolean execute(final Command command) throws Exception {
		this.applicationController.process(buildStartupInfo(command));
		return true;
	}

	protected abstract StartupInfo buildStartupInfo(final Command command);

}
