package com.spaeth.appbase.core.datasource.decorators;

import java.util.Collection;

import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.CollectionDataSourceDiff;
import com.spaeth.appbase.core.datasource.CollectionElementStatus;
import com.spaeth.appbase.core.datasource.DataSource;

public abstract class AbstractCollectionDataSourceDecorator extends AbstractDataSourceDecorator<CollectionDataSource> implements
		CollectionDataSource {

	public AbstractCollectionDataSourceDecorator(final CollectionDataSource decorated) {
		super(decorated);
	}

	@Override
	public DataSource getElement(final Object value) {
		return decorated.getElement(value);
	}

	@Override
	public Collection<DataSource> getElements() {
		return decorated.getElements();
	}

	@Override
	public DataSource addElement(final Object value) {
		return decorated.addElement(value);
	}

	@Override
	public void removeElement(final Object value) {
		decorated.removeElement(value);
	}

	@Override
	public CollectionElementStatus getElementStatus(final Object value) {
		return decorated.getElementStatus(value);
	}

	@Override
	public Integer size() {
		return decorated.size();
	}

	@Override
	public void clear() {
		decorated.clear();
	}

	@Override
	public CollectionDataSourceDiff getDiff() {
		return decorated.getDiff();
	}

}
