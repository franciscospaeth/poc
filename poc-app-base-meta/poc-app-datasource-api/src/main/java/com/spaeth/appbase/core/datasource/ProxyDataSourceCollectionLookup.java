package com.spaeth.appbase.core.datasource;

public class ProxyDataSourceCollectionLookup implements ProxyDataSourceLookup<DataSource, Object> {

	private final DataSourceHolder dataSourceHolder;
	private final String[] collectionDataSourceName;

	public ProxyDataSourceCollectionLookup(final DataSourceHolder dataSourceHolder,
			final String... collectionDataSourceName) {
		super();
		this.dataSourceHolder = dataSourceHolder;
		this.collectionDataSourceName = collectionDataSourceName;
	}

	@Override
	public DataSource getDataSource(final Object value) {
		if (value == null) {
			return null;
		}

		CollectionDataSource collectionDataSource = (CollectionDataSource) getProviderDataSource();

		DataSource result = collectionDataSource.getElement(value);

		// in case that was just deleted
		if (result == null) {
			return null;
		}

		CollectionElementStatus elementStatus = collectionDataSource.getElementStatus(value);
		if (elementStatus == CollectionElementStatus.NEW) {
			return result;
		}

		// synchronize object
		Object synchronizedVersion = synchronize(value);
		if (synchronizedVersion == null) {
			// if null, element can be deleted from initial collection and
			// return null for proxy
			collectionDataSource.removeElement(value);
			return null;
		} else if (synchronizedVersion != value && !result.isModified()) {
			// if a version is presented just reset it
			result.reset(synchronizedVersion);
		}

		return result;
	}

	protected Object synchronize(final Object object) {
		return object;
	}

	@Override
	public DataSource getProviderDataSource() {
		DataSource dataSource = dataSourceHolder.getDataSource();
		return dataSource.getDataSource(collectionDataSourceName);
	}

}
