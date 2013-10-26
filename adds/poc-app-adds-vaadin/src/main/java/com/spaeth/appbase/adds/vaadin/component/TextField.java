package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.component.api.ITextField;
import com.spaeth.appbase.core.model.DefaultActionParameters;
import com.spaeth.appbase.model.Action;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;

public class TextField extends VisualFieldComponent<com.vaadin.ui.TextField> implements ITextField, BlurListener,
		FocusListener {

	private static final long serialVersionUID = 1L;

	private Action action;

	private ShortcutListener actionShortcut;

	@Override
	protected com.vaadin.ui.TextField createDelegated() {
		com.vaadin.ui.TextField delegated = new com.vaadin.ui.TextField();
		delegated.setImmediate(true);
		delegated.setInvalidAllowed(true);
		delegated.setInvalidCommitted(true);
		delegated.setNullRepresentation("");

		actionShortcut = new ShortcutListener("Action", ShortcutAction.KeyCode.ENTER, null) {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(final Object sender, final Object target) {
				executeAction();
			}

		};

		return delegated;
	}

	@Override
	public void setAction(final Action action) {
		if (action != null) {
			getDelegated().removeListener((BlurListener) this);
			getDelegated().removeListener((FocusListener) this);
		}
		this.action = action;
		if (action != null) {
			getDelegated().addListener((BlurListener) this);
			getDelegated().addListener((FocusListener) this);
		}
	}

	@Override
	public Action getAction() {
		return action;
	}

	private void executeAction() {
		if (action != null) {
			action.execute(new DefaultActionParameters.Builder(TextField.this).build());
		}
	}

	@Override
	public void focus(final FocusEvent event) {
		getDelegated().addShortcutListener(actionShortcut);
	}

	@Override
	public void blur(final BlurEvent event) {
		getDelegated().removeShortcutListener(actionShortcut);
	}

}
