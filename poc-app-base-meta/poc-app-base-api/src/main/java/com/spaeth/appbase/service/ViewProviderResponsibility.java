package com.spaeth.appbase.service;

import com.spaeth.appbase.model.StartupInfo;
import com.spaeth.appbase.model.ViewContext;

/**
 * Creates an object that defines if certain {@link StartupInfo} in a given
 * {@link ViewContext} shall be handle by the {@link ViewProvider} that ownes
 * this {@link ViewProviderResponsibility}.
 * 
 * Commonly the process to define if certain {@link StartupInfo} shall be handle
 * or not does not depends on the {@link ViewProvider} itself due this reason
 * this activity is delegated to a implementation of
 * {@link ViewProviderResponsibility}.
 * 
 * @author "Francisco Spaeth (francisco.spaeth@gmail.com)"
 * 
 */
public interface ViewProviderResponsibility {

	/**
	 * If the {@link ViewProvider} that owns this implementation shall handle
	 * the {@link StartupInfo} on the given {@link ViewContext}.
	 * 
	 * @param startupInfo
	 * @param viewContext
	 * @return
	 */
	boolean isResponsible(StartupInfo startupInfo, ViewContext viewContext);

}
