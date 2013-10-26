package com.spaeth.appbase.component;

import java.util.List;

import com.spaeth.appbase.component.api.ILayout;
import com.spaeth.appbase.component.api.IScrollPanel;

public class ScrollPanel extends AbstractComponent<IScrollPanel> implements IScrollPanel {

	private static final long serialVersionUID = 1L;

	public ScrollPanel() {
		super();
	}

	public ScrollPanel(final ILayout layout) {
		super(layout);
	}

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
	public void addComponent(final DetacheableComponent component) {
		getDelegated().addComponent(component);
	}

	@Override
	public void removeComponent(final DetacheableComponent component) {
		getDelegated().removeComponent(component);
	}

	@Override
	public List<DetacheableComponent> getComponents() {
		return getDelegated().getComponents();
	}

}
