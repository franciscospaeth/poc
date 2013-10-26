package com.spaeth.appbase.core.service;

import java.util.Map;

import com.spaeth.appbase.model.StartupInfo;
import com.spaeth.appbase.model.ViewModel;
import com.spaeth.appbase.service.ViewModelProvider;

public class ClassBasedRepositoryViewModelProvider implements ViewModelProvider {

	private final Repository repository;
	private Map<Class<? extends StartupInfo>, Class<? extends ViewModel>> handledClasses;

	public ClassBasedRepositoryViewModelProvider(Repository repository,
			Map<Class<? extends StartupInfo>, Class<? extends ViewModel>> handledClasses) {
		super();
		this.repository = repository;
		this.handledClasses = handledClasses;
	}

	@Override
	public ViewModel getModelFor(final StartupInfo startupInfo) {
		if (isResponsible(startupInfo)) {
			final ViewModel service = this.repository.newInstance(this.handledClasses.get(startupInfo.getClass()));
			service.initialize(startupInfo);
			return service;
		}
		return null;
	}

	protected boolean isResponsible(final StartupInfo startupInfo) {
		return this.handledClasses.containsKey(startupInfo.getClass());
	}

}
