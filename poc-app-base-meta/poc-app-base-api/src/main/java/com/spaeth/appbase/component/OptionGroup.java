package com.spaeth.appbase.component;

import com.spaeth.appbase.component.api.IOptionGroup;
import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.DataSource;

public class OptionGroup extends AbstractComponent<IOptionGroup> implements IOptionGroup {

	private static final long serialVersionUID = 1L;

	@Override
	public Measure getWidth() {
		return getDelegated().getWidth();
	}

	@Override
	public void setParent(final ComponentContainer componentContainer) {
		getDelegated().setParent(componentContainer);
	}

	@Override
	public float getNormalizedWidth() {
		return getDelegated().getNormalizedWidth();
	}

	@Override
	public void setDataSource(final DataSource valueDataSource) {
		getDelegated().setDataSource(valueDataSource);
	}

	@Override
	public void setWidth(final Measure width) {
		getDelegated().setWidth(width);
	}

	@Override
	public Measure getHeight() {
		return getDelegated().getHeight();
	}

	@Override
	public DataSource getDataSource() {
		return getDelegated().getDataSource();
	}

	@Override
	public float getNormalizedHeight() {
		return getDelegated().getNormalizedHeight();
	}

	@Override
	public void setCollectionDataSource(final CollectionDataSource collectionDataSource) {
		getDelegated().setCollectionDataSource(collectionDataSource);
	}

	@Override
	public void setCaption(final String caption) {
		getDelegated().setCaption(caption);
	}

	@Override
	public void setHeight(final Measure height) {
		getDelegated().setHeight(height);
	}

	@Override
	public String getCaption() {
		return getDelegated().getCaption();
	}

	@Override
	public boolean isVisible() {
		return getDelegated().isVisible();
	}

	@Override
	public Object getValue() {
		return getDelegated().getValue();
	}

	@Override
	public void setValue(final Object value) {
		getDelegated().setValue(value);
	}

	@Override
	public void setVisible(final boolean visible) {
		getDelegated().setVisible(visible);
	}

	@Override
	public CollectionDataSource getCollectionDataSource() {
		return getDelegated().getCollectionDataSource();
	}

	@Override
	public boolean isMultiSelect() {
		return getDelegated().isMultiSelect();
	}

	@Override
	public void setMultiSelect(final boolean multiSelect) {
		getDelegated().setMultiSelect(multiSelect);
	}

	@Override
	public void setCaptionProperty(final String propertyName) {
		getDelegated().setCaptionProperty(propertyName);
	}

	@Override
	public String getCaptionProperty() {
		return getDelegated().getCaptionProperty();
	}

}
