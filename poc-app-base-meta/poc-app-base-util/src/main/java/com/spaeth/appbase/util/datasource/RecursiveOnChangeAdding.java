package com.spaeth.appbase.util.datasource;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.DataSourceVisitor;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;

public class RecursiveOnChangeAdding implements DataSourceVisitor {

	private final ValueChangeListener onChangeListener;

	public RecursiveOnChangeAdding(final ValueChangeListener onChangeListener) {
		super();
		this.onChangeListener = onChangeListener;
	}

	@Override
	public void visit(final DataSource dataSource) {
		dataSource.addDataSourceValueChangeListener(onChangeListener);
		for (DataSource ds : dataSource.getDataSources()) {
			ds.accept(this);
		}
	}

}
