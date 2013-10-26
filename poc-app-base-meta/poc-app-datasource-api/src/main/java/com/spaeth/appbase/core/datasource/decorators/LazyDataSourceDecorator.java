package com.spaeth.appbase.core.datasource.decorators;

import org.apache.commons.lang3.ObjectUtils;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.DataSourceMeta;
import com.spaeth.appbase.core.datasource.builder.DataSourceBuilder;
import com.spaeth.appbase.core.datasource.decorators.builder.LazyDataSourceDecoratorBuilder;

public class LazyDataSourceDecorator<ProvidedType, KeyType> extends AbstractDataSourceDecorator<DataSource> {

	private final DataSourceContentProvider<ProvidedType, KeyType> dataSourceContentProvider;
	private final Class<ProvidedType> providedType;
	private DataSourceMeta meta = null;

	public LazyDataSourceDecorator(final DataSource decorated, final Class<ProvidedType> providedType,
			final DataSourceContentProvider<ProvidedType, KeyType> contentProvider) {
		super(decorated);
		this.dataSourceContentProvider = contentProvider;
		this.providedType = providedType;
		meta = new DataSourceMeta(decorated.getName(), providedType, decorated.getMeta().getServingDirective());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getModel() {
		return dataSourceContentProvider.load((KeyType) decorated.getModel());
	}

	@Override
	public Object get() {
		return getModel();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setModel(final Object value) {
		KeyType key = (KeyType) decorated.getModel();
		KeyType newKey = dataSourceContentProvider.store(key, (ProvidedType) value);
		if (!ObjectUtils.equals(key, newKey)) {
			decorated.setModel(newKey);
		}
	}

	@Override
	public void set(final Object value) {
		setModel(value);
	}

	@Override
	public Class<?> getType() {
		return providedType;
	}

	@Override
	public DataSourceMeta getMeta() {
		return meta;
	}

	@Override
	public DataSourceBuilder<?> getBuilder() {
		return decorate(super.getBuilder());
	}

	@Override
	protected DataSourceBuilder<?> decorate(final DataSourceBuilder<?> builder) {
		LazyDataSourceDecoratorBuilder<ProvidedType, KeyType> decorator = new LazyDataSourceDecoratorBuilder<ProvidedType, KeyType>();
		decorator.setProvidedType(providedType);
		decorator.setContentProvider(dataSourceContentProvider);
		builder.addDecorator(decorator);
		return builder;
	}

	@Override
	public String toString() {
		return dataSourceContentProvider.toString() + " lazied by " + this.getClass();
	}

}
