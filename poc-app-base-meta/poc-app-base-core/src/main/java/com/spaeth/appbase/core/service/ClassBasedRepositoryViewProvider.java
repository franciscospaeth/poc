package com.spaeth.appbase.core.service;

import java.util.Map;

import javax.inject.Inject;

import com.spaeth.appbase.model.StartupInfo;
import com.spaeth.appbase.model.View;
import com.spaeth.appbase.model.ViewContext;
import com.spaeth.appbase.service.ViewProvider;

/**
 * Implementation of {@link ViewProvider} that relies the activity to
 * instantiate the view to the {@link Repository}, taking advantages of IOC
 * (i.e. {@link Inject}).
 * 
 * @author "Francisco Spaeth (francisco.spaeth@gmail.com)"
 * 
 */
public class ClassBasedRepositoryViewProvider implements ViewProvider<View<?>> {

	private final Repository repository;
	private final Map<Class<? extends StartupInfo>, Class<? extends View<?>>> handledClasses;

	public ClassBasedRepositoryViewProvider(final Repository repository,
			final Map<Class<? extends StartupInfo>, Class<? extends View<?>>> handledClasses) {
		this.repository = repository;
		this.handledClasses = handledClasses;
	}

	@Override
	public View<?> getViewFor(final StartupInfo startupInfo, final ViewContext viewContext) {
		if (startupInfo == null) {
			throw new IllegalArgumentException("startupInfo should not be null");
		}

		if (isResponsible(startupInfo, viewContext)) {
			return this.repository.newInstance(handledClasses.get(startupInfo.getClass()));
		}
		return null;
	}

	protected boolean isResponsible(final StartupInfo startupInfo, final ViewContext viewContext) {
		return this.handledClasses.containsKey(startupInfo.getClass());
	}

}
