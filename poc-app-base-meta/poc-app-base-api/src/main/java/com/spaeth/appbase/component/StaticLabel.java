package com.spaeth.appbase.component;

import com.spaeth.appbase.component.api.IStaticLabel;

public class StaticLabel extends AbstractComponent<IStaticLabel> implements IStaticLabel {

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
	public void setText(final String text) {
		getDelegated().setText(text);
	}

	@Override
	public String getText() {
		return getDelegated().getText();
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
