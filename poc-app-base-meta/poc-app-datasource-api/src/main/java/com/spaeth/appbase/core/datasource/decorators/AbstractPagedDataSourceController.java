package com.spaeth.appbase.core.datasource.decorators;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public abstract class AbstractPagedDataSourceController<ResultType> implements PagedDataSourceController<ResultType> {

	@Override
	public PagedPageRequest createPageRequest(final int page, final int pageSize) {
		return new DefaultPagedPageRequest(page, pageSize, getParameters());
	}

	@Override
	public ReferencedPageRequest<ResultType> createPageRequest(final ResultType element, final int pageSize) {
		return new DefaultReferencedPageRequest<ResultType>(element, pageSize, getParameters());
	}

	protected Map<String, Serializable> getParameters() {
		return Collections.emptyMap();
	}

}
