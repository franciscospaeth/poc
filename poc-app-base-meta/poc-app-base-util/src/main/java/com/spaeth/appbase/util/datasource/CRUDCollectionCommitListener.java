package com.spaeth.appbase.util.datasource;

import java.io.Serializable;

import com.spaeth.appbase.core.datasource.CollectionDataSourceDiff;
import com.spaeth.appbase.core.datasource.event.CommitListener;
import com.spaeth.appbase.util.service.CRUDService;

public class CRUDCollectionCommitListener implements CommitListener {

	@SuppressWarnings("rawtypes")
	private final CRUDService crudService;

	public CRUDCollectionCommitListener(final CRUDService<? extends Serializable> crudService) {
		this.crudService = crudService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onPostCommit(final CommitEvent event) {
		CollectionDataSourceDiff diff = (CollectionDataSourceDiff) event.getDiff();
		for (Object o : diff.getRemovedElements()) {
			crudService.delete((Serializable) o);
		}
		for (Object o : diff.getAllElements()) {
			crudService.save((Serializable) o);
		}
	}

	@Override
	public void onPreCommit(final CommitEvent event) throws CommitException {
	}

}
