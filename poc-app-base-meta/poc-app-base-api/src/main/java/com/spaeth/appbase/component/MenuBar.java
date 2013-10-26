package com.spaeth.appbase.component;

import java.util.List;

import com.spaeth.appbase.component.api.IMenuBar;

public class MenuBar extends AbstractComponent<IMenuBar> implements IMenuBar {

	private static final long serialVersionUID = 1L;

	@Override
	public void setParent(final ComponentContainer componentContainer) {
		getDelegated().setParent(componentContainer);
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
	public boolean isVisible() {
		return getDelegated().isVisible();
	}

	@Override
	public void setVisible(final boolean visible) {
		getDelegated().setVisible(visible);
	}

	@Override
	public List<MenuItem> getOptions() {
		return getDelegated().getOptions();
	}

	@Override
	public void setOptions(final List<MenuItem> options) {
		getDelegated().setOptions(options);
	}

}
