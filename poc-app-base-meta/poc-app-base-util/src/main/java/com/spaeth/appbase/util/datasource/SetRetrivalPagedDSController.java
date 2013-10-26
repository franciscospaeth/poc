package com.spaeth.appbase.util.datasource;

import java.io.Serializable;
import java.util.Collection;

import com.spaeth.appbase.core.datasource.decorators.AbstractPagedDataSourceController;
import com.spaeth.appbase.core.datasource.decorators.PageRequest;
import com.spaeth.appbase.core.datasource.decorators.PageResponse;
import com.spaeth.appbase.util.service.SetRetrievalService;

public class SetRetrivalPagedDSController<ManagedClass extends Serializable> extends
		AbstractPagedDataSourceController<ManagedClass> {

	private final SetRetrievalService<ManagedClass> retrievalSupportService;

	public SetRetrivalPagedDSController(final SetRetrievalService<ManagedClass> retrivalSupportService) {
		super();
		this.retrievalSupportService = retrivalSupportService;
	}

	@Override
	public PageResponse<? extends Collection<ManagedClass>> load(final PageRequest pageRequest) {
		return retrievalSupportService.load(pageRequest);
	}

}
