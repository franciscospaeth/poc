package com.spaeth.appbase.core.datasource.event;

import java.util.EventObject;

import com.spaeth.appbase.core.datasource.DataSource;

public abstract class DataSourceEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private DataSource source;

	public DataSourceEvent(DataSource dataSource) {
		super(dataSource);
		source = dataSource;
	}

	@Override
	public DataSource getSource() {
		return source;
	}

}