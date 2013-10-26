package com.spaeth.appbase.adds.swing.component;

import javax.swing.JComponent;

import com.spaeth.appbase.component.CollectionViewerComponent;
import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener;

abstract class VisualCollectionFieldComponent<ComponentClass extends JComponent> extends
		VisualFieldComponent<ComponentClass> implements CollectionViewerComponent, AccessPolicyChangeListener {

	private static final long serialVersionUID = 1L;

	private CollectionDataSource collectionDataSource;

	@Override
	public void setCollectionDataSource(final CollectionDataSource collectionDataSource) {
		if (collectionDataSource != null) {
			collectionDataSource.removeDataSourceAccessPolicyChangeListener(this);
			collectionDataSource.removeDataSourceValueChangeListener(this);
		}
		this.collectionDataSource = collectionDataSource;
		if (collectionDataSource != null) {
			collectionDataSource.addDataSourceValueChangeListener(this);
			collectionDataSource.addDataSourceAccessPolicyChangeListener(this);
		}
	}

	@Override
	public CollectionDataSource getCollectionDataSource() {
		return collectionDataSource;
	}

	@Override
	public void onAccessPolicyChanged(final AccessPolicyChangeEvent event) {
	}

}
