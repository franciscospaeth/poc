package com.spaeth.appbase.adds.security.model;

import com.spaeth.appbase.model.Action;

public class ActionSecurityContext {

	private String actionName;
	private Action action;
	private Object[] parameters;

	public ActionSecurityContext(String actionName, Action action, Object[] parameters) {
		super();
		this.actionName = actionName;
		this.action = action;
		this.parameters = parameters;
	}

	public String getActionName() {
		return actionName;
	}

	public Action getAction() {
		return action;
	}

	public Object[] getParameters() {
		return parameters;
	}

}
