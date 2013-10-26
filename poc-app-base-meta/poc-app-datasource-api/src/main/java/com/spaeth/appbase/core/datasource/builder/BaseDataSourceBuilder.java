package com.spaeth.appbase.core.datasource.builder;

import com.spaeth.appbase.core.datasource.DataSource;

public interface BaseDataSourceBuilder<T extends BaseDataSourceBuilder<?>> {

	DataSource build(DataSource owner);

	DataSource build();

	T setName(String name);

	String getName();

}
