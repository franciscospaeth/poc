package com.spaeth.appbase.adds.datasource.bean;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.spaeth.appbase.adds.datasource.bean.builder.BeanSetDataSourceBuilder;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.DataSourceMeta;
import com.spaeth.appbase.core.datasource.SetDataSource;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener.DataSourceEventCause;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener.DataSourceValueChangedEvent;

public class BeanSetDataSource extends BeanCollectionDataSource<BeanSetDataSourceBuilder> implements SetDataSource {

	public BeanSetDataSource(final DataSource owner, final DataSourceMeta meta, final Object initialValue) {
		super(owner, meta, initialValue);
	}

	public BeanSetDataSource(final DataSource owner, final String name, final Class<?> type, final Object initialValue) {
		super(owner, name, type, initialValue);
	}

	public BeanSetDataSource(final DataSource owner, final String name, final Class<?> type) {
		super(owner, name, type, createNewValue(type));
	}

	public BeanSetDataSource(final DataSourceMeta meta, final Object initialValue) {
		super(meta, initialValue);
	}

	public BeanSetDataSource(final String name, final Class<?> type, final Object initialValue) {
		super(name, type, initialValue);
	}

	public BeanSetDataSource(final String name, final Class<?> type) {
		super(name, type, createNewValue(type));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object createNewValue(final Class<?> safeType) {
		return Collections.checkedSet(new LinkedHashSet(), safeType);
	}

	@Override
	public Object getModel() {
		// security
		checkReadPermission();

		// core
		return new LinkedHashSet<Object>(dataSourceMap.keySet());
	}

	@Override
	public void removeElement(final Object value) {
		// check access
		checkWritePermission();

		Set<?> oldValue = new HashSet<Object>(dataSourceMap.keySet());

		removeElementFromDataSourceMap(value);

		fireOnChangeListener(new DataSourceValueChangedEvent(DataSourceEventCause.COLLECTION_ELEMENT_REMOVED, this,
				Collections.unmodifiableSet(oldValue), Collections.unmodifiableSet(dataSourceMap.keySet())));

	}

	@Override
	public DataSource addElement(final Object value) {
		// check access
		checkWritePermission();

		// configure builder
		DataSource ds = createElementDataSourceBuilder(value);
		Set<?> oldValue = new HashSet<Object>(dataSourceMap.keySet());

		dataSourceMap.put(value, ds);

		fireOnChangeListener(new DataSourceValueChangedEvent(DataSourceEventCause.COLLECTION_ELEMENT_REMOVED, this,
				Collections.unmodifiableSet(oldValue), Collections.unmodifiableSet(dataSourceMap.keySet())));

		return ds;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Collection<?> processSetCollection(final Object value) {
		return Collections.checkedSet((Set) value, getType());
	}

	@Override
	protected BeanSetDataSourceBuilder createBuilder() {
		return new BeanSetDataSourceBuilder();
	}

}