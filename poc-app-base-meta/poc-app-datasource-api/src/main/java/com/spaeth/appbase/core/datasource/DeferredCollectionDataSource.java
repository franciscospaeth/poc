package com.spaeth.appbase.core.datasource;

import java.util.Collection;

class DeferredCollectionDataSource extends DeferredDataSource implements CollectionDataSource {

	public DeferredCollectionDataSource(final ProxyDataSource referenceDataSource, final String[] path) {
		super(referenceDataSource, path);
		this.dataSource = NullCollectionDataSource.INSTANCE;
	}

	@Override
	protected CollectionDataSource getReferredDataSource() {
		DataSource result = null;

		DataSource realDataSource = referenceDataSource.getRealDataSource();

		if (realDataSource != null) {
			result = realDataSource.getDataSource(path);
		}

		if (result == null) {
			return NullCollectionDataSource.INSTANCE;
		}

		if (!(result instanceof CollectionDataSource)) {
			throw new IllegalStateException();
		}

		return (CollectionDataSource) result;
	}

	@Override
	public DataSource getElement(final Object value) {
		return getReferredDataSource().getElement(value);
	}

	@Override
	public Collection<DataSource> getElements() {
		return getReferredDataSource().getElements();
	}

	@Override
	public DataSource addElement(final Object value) {
		return getReferredDataSource().addElement(value);
	}

	@Override
	public void removeElement(final Object value) {
		getReferredDataSource().removeElement(value);
	}

	@Override
	public Integer size() {
		return getReferredDataSource().size();
	}

	@Override
	public void clear() {
		getReferredDataSource().clear();
	}

	@Override
	public CollectionDataSourceDiff getDiff() {
		return getReferredDataSource().getDiff();
	}

	@Override
	public CollectionElementStatus getElementStatus(final Object value) {
		return getReferredDataSource().getElementStatus(value);
	}

}
