package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.FieldComponent;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.VisualComponent;
import com.spaeth.appbase.core.datasource.DataSource;

public interface IFileField extends VisualComponent, FieldComponent {

	void setMessageIcon(StreamProvider streamProvider);

	StreamProvider getMessageIcon();

	void setNameDataSource(DataSource dataSource);

	DataSource getNameDataSource();

}
