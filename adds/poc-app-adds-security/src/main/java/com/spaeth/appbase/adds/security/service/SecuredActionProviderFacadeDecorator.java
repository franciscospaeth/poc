package com.spaeth.appbase.adds.security.service;

import com.spaeth.appbase.core.security.service.SecurityPermissionService;
import com.spaeth.appbase.core.service.ActionProviderFacade;
import com.spaeth.appbase.model.Action;
import com.spaeth.appbase.service.ActionProvider;

public class SecuredActionProviderFacadeDecorator implements ActionProviderFacade {

	private ActionProviderFacade decorated;
	private SecurityPermissionService permissionService;

	public SecuredActionProviderFacadeDecorator(ActionProviderFacade decorator, SecurityPermissionService permissionService) {
		super();
		this.decorated = decorator;
		this.permissionService = permissionService;
	}

	@Override
	public Action getAction(String actionName) {
		return decorate(actionName, decorated.getAction(actionName));
	}

	@Override
	public Action getAction(ActionProvider transientActionProvider, String actionName) {
		return decorate(actionName, decorated.getAction(transientActionProvider, actionName));
	}

	private Action decorate(String actionName, Action action) {
		if (action == null) {
			throw new IllegalArgumentException(String.format("action named '%s' is not accessible", actionName));
		}
		return new SecuredActionDecorator(actionName, action, permissionService);
	}
	
}
