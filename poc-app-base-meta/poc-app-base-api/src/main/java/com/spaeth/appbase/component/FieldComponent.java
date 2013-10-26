package com.spaeth.appbase.component;

import com.spaeth.appbase.core.datasource.DataSource;

public interface FieldComponent {

	void setDataSource(DataSource valueDataSource);

	DataSource getDataSource();

	void setCaption(String caption);

	String getCaption();

	Object getValue();

	void setValue(Object value);

}
