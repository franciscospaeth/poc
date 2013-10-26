package com.spaeth.appbase.core.service;

import com.spaeth.appbase.model.Action;
import com.spaeth.appbase.service.ActionProvider;

public class ActionProviderFacadeActionProviderAdapter implements ActionProvider {

	private ActionProviderFacade actionProviderFacade;
	private ActionProvider transientActionProvider;

	public ActionProviderFacadeActionProviderAdapter(ActionProviderFacade actionProviderFacade,
			ActionProvider transientActionProvider) {
		super();
		this.actionProviderFacade = actionProviderFacade;
		this.transientActionProvider = transientActionProvider;
	}

	@Override
	public Action getAction(String actionName) {
		return actionProviderFacade.getAction(transientActionProvider, actionName);
	}

}
