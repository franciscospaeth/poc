package com.spaeth.appbase.component;

import com.spaeth.appbase.component.api.IPasswordField;
import com.spaeth.appbase.core.datasource.DataSource;

public class PasswordField extends AbstractComponent<IPasswordField> implements IPasswordField {

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
	public void setVisible(final boolean visible) {
		getDelegated().setVisible(visible);
	}

	@Override
	public void setValue(final Object value) {
		getDelegated().setValue(value);
	}

}
