package com.spaeth.appbase.event;

import java.util.EventObject;

import com.spaeth.appbase.model.Action;

public class ActionUpdateEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private final Action action;

	public ActionUpdateEvent(final Object source, final Action action) {
		super(source);
		this.action = action;
	}

	public Action getAction() {
		return this.action;
	}

}
