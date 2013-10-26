package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.VisualComponent;
import com.spaeth.appbase.core.datasource.decorators.PagedDataSourceDecorator;

public interface IPageStatus extends VisualComponent {

	void setPagedDataSource(PagedDataSourceDecorator pagedDataSource);

}
