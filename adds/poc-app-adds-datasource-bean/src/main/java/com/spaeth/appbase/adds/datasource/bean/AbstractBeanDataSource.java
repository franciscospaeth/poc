package com.spaeth.appbase.adds.datasource.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.spaeth.appbase.core.datasource.AbstractDataSource;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.DataSourceDiff;
import com.spaeth.appbase.core.datasource.DataSourceMeta;
import com.spaeth.appbase.core.datasource.DataSourceUtils;
import com.spaeth.appbase.core.datasource.DefaultDataSourceDiff;
import com.spaeth.appbase.core.datasource.builder.AbstractDataSourceBuilder;
import com.spaeth.appbase.core.datasource.event.CommitListener.CommitEvent;
import com.spaeth.appbase.core.datasource.event.CommitListener.CommitException;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener.DataSourceEventCause;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener.DataSourceValueChangedEvent;

public abstract class AbstractBeanDataSource<T extends AbstractDataSourceBuilder<?>> extends AbstractDataSource<T> {

	private Object value;
	private Object initialValue;
	private boolean executingSet = false;

	public AbstractBeanDataSource(final DataSourceMeta meta) {
		super(meta);
	}

	public AbstractBeanDataSource(final DataSource owner, final DataSourceMeta meta) {
		super(owner, meta);
	}

	public AbstractBeanDataSource(final DataSourceMeta meta, final Object initialValue) {
		super(meta);
		this.initialValue = initialValue;
	}

	public AbstractBeanDataSource(final DataSource owner, final DataSourceMeta meta, final Object initialValue) {
		super(owner, meta);
		this.initialValue = initialValue;
	}

	protected void executeSet(final DataSource eventGenerator, final Object value, final DataSourceEventCause cause) {
		if (executingSet) {
			return;
		}
		try {
			executingSet = true;
			// changing
			final Object oldValue = this.value;
			this.value = value;

			if (DataSourceEventCause.RESET == cause || DataSourceEventCause.COMMIT == cause) {
				this.initialValue = value;
			}

			fireOnChangeListener(new DataSourceValueChangedEvent(cause, eventGenerator, oldValue, value));
		} finally {
			executingSet = false;
		}
	}

	@Override
	public void createNew() {
		super.createNew();
		this.initialValue = value;
	}

	@Override
	protected void setInternal(final DataSource eventGenerator, final Object value) {
		if (ObjectUtils.equals(value, this.value)) {
			return;
		}

		executeSet(eventGenerator, value, DataSourceEventCause.SET);
	}

	@Override
	public void commit() {
		if (isModified() && validate().isEmpty()) {
			DefaultDataSourceDiff dsDiff = (DefaultDataSourceDiff) getDiff();

			// TODO trigger before commit here with referring to value
			CommitEvent event = new CommitEvent(this, dsDiff);
			try {
				fireOnPreCommitListener(event);
			} catch (CommitException e) {
				return;
			}

			ArrayList<DataSource> commitedDSs = new ArrayList<DataSource>();

			// first: commit all hanging stuff due to autocreate and so on
			for (DataSource ds : getDataSources()) {
				if (ds.isModified()) {
					commitedDSs.add(ds);
					ds.commit();
				}
			}
			// second: set it to the current value
			for (DataSource ds : commitedDSs) {
				try {
					PropertyUtils.setProperty(value, ds.getName(), ds.getCoreDataSource().getModel());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

			fireOnPostCommitListener(event);

			// third: set the initialValue as value, no notifications should be
			// necessary, as this version represents exactly state from
			// datasources
			reset(value);

		}
	}

	@Override
	protected void processDataSourceValueChangeEvent(final DataSourceValueChangedEvent event) {
		super.processDataSourceValueChangeEvent(event);
		if (DataSourceUtils.isSuperior(event.getSource(), this)) {
			executeSet(event.getSource(), event.getNewValue(), event.getCause());
		}
	}

	@Override
	public void reset() {
		executeSet(this, initialValue, DataSourceEventCause.RESET);
	}

	@Override
	public void reset(final Object value) {
		executeSet(this, value, DataSourceEventCause.RESET);
	}

	@Override
	public boolean isModified() {
		boolean modified = this.value != this.initialValue;

		if (this.value == null) {
			return modified;
		}

		modified = !this.value.equals(this.initialValue);

		if (modified) {
			return true;
		}

		for (DataSource ds : getDataSources()) {
			if (ds.isModified()) {
				return true;
			}
		}

		return false;

	}

	@Override
	protected Object getInternal() {
		return value;
	}

	@Override
	protected void configureBuilder(final T createdBuilder) {
		super.configureBuilder(createdBuilder);
		createdBuilder.setInitialValue(initialValue);
	}

	@Override
	public DataSourceDiff getDiff() {
		Object newValue = value != initialValue ? value : null;
		Map<String, Object> propertiesChanges = new HashMap<String, Object>();
		for (DataSource ds : getDataSources()) {
			if (ds.isModified()) {
				propertiesChanges.put(ds.getName(), ds.getModel());
			}
		}
		return new DefaultDataSourceDiff(newValue, propertiesChanges);
	}

}