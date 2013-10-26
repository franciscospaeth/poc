package com.spaeth.appbase.adds.datasource.bean.builder;

import com.spaeth.appbase.adds.datasource.bean.BeanCollectionDataSource;
import com.spaeth.appbase.core.datasource.CreateTemplate;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.builder.AbstractDataSourceBuilder;
import com.spaeth.appbase.core.datasource.builder.CollectionDataSourceBuilder;
import com.spaeth.appbase.core.datasource.builder.DataSourceBuilder;

public abstract class BeanCollectionDataSourceBuilder<T extends BeanCollectionDataSourceBuilder<?>> extends AbstractDataSourceBuilder<T>
		implements CollectionDataSourceBuilder<T> {

	private CreateTemplate<DataSourceBuilder<?>, Object> createElementTemplate = null;

	public BeanCollectionDataSourceBuilder() {
		super();
	}

	public BeanCollectionDataSourceBuilder(final String name, final Class<?> type) {
		super(name, type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T setElementCreateTemplate(final CreateTemplate<DataSourceBuilder<?>, Object> createElementTemplate) {
		this.createElementTemplate = createElementTemplate;
		return (T) this;
	}

	@Override
	public BeanCollectionDataSource<?> build() {
		return (BeanCollectionDataSource<?>) super.build();
	}

	@Override
	protected void configure(final DataSource result) {
		super.configure(result);
		BeanCollectionDataSource<?> bvd = (BeanCollectionDataSource<?>) result;

		if (createElementTemplate != null) {
			bvd.setElementCreateTemplate(createElementTemplate);
		}

	}

}
