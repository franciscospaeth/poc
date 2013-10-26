package com.spaeth.appbase.adds.vaadin.component.mediator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.spaeth.appbase.core.datasource.DataSource;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public final class VaadinDataSourceItemAdapter implements Item {

	private static final long serialVersionUID = 1L;

	private final DataSource dataSource;
	private final Map<DataSource, VaadinDataSourcePropertyAdapter> propertyAdapters = new HashMap<DataSource, VaadinDataSourcePropertyAdapter>();

	VaadinDataSourceItemAdapter(final DataSource dataSource) {
		if (dataSource == null) {
			throw new NullPointerException();
		}
		this.dataSource = dataSource;
	}

	@Override
	public Property getItemProperty(final Object id) {
		DataSource ds = dataSource.getDataSource(StringUtils.splitByWholeSeparatorPreserveAllTokens(String.valueOf(id),
				"."));
		if (!propertyAdapters.containsKey(ds)) {
			VaadinDataSourcePropertyAdapter property = new VaadinDataSourcePropertyAdapter();
			property.setDataSource(ds);
			propertyAdapters.put(ds, property);
		}
		return propertyAdapters.get(ds);
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		return dataSource.getDataSourceNames();
	}

	@Override
	public boolean addItemProperty(final Object id, final Property property) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItemProperty(final Object id) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}