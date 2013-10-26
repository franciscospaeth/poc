package com.spaeth.appbase.adds.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.spaeth.appbase.Application;
import com.spaeth.appbase.adds.swing.component.ComponentContainer;
import com.spaeth.appbase.event.ApplicationInitListener;
import com.spaeth.appbase.event.ApplicationInitializationEvent;
import com.spaeth.appbase.model.Message;

public class SwingApplication implements Application<SwingApplication> {

	private JFrame mainFrame;
	private final ApplicationInitListener applicationInitializationListener;

	public SwingApplication(final ApplicationInitListener applicationInitializationListener) {
		super();
		this.applicationInitializationListener = applicationInitializationListener;
	}

	@Override
	public void initialize() {
		applicationInitializationListener.applicationInitialized(new ApplicationInitializationEvent(this));
	}

	@Override
	public SwingApplication getInstance() {
		return this;
	}

	@Override
	public void exit() {
		System.exit(0);
	}

	public void showOnMainFrame(final ComponentContainer<JPanel> view) {
		if (mainFrame != null) {
			mainFrame.dispose();
		}
		mainFrame = new JFrame();
		mainFrame.setContentPane(view.getDelegated());
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	@Override
	public void showMessage(final Message message) {
		throw new RuntimeException("not yet implemented");
	}

}
