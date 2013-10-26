package com.spaeth.appbase.component;

import com.spaeth.appbase.component.api.IFileField;
import com.spaeth.appbase.core.datasource.DataSource;

public class FileField extends AbstractComponent<IFileField> implements IFileField {

	private static final long serialVersionUID = 1L;

	public FileField() {
	}

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
	public Component getParent() {
		return getDelegated().getParent();
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
	public void setMessageIcon(final StreamProvider streamProvider) {
		getDelegated().setMessageIcon(streamProvider);
	}

	@Override
	public StreamProvider getMessageIcon() {
		return getDelegated().getMessageIcon();
	}

	@Override
	public void setNameDataSource(final DataSource dataSource) {
		getDelegated().setNameDataSource(dataSource);
	}

	@Override
	public DataSource getNameDataSource() {
		return getDelegated().getNameDataSource();
	}

}
