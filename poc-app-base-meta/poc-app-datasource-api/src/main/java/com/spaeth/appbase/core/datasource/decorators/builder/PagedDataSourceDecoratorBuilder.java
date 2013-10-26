package com.spaeth.appbase.core.datasource.decorators.builder;

import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.builder.DecoratorBuilder;
import com.spaeth.appbase.core.datasource.decorators.PagedDataSourceController;
import com.spaeth.appbase.core.datasource.decorators.PagedDataSourceDecorator;

public class PagedDataSourceDecoratorBuilder implements DecoratorBuilder {

	private PagedDataSourceController<?> pagedDataSourceController;
	private int pageSize = 15;

	@Override
	public CollectionDataSource decorate(final DataSource decorate) {
		if (!(decorate instanceof CollectionDataSource)) {
			throw new IllegalArgumentException("decorate parameter should be an CollectionDataSource");
		}
		return new PagedDataSourceDecorator(pagedDataSourceController, (CollectionDataSource) decorate, pageSize);
	}

	public PagedDataSourceDecoratorBuilder setPagedDataSourceController(
			final PagedDataSourceController<?> pagedDataSourceController) {
		this.pagedDataSourceController = pagedDataSourceController;
		return this;
	}

	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
	}

}
