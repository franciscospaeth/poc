package com.spaeth.appbase.component.stream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;

public class DataSourceStreamProvider extends AbstractStreamProvider implements ValueChangeListener {

	private final DataSource dataSource;

	public DataSourceStreamProvider(final DataSource dataSource) {
		if (dataSource.getType() != byte[].class) {
			throw new IllegalArgumentException(String.format(
					"in order to use a datasource on a stream provider, it needs to hold byte[], but %s provided", dataSource.getType()));
		}
		this.dataSource = dataSource;
		this.dataSource.addDataSourceValueChangeListener(this);
	}

	@Override
	protected InputStream createInputStream() {
		Object content = dataSource.get();
		if (content == null) {
			return new ByteArrayInputStream(new byte[] {});
		}
		return new ByteArrayInputStream((byte[]) content);
	}

	@Override
	public void onChange(final DataSourceValueChangedEvent event) {
		fireUpdateListeners();
	}

}
