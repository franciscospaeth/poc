package com.spaeth.appbase.adds.security.service;

import com.spaeth.appbase.core.security.service.SecurityPermissionService;
import com.spaeth.appbase.event.ActionUpdateEventListener;
import com.spaeth.appbase.model.Action;
import com.spaeth.appbase.model.ActionParameters;
import com.spaeth.appbase.model.ActionSecurityConstraint;

public class SecuredActionDecorator implements Action {

	private static final long serialVersionUID = 1L;

	private final Action decorated;
	private final SecurityPermissionService permissionService;
	private final String actionName;

	public SecuredActionDecorator(final String actionName, final Action decorated, final SecurityPermissionService permissionService) {
		super();
		this.actionName = actionName;
		this.decorated = decorated;
		this.permissionService = permissionService;
	}

	@Override
	public void refreshSecurityEvaluations() {
		decorated.refreshSecurityEvaluations();
	}

	@Override
	public boolean isEnabled() {
		return decorated.isEnabled() && permissionService.hasPermission(actionName);
	}

	@Override
	public void setEnabled(final boolean enable) {
		decorated.setEnabled(enable);
	}

	@Override
	public void execute(final ActionParameters parameters) {
		if (permissionService.hasPermission(actionName, parameters)) {
			decorated.execute(parameters);
		} else {
			throw new IllegalArgumentException(String.format("permission not granted to '%s' with following parameters: '%s'", actionName,
					parameters));
		}
	}

	@Override
	public void addUpdateListener(final ActionUpdateEventListener listener) {
		decorated.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(final ActionUpdateEventListener listener) {
		decorated.removeUpdateListener(listener);
	}

	@Override
	public void removeSecurityConstraint(final ActionSecurityConstraint securityConstraint) {
		decorated.removeSecurityConstraint(securityConstraint);
	}

	@Override
	public void addSecurityConstraint(final ActionSecurityConstraint securityConstraint) {
		decorated.addSecurityConstraint(securityConstraint);
	}

}