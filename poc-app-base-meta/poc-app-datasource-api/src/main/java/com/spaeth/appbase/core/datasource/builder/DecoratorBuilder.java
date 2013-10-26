package com.spaeth.appbase.core.datasource.builder;

import com.spaeth.appbase.core.datasource.DataSource;

public interface DecoratorBuilder {

	DataSource decorate(DataSource decorate);

}
