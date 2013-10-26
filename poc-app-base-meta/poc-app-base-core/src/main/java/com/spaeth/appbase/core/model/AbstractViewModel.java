package com.spaeth.appbase.core.model;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.model.action.ObjectMethodActionProvider;
import com.spaeth.appbase.model.Action;
import com.spaeth.appbase.model.StartupInfo;
import com.spaeth.appbase.model.ViewModel;
import com.spaeth.appbase.service.ActionProvider;

public class AbstractViewModel implements ViewModel {

	private final ActionProvider actionProvider = new ObjectMethodActionProvider(this);
	private DataSource dataSource;

	@Override
	public final void initialize(final StartupInfo startupInfo) {
		this.dataSource = configureDataSource(startupInfo);
		onInitialized(startupInfo);
	}

	protected DataSource configureDataSource(final StartupInfo startupInfo) {
		return null;
	}

	protected void onInitialized(final StartupInfo startupInfo) {
	}

	@Override
	public Action getAction(final String actionName) {
		return this.actionProvider.getAction(actionName);
	}

	@Override
	public DataSource getDataSource() {
		return this.dataSource;
	}

	public DataSource getDataSource(final String... name) {
		return dataSource.getDataSource(name);
	}

	public <M> M getDataSource(final Class<M> expectedDataSsourceClass, final String... name) {
		DataSource loadedDataSource = dataSource.getDataSource(name);

		if (!expectedDataSsourceClass.isInstance(loadedDataSource)) {
			throw new IllegalStateException("data source addressed by " + name + " is not a " + expectedDataSsourceClass + " but a "
					+ dataSource.getClass());
		}

		return expectedDataSsourceClass.cast(loadedDataSource);
	}

}
