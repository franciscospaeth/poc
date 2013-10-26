package com.spaeth.appbase.util.datasource.decorators;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.spaeth.appbase.core.datasource.decorators.AbstractPagedDataSourceController;
import com.spaeth.appbase.core.datasource.decorators.PagedDataSourceController;

public abstract class ParametrizedListDataSourceController<ElementType> extends
		AbstractPagedDataSourceController<ElementType> implements PagedDataSourceController<ElementType> {

	private final Map<String, Serializable> parameters = new HashMap<String, Serializable>();

	public boolean containsParameter(final String key) {
		return parameters.containsKey(key);
	}

	public Serializable getParameterValue(final String key) {
		return parameters.get(key);
	}

	public Serializable setParameterValue(final String key, final Serializable value) {
		return parameters.put(key, value);
	}

	public Serializable removeParameter(final String key) {
		return parameters.remove(key);
	}

	public void setAllParameterValues(final Map<? extends String, ? extends Serializable> m) {
		parameters.putAll(m);
	}

	public void clearParameters() {
		parameters.clear();
	}

	public Set<String> getParameterNames() {
		return parameters.keySet();
	}

}
