package com.spaeth.appbase.util.service;

import java.util.List;

import com.spaeth.appbase.model.Action;

public class DefaultSystemTray implements SystemTray {

	private final List<Action> actions;

	public DefaultSystemTray(final List<Action> actions) {
		this.actions = actions;
	}

	@Override
	public List<Action> getActions() {
		return this.actions;
	}

}
