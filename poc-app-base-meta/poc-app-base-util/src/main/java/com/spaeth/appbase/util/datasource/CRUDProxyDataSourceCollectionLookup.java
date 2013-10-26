package com.spaeth.appbase.util.datasource;

import java.io.Serializable;

import com.spaeth.appbase.core.datasource.DataSourceHolder;
import com.spaeth.appbase.core.datasource.ProxyDataSourceCollectionLookup;
import com.spaeth.appbase.util.service.CRUDService;

@SuppressWarnings("rawtypes")
public class CRUDProxyDataSourceCollectionLookup extends ProxyDataSourceCollectionLookup {

	private final CRUDService crudSupportService;

	public CRUDProxyDataSourceCollectionLookup(final DataSourceHolder dataSourceHolder,
			final CRUDService crudSupportService, final String... collectionDataSourceName) {
		super(dataSourceHolder, collectionDataSourceName);
		this.crudSupportService = crudSupportService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object synchronize(final Object object) {
		return crudSupportService.retrieve((Serializable) object);
	}

}
