package com.spaeth.appbase.core.service;

import com.spaeth.appbase.ViewPort;
import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.model.StartupInfo;
import com.spaeth.appbase.model.View;
import com.spaeth.appbase.model.ViewContext;
import com.spaeth.appbase.model.ViewModel;
import com.spaeth.appbase.service.ApplicationController;
import com.spaeth.appbase.service.ViewModelProvider;
import com.spaeth.appbase.service.ViewProvider;

public class ApplicationControllerImpl implements ApplicationController {

	private final ViewContextBuilderFactory viewContextBuilderFactory;
	private final ViewModelProvider viewModelProvider;
	private final ViewProvider<? extends View<?>> viewProvider;
	private final ViewPort viewPort;

	public ApplicationControllerImpl(final ViewContextBuilderFactory viewContextBuilderFactory,
			@Principal final ViewModelProvider viewModelProvider, @Principal final ViewProvider<? extends View<?>> viewProvider,
			@Principal final ViewPort viewPort) {
		super();
		this.viewContextBuilderFactory = viewContextBuilderFactory;
		this.viewModelProvider = viewModelProvider;
		this.viewProvider = viewProvider;
		this.viewPort = viewPort;
	}

	@Override
	public void process(final StartupInfo startupInfo) {
		if (startupInfo == null) {
			throw new IllegalArgumentException("startupInfo is null, therefore no process is possible");
		}

		final ViewModel modelProvided = this.viewModelProvider.getModelFor(startupInfo);
		final ViewContext context = this.viewContextBuilderFactory.newViewContextBuilder().with(this).with(modelProvided).build();

		final View<?> viewProvided = this.viewProvider.getViewFor(startupInfo, context);

		if (viewProvided == null) {
			throw new IllegalArgumentException("Startup info: " + startupInfo + " resulted in no view.");
		} else {
			viewProvided.initialize(modelProvided, startupInfo);
		}

		if (!this.viewPort.showView(viewProvided)) {
			throw new IllegalArgumentException("Startup info: " + startupInfo
					+ " resulted in a view that isn't shown by any of the view ports.");
		}
	}

}
