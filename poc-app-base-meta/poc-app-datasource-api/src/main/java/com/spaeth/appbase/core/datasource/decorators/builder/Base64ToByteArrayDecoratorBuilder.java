package com.spaeth.appbase.core.datasource.decorators.builder;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.builder.DecoratorBuilder;
import com.spaeth.appbase.core.datasource.decorators.Base64ToByteArrayDecorator;

public class Base64ToByteArrayDecoratorBuilder implements DecoratorBuilder {

	@Override
	public DataSource decorate(final DataSource decorate) {
		return new Base64ToByteArrayDecorator(decorate);
	}

}
