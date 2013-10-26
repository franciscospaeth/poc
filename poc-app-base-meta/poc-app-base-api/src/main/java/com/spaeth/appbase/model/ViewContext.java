package com.spaeth.appbase.model;

import com.spaeth.appbase.Application;
import com.spaeth.appbase.service.ApplicationController;

/**
 * Context within a view will have her life cycle.
 * 
 * @author "Francisco Spaeth (francisco.spaeth@gmail.com)"
 * 
 */
public interface ViewContext {

	Application<?> getApplication();

	ApplicationController getApplicationController();

	ViewModel getModel();

}
