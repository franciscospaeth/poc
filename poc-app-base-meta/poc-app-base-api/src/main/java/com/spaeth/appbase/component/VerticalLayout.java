package com.spaeth.appbase.component;

import java.util.List;

import com.spaeth.appbase.component.api.IOrderedLayout;

public class VerticalLayout extends AbstractComponent<IOrderedLayout> implements IOrderedLayout {

	private static final long serialVersionUID = 1L;

	public VerticalLayout() {
		super(VerticalLayoutFlow.TOP_DOWN);
	}

	public VerticalLayout(final VerticalLayoutFlow flow) {
		super(flow);
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
	public void setExpandRation(final float[] expandRation) {
		getDelegated().setExpandRation(expandRation);
	}

	@Override
	public float[] getExpandRatio() {
		return getDelegated().getExpandRatio();
	}

	@Override
	public boolean isSpaced() {
		return getDelegated().isSpaced();
	}

	@Override
	public void setSpaced(final boolean spaced) {
		getDelegated().setSpaced(spaced);
	}

	@Override
	public void setMarginVisible(final boolean margin) {
		getDelegated().setMarginVisible(margin);
	}

	@Override
	public boolean isMarginVisible() {
		return getDelegated().isMarginVisible();
	}

}
