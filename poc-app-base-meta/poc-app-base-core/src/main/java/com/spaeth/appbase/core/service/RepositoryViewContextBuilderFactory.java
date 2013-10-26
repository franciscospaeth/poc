package com.spaeth.appbase.core.service;

import com.spaeth.appbase.Application;

public class RepositoryViewContextBuilderFactory implements ViewContextBuilderFactory {

	private final Repository repository;

	public RepositoryViewContextBuilderFactory(final Repository repository) {
		this.repository = repository;
	}

	@Override
	public ViewContextBuilder newViewContextBuilder() {
		return new ViewContextBuilder(repository.getInstance(Application.class));
	}
}
