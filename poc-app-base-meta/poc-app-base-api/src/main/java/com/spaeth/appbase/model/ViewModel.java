package com.spaeth.appbase.model;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.DataSourceHolder;
import com.spaeth.appbase.service.ActionProvider;

public interface ViewModel extends ActionProvider, DataSourceHolder {

	public static final ViewModel DUMMY = new ViewModel() {

		@Override
		public Action getAction(final String actionName) {
			return null;
		}

		@Override
		public void initialize(final StartupInfo startupInfo) {
		}

		@Override
		public DataSource getDataSource() {
			return null;
		}

	};

	/**
	 * Executed after its creation in order to setup its state.
	 * 
	 * @param startupInfo
	 */
	void initialize(StartupInfo startupInfo);

}
