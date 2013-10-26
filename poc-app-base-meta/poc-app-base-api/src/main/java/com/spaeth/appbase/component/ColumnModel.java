package com.spaeth.appbase.component;

import java.util.List;

import com.spaeth.appbase.component.api.IDataGridColumn;
import com.spaeth.appbase.core.datasource.CollectionDataSource;

public interface ColumnModel {

	List<IDataGridColumn> getColumns(CollectionDataSource dataSource);

}
