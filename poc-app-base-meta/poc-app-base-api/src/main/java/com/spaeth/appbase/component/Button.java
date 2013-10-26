package com.spaeth.appbase.component;

import com.spaeth.appbase.component.api.IButton;
import com.spaeth.appbase.model.Action;

public class Button extends AbstractComponent<IButton> implements IButton {

	private static final long serialVersionUID = 1L;

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
	public void setAction(final Action action) {
		getDelegated().setAction(action);
	}

	@Override
	public void setVisible(final boolean visible) {
		getDelegated().setVisible(visible);
	}

	@Override
	public Action getAction() {
		return getDelegated().getAction();
	}

	@Override
	public void setParent(final ComponentContainer componentContainer) {
		getDelegated().setParent(componentContainer);
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
	public StreamProvider getIcon() {
		return getDelegated().getIcon();
	}

	@Override
	public void setIcon(final StreamProvider streamProvider) {
		getDelegated().setIcon(streamProvider);
	}

	@Override
	public SizeDefinition getSize() {
		return getDelegated().getSize();
	}

	@Override
	public void setSize(final SizeDefinition sizeDefinition) {
		getDelegated().setSize(sizeDefinition);
	}

}
