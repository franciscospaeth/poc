package com.spaeth.appbase.component;

import com.spaeth.appbase.component.api.ILabel;
import com.spaeth.appbase.core.datasource.DataSource;

public class Label extends AbstractComponent<ILabel> implements ILabel {

	private static final long serialVersionUID = 1L;

	@Override
	public void setDataSource(final DataSource valueDataSource) {
		getDelegated().setDataSource(valueDataSource);
	}

	@Override
	public Measure getWidth() {
		return getDelegated().getWidth();
	}

	@Override
	public void setWidth(final Measure width) {
		getDelegated().setWidth(width);
	}

	@Override
	public float getNormalizedWidth() {
		return getDelegated().getNormalizedWidth();
	}

	@Override
	public Measure getHeight() {
		return getDelegated().getHeight();
	}

	@Override
	public float getNormalizedHeight() {
		return getDelegated().getNormalizedHeight();
	}

	@Override
	public void setHeight(final Measure height) {
		getDelegated().setHeight(height);
	}

	@Override
	public DataSource getDataSource() {
		return getDelegated().getDataSource();
	}

	@Override
	public boolean isVisible() {
		return getDelegated().isVisible();
	}

	@Override
	public void setVisible(final boolean visible) {
		getDelegated().setVisible(visible);
	}

	@Override
	public void setParent(final ComponentContainer componentContainer) {
		getDelegated().setParent(componentContainer);
	}

	@Override
	public void setCaption(final String caption) {
		getDelegated().setCaption(caption);
	}

	@Override
	public String getCaption() {
		return getDelegated().getCaption();
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
	public void setTextSizeDefinition(final SizeDefinition size) {
		getDelegated().setTextSizeDefinition(size);
	}

	@Override
	public SizeDefinition getTextSizeDefinition() {
		return getDelegated().getTextSizeDefinition();
	}

}
