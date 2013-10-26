package com.spaeth.appbase.component;

import java.util.ArrayList;
import java.util.List;

import com.spaeth.appbase.component.api.IDataGridColumn;
import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.DataSource;

public class AutoColumnModel implements ColumnModel {

	@Override
	public List<IDataGridColumn> getColumns(final CollectionDataSource dataSource) {

		List<IDataGridColumn> result = new ArrayList<IDataGridColumn>();

		for (DataSource ds : dataSource.getDataSources()) {
			result.add(new DataGridColumn(ds.getType(), ds.getName(), ds.getName()));
		}

		return result;

	}

	protected String getCaption(final String name) {
		return name;
	}

}
