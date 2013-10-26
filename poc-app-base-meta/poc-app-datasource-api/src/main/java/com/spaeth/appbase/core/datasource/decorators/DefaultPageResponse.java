package com.spaeth.appbase.core.datasource.decorators;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class DefaultPageResponse<ResultType extends Collection<?>> implements PageResponse<ResultType> {

	private final int page;
	private final int pageSize;
	private final ResultType result;
	private final int total;
	private final Map<String, Serializable> parameters;

	public DefaultPageResponse(final int page, final int pageSize, final ResultType result, final int total,
			final Map<String, Serializable> parameters) {
		super();
		this.page = page;
		this.pageSize = pageSize;
		this.result = result;
		this.total = total;
		this.parameters = parameters;
	}

	@Override
	public int getPage() {
		return page;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public ResultType getResult() {
		return result;
	}

	@Override
	public int getTotal() {
		return total;
	}

	@Override
	public Map<String, Serializable> getParameters() {
		return parameters;
	}

}
