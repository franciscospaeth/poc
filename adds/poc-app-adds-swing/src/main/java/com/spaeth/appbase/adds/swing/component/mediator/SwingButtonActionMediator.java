package com.spaeth.appbase.adds.swing.component.mediator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.spaeth.appbase.core.model.DefaultActionParameters;
import com.spaeth.appbase.event.ActionUpdateEvent;
import com.spaeth.appbase.event.ActionUpdateEventListener;
import com.spaeth.appbase.model.Action;

public class SwingButtonActionMediator implements ActionListener, ActionUpdateEventListener {

	private static final long serialVersionUID = 1L;
	private final Action action;
	private final JButton button;
	private final Object source;

	public SwingButtonActionMediator(final Action action, final JButton button, final Object source) {
		this.button = button;
		this.action = action;
		this.source = source;
		initialize();
	}

	private void initialize() {
		this.action.refreshSecurityEvaluations();
		synchronizeActionValues();
	}

	@Override
	public void actionUpdated(final ActionUpdateEvent actionUpdateEvent) {
		synchronizeActionValues();
	}

	private void synchronizeActionValues() {
		this.button.setEnabled(this.action.isEnabled());
	}

	public Action getAction() {
		return action;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		this.action.execute(new DefaultActionParameters.Builder(source).build());
	}

}
