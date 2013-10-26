package com.spaeth.appbase.component;

import java.io.Serializable;

import com.spaeth.appbase.component.api.IDataGridColumn;

public class DataGridColumn implements IDataGridColumn, Serializable {

	private static final long serialVersionUID = 1L;

	private final Class<?> type;
	private final String propertyName;
	private final String caption;

	public DataGridColumn(final Class<?> type, final String propertyName, final String caption) {
		this.type = type;
		this.propertyName = propertyName;
		this.caption = caption;
	}

	@Override
	public String getCaption() {
		return caption;
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public Class<?> getType() {
		return type;
	}

}
