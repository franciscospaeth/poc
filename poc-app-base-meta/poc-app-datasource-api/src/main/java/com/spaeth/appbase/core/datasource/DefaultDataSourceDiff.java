package com.spaeth.appbase.core.datasource;

import java.util.Collections;
import java.util.Map;

public class DefaultDataSourceDiff implements DataSourceDiff {

	private Object value = null;
	private Map<String, Object> propertiesNewValues = Collections.emptyMap();

	public DefaultDataSourceDiff(final Object newValue, final Map<String, Object> propertiesNewValues) {
		this.value = newValue;
		this.propertiesNewValues = propertiesNewValues;
	}

	@Override
	public int getDiffCount() {
		return (value != null ? 1 : 0) + propertiesNewValues.size();
	}

	@Override
	public String toString() {
		return "DefaultDataSourceDiff [value=" + value + ", propertiesNewValues=" + propertiesNewValues + "]";
	}

}
