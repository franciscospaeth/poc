package com.spaeth.appbase.adds.datasource.bean;

import org.apache.commons.beanutils.PropertyUtils;

import com.spaeth.appbase.adds.datasource.bean.builder.BeanDataSourceBuilder;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.DataSourceMeta;
import com.spaeth.appbase.core.datasource.ServingDirective;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener.DataSourceValueChangedEvent;

public class BeanDataSource extends AbstractBeanDataSource<BeanDataSourceBuilder> implements DataSource {

	public BeanDataSource(final DataSource owner, final DataSourceMeta meta, final Object initialValue) {
		super(owner, meta, initialValue);
	}

	public BeanDataSource(final String name, final Class<?> type) {
		super(new DataSourceMeta(name, type, ServingDirective.VALUE));
	}

	public BeanDataSource(final String name, final Class<?> type, final Object initialValue) {
		super(null, new DataSourceMeta(name, type, ServingDirective.VALUE), initialValue);
	}

	public BeanDataSource(final DataSource owner, final String name, final Class<?> type) {
		super(owner, new DataSourceMeta(name, type, ServingDirective.VALUE));
	}

	public BeanDataSource(final DataSource owner, final String name, final Class<?> type, final Object initialValue) {
		super(owner, new DataSourceMeta(name, type, ServingDirective.VALUE), initialValue);
	}

	public BeanDataSource(final DataSourceMeta meta, final Object initialValue) {
		super(meta, initialValue);
	}

	@Override
	protected BeanDataSourceBuilder createBuilder() {
		return new BeanDataSourceBuilder();
	}

	@Override
	protected void propagateChangedDown(final DataSourceValueChangedEvent dsce) {
		for (DataSource ds : getDataSources()) {
			try {
				Object oldValue = null;
				Object newValue = null;
				if (dsce.getOldValue() != null) {
					oldValue = PropertyUtils.getProperty(dsce.getOldValue(), ds.getName());
				}
				if (dsce.getNewValue() != null) {
					newValue = PropertyUtils.getProperty(dsce.getNewValue(), ds.getName());
				}
				ds.process(new DataSourceValueChangedEvent(dsce.getCause(), dsce.getSource(), oldValue, newValue));
			} catch (Exception e) {
				System.err.println("error notifying child due to " + e.getMessage());
			}
		}
	}

}
