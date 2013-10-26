package com.spaeth.appbase.core.datasource.decorators;

import java.io.Serializable;
import java.util.Map;

public class DefaultReferencedPageRequest<ResultType> extends AbstractPageRequest implements
		ReferencedPageRequest<ResultType> {

	private final ResultType referenced;

	public DefaultReferencedPageRequest(final ResultType referenced) {
		super();
		this.referenced = referenced;
	}

	public DefaultReferencedPageRequest(final ResultType referenced, final int pageSize) {
		super(pageSize);
		this.referenced = referenced;
	}

	public DefaultReferencedPageRequest(final ResultType referenced, final int pageSize,
			final Map<String, Serializable> parameters) {
		super(pageSize, parameters);
		this.referenced = referenced;
	}

	@Override
	public ResultType getReferenced() {
		return referenced;
	}

}
