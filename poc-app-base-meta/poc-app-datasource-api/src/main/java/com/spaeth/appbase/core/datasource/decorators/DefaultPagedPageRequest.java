package com.spaeth.appbase.core.datasource.decorators;

import java.io.Serializable;
import java.util.Map;

public class DefaultPagedPageRequest extends AbstractPageRequest implements PagedPageRequest {

	private final int page;

	public DefaultPagedPageRequest(final int page, final int pageSize) {
		super(pageSize);
		this.page = page;
	}

	public DefaultPagedPageRequest(final int page, final int pageSize, final Map<String, Serializable> parameters) {
		super(pageSize, parameters);
		this.page = page;
	}

	@Override
	public int getPage() {
		return page;
	}

}
