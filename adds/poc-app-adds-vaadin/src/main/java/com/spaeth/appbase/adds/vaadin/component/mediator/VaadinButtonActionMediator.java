package com.spaeth.appbase.adds.vaadin.component.mediator;

import com.spaeth.appbase.core.model.DefaultActionParameters;
import com.spaeth.appbase.event.ActionUpdateEvent;
import com.spaeth.appbase.event.ActionUpdateEventListener;
import com.spaeth.appbase.model.Action;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class VaadinButtonActionMediator implements ClickListener, ActionUpdateEventListener {

	private static final long serialVersionUID = 1L;
	private final Action action;
	private final Button button;
	private final Object source;

	public VaadinButtonActionMediator(final Action action, final Button button, final Object source) {
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
	public void buttonClick(final ClickEvent event) {
		this.action.execute(new DefaultActionParameters.Builder(source).build());
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

}
