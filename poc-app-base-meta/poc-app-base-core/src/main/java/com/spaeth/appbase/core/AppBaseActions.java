package com.spaeth.appbase.core;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.Application;
import com.spaeth.appbase.annotations.Action;

public class AppBaseActions {

	private static Logger logger = LoggerFactory.getLogger(AppBaseActions.class);

	@Inject
	private Application<?> application;

	@Action(name = "com.spaeth.appbase.application.exit")
	public void exit() {
		logger.info("application exit invoked");
		application.exit();
	}

	public void setApplication(final Application<?> application) {
		this.application = application;
	}

}
