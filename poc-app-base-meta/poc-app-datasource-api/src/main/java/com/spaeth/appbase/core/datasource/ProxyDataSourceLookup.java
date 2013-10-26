package com.spaeth.appbase.core.datasource;

public interface ProxyDataSourceLookup<DataSourceResultType extends DataSource, ValueType> {

	DataSourceResultType getDataSource(ValueType value);

	DataSource getProviderDataSource();

}
