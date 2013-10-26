package com.spaeth.appbase.adds.vaadin;

import com.spaeth.appbase.Application;
import com.spaeth.appbase.event.ApplicationInitListener;
import com.spaeth.appbase.event.ApplicationInitializationEvent;
import com.spaeth.appbase.model.Message;
import com.vaadin.ui.Window;

public class VaadinApplication extends com.vaadin.Application implements Application<com.vaadin.Application> {

	private static final long serialVersionUID = 1L;
	private final ApplicationInitListener applicationInitializationListener;

	public VaadinApplication(final ApplicationInitListener applicationInitializationListener) {
		this.applicationInitializationListener = applicationInitializationListener;
	}

	@Override
	public final void init() {
		initialize();
		applicationInitializationListener.applicationInitialized(new ApplicationInitializationEvent(this));
	}

	@Override
	public com.vaadin.Application getInstance() {
		return this;
	}

	@Override
	public void initialize() {
		setMainWindow(new Window("Poc App - Vaadin"));
	}

	@Override
	public void exit() {
		close();
	}

	@Override
	public void showMessage(final Message message) {
		getMainWindow().showNotification(message.getTitle(), message.getMessage(), Window.Notification.TYPE_TRAY_NOTIFICATION);
	}

}
