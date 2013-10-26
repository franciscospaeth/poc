package com.spaeth.appbase.event;

import java.util.EventObject;

import com.spaeth.appbase.Application;

public class ApplicationInitializationEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private final Application<?> application;

	public ApplicationInitializationEvent(Application<?> application) {
		super(application);
		this.application = application;
	}
	
	public Application<?> getApplication() {
		return application;
	}

}
