package com.spaeth.appbase.core.service;

import com.spaeth.appbase.Application;
import com.spaeth.appbase.model.ViewContext;
import com.spaeth.appbase.model.ViewModel;
import com.spaeth.appbase.service.ApplicationController;

public class ViewContextBuilder {

	private final Application<?> application;
	private ApplicationController applicationController;
	private ViewModel viewModel;

	public ViewContextBuilder(final Application<?> application) {
		this.application = application;
	}

	public ViewContextBuilder with(final ApplicationController applicationController) {
		this.applicationController = applicationController;
		return this;
	}

	public ViewContextBuilder with(final ViewModel viewModel) {
		this.viewModel = viewModel;
		return this;
	}

	public ViewContext build() {
		return new ViewContext() {

			@Override
			public ViewModel getModel() {
				return ViewContextBuilder.this.viewModel;
			}

			@Override
			public ApplicationController getApplicationController() {
				return ViewContextBuilder.this.applicationController;
			}

			@Override
			public Application<?> getApplication() {
				return ViewContextBuilder.this.application;
			}

		};
	}

}
