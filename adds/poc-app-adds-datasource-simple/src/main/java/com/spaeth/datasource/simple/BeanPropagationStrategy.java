package com.spaeth.datasource.simple;

import org.apache.commons.beanutils.PropertyUtils;

import com.spaeth.datasource.DataSourceException;

public final class BeanPropagationStrategy implements PropagationStrategy {

	public static final BeanPropagationStrategy INSTANCE = new BeanPropagationStrategy();

	private BeanPropagationStrategy() {
	}

	@Override
	public void propagate(final SimpleDataSource dataSource, final Object oldValue, final Object newValue) {
		for (final String dsn : dataSource.getPropertyNames()) {
			final SimpleDataSource ds = (SimpleDataSource) dataSource.getProperty(dsn);
			try {
				Object valueToSet;
				if (newValue != null) {
					valueToSet = PropertyUtils.getProperty(newValue, dsn);
				} else {
					valueToSet = null;
				}
				ds.setCore(dataSource, valueToSet);
			} catch (final Exception e) {
				throw new DataSourceException(e);
			}
		}
	}

}
