package com.spaeth.appbase.core.datasource.decorators.builder;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.builder.DecoratorBuilder;
import com.spaeth.appbase.core.datasource.decorators.DataSourceContentProvider;
import com.spaeth.appbase.core.datasource.decorators.LazyDataSourceDecorator;

public class LazyDataSourceDecoratorBuilder<ProvidedType, KeyType> implements DecoratorBuilder {

	@SuppressWarnings("unchecked")
	private DataSourceContentProvider<ProvidedType, KeyType> dataSourceContentProvider = DataSourceContentProvider.DUMMY;
	private Class<ProvidedType> providedType = null;

	@Override
	public DataSource decorate(final DataSource decorate) {
		if (providedType == null) {
			throw new IllegalStateException(
					"a provided type should be configured for a lazyDataSourceDecorator build process");
		}
		if (dataSourceContentProvider == null) {
			throw new IllegalStateException(
					"a dataSourceContentProvider should be configured for a lazyDataSourceDecorator build process");
		}

		return new LazyDataSourceDecorator<ProvidedType, KeyType>(decorate, providedType, dataSourceContentProvider);
	}

	public LazyDataSourceDecoratorBuilder<ProvidedType, KeyType> setContentProvider(
			final DataSourceContentProvider<ProvidedType, KeyType> dataSourceContentProvider) {
		if (dataSourceContentProvider == null) {
			throw new IllegalArgumentException("lazyDataSourceContentProvider should not be null");
		}
		this.dataSourceContentProvider = dataSourceContentProvider;
		return this;
	}

	public LazyDataSourceDecoratorBuilder<ProvidedType, KeyType> setProvidedType(final Class<ProvidedType> providedType) {
		this.providedType = providedType;
		return this;
	}

}
