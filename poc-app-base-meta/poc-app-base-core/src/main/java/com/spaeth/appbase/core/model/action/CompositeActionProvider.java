package com.spaeth.appbase.core.model.action;

import com.spaeth.appbase.model.Action;
import com.spaeth.appbase.service.ActionProvider;

public class CompositeActionProvider implements ActionProvider {

	private final ActionProvider primary;
	private final ActionProvider secondary;

	public CompositeActionProvider(final ActionProvider primary, final ActionProvider secondary) {
		super();
		this.primary = primary;
		this.secondary = secondary;
	}

	@Override
	public Action getAction(final String actionName) {
		Action action = this.primary.getAction(actionName);
		if (action == null) {
			action = this.secondary.getAction(actionName);
		}
		return action;
	}

}
