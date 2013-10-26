package com.spaeth.appbase.core.datasource.event;

import com.spaeth.appbase.core.datasource.DataSource;

public class DataSourceTerminationEvent extends DataSourceEvent {

	private static final long serialVersionUID = 1L;

	public DataSourceTerminationEvent(final DataSource dataSource) {
		super(dataSource);
	}

}
