package com.spaeth.appbase.adds.datasource.bean.builder;

import com.spaeth.appbase.adds.datasource.bean.BeanSetDataSource;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.builder.SetDataSourceBuilder;

public class BeanSetDataSourceBuilder extends BeanCollectionDataSourceBuilder<BeanSetDataSourceBuilder> implements
		SetDataSourceBuilder<BeanSetDataSourceBuilder> {

	public BeanSetDataSourceBuilder() {
		super();
	}

	public BeanSetDataSourceBuilder(final String name, final Class<?> type) {
		super(name, type);
	}

	@Override
	protected BeanSetDataSource create(final DataSource owner) {
		return new BeanSetDataSource(owner, name, autoCalculateTypeIfNeeded(owner), initialValue);
	}

}
