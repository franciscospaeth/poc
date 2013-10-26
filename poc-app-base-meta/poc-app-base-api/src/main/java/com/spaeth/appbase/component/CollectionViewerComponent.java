package com.spaeth.appbase.component;

import com.spaeth.appbase.core.datasource.CollectionDataSource;

public interface CollectionViewerComponent {

	void setCollectionDataSource(CollectionDataSource collectionDataSource);

	CollectionDataSource getCollectionDataSource();

}
