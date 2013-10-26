package com.spaeth.appbase.core.service;

import com.spaeth.appbase.model.Action;
import com.spaeth.appbase.service.ActionProvider;

/**
 * Used in order to allow the injection based on interface as
 * {@link ActionProvider} will be overused.
 * 
 * @author spaeth
 * 
 */
public class DefaultActionProviderFacade implements ActionProviderFacade {

	private final ActionProvider actionProvider;

	public DefaultActionProviderFacade(final ActionProvider actionProvider) {
		super();
		this.actionProvider = actionProvider;
	}

	@Override
	public Action getAction(final String actionName) {
		return actionProvider.getAction(actionName);
	}

	@Override
	public Action getAction(final ActionProvider transientActionProvider, final String actionName) {
		if (transientActionProvider != null) {
			Action a = transientActionProvider.getAction(actionName);
			if (a != null) {
				return a;
			}
		}
		return getAction(actionName);
	}

}
