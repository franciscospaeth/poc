package com.spaeth.appbase.service;

import com.spaeth.appbase.model.StartupInfo;
import com.spaeth.appbase.model.View;
import com.spaeth.appbase.model.ViewContext;

/**
 * Service responsible to create a view for a given startup info within a given
 * context.
 * 
 * @author "Francisco Spaeth (francisco.spaeth@gmail.com)"
 * 
 */
public interface ViewProvider<ResultViewType extends View<?>> {

	ResultViewType getViewFor(StartupInfo startupInfo, ViewContext viewContext);

}
