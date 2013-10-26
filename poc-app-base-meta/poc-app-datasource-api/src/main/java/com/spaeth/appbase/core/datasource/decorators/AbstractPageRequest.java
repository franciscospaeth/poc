package com.spaeth.appbase.core.datasource.decorators;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public abstract class AbstractPageRequest implements PageRequest {

	private static final int DEFAULT_DATASOURCE_PAGE_SIZE = 15;
	protected final int pageSize;
	protected final Map<String, Serializable> parameters;

	public AbstractPageRequest() {
		super();
		pageSize = DEFAULT_DATASOURCE_PAGE_SIZE;
		parameters = Collections.emptyMap();
	}

	public AbstractPageRequest(final int pageSize) {
		super();
		this.pageSize = pageSize;
		parameters = Collections.emptyMap();
	}

	public AbstractPageRequest(final int pageSize, final Map<String, Serializable> parameters) {
		super();
		this.pageSize = pageSize;
		this.parameters = parameters;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public Map<String, Serializable> getParameters() {
		return Collections.unmodifiableMap(parameters);
	}

}