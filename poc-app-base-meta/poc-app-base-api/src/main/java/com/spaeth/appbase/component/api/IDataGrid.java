package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.CollectionViewerComponent;
import com.spaeth.appbase.component.ColumnModel;
import com.spaeth.appbase.component.FieldComponent;
import com.spaeth.appbase.component.VisualComponent;

public interface IDataGrid extends FieldComponent, VisualComponent, CollectionViewerComponent {

	void setColumnModel(ColumnModel columnModel);

	ColumnModel getColumnModel();

}
