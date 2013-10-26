package com.spaeth.appbase.core.datasource.builder;

import com.spaeth.appbase.core.datasource.CreateTemplate;

public interface CollectionDataSourceBuilder<T extends CollectionDataSourceBuilder<?>> extends ValueDataSourceBuilder<T> {

	T setElementCreateTemplate(CreateTemplate<DataSourceBuilder<?>, Object> createElementTemplate);

}
