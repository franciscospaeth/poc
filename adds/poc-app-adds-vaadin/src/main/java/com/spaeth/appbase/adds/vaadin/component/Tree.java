package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.adds.vaadin.component.mediator.VaadinDataSourceContainerAdapter;
import com.spaeth.appbase.adds.vaadin.component.mediator.VaadinHierarchicalDataSource;
import com.spaeth.appbase.component.api.ITree;
import com.spaeth.appbase.core.datasource.CollectionDataSource;

public class Tree extends VisualCollectionFieldComponent<com.vaadin.ui.Tree> implements ITree {

	private static final long serialVersionUID = 1L;

	private String parentProperty;

	@Override
	protected com.vaadin.ui.Tree createDelegated() {
		com.vaadin.ui.Tree tree = new com.vaadin.ui.Tree();
		tree.setImmediate(true);
		return tree;
	}

	@Override
	public void setParentProperty(final String propertyName) {
		this.parentProperty = propertyName;
	}

	@Override
	public String getParentProperty() {
		return parentProperty;
	}

	@Override
	public void setCaptionProperty(final String propertyName) {
		getDelegated().setItemCaptionPropertyId(propertyName);
	}

	@Override
	public String getCaptionProperty() {
		Object propertyName = getDelegated().getItemCaptionPropertyId();
		if (propertyName == null) {
			return null;
		}
		return String.valueOf(propertyName);
	}

	@Override
	protected VaadinDataSourceContainerAdapter createCollectionDataSourceAdapter(final CollectionDataSource colDS) {
		return new VaadinHierarchicalDataSource(colDS, parentProperty);
	}

}
