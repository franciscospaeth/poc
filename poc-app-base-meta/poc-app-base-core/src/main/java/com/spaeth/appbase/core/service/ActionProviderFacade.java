package com.spaeth.appbase.core.service;

import com.spaeth.appbase.model.Action;
import com.spaeth.appbase.service.ActionProvider;

public interface ActionProviderFacade {

	Action getAction(String actionName);

	Action getAction(ActionProvider transientActionProvider, String actionName);

}
