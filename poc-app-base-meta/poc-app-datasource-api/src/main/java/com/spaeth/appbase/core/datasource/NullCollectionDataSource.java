package com.spaeth.appbase.core.datasource;

import java.util.Collection;

class NullCollectionDataSource extends NullDataSource implements CollectionDataSource {

	public static final CollectionDataSource INSTANCE = new NullCollectionDataSource();

	protected NullCollectionDataSource() {
	}

	@Override
	public DataSource getElement(final Object value) {
		return null;
	}

	@Override
	public Collection<DataSource> getElements() {
		return null;
	}

	@Override
	public DataSource addElement(final Object value) {
		return null;
	}

	@Override
	public void removeElement(final Object value) {
	}

	@Override
	public Integer size() {
		return null;
	}

	@Override
	public void clear() {
	}

	@Override
	public CollectionDataSourceDiff getDiff() {
		return CollectionDataSourceDiff.NO_DIFF;
	}

	@Override
	public CollectionElementStatus getElementStatus(final Object value) {
		return CollectionElementStatus.NOT_FOUND;
	}

}
