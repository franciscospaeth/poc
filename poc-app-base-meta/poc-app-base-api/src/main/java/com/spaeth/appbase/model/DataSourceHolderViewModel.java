package com.spaeth.appbase.model;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.DataSourceHolder;
import com.spaeth.appbase.model.ViewModel;

public interface DataSourceHolderViewModel extends ViewModel, DataSourceHolder {

	public static class DataSourceHolderViewModelHelper {

		public static final DataSource getDataSource(final ViewModel viewModel) {
			if (!(viewModel instanceof DataSourceHolderViewModel)) {
				return null;
			}
			return ((DataSourceHolderViewModel) viewModel).getDataSource();
		}

	}

}
