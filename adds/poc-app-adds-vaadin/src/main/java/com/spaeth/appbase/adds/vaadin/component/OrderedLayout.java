package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.component.api.IOrderedLayout;
import com.vaadin.ui.AbstractOrderedLayout;

public abstract class OrderedLayout extends ComponentContainer<AbstractOrderedLayout> implements IOrderedLayout {

	private static final long serialVersionUID = 1L;

	@Override
	public void setExpandRation(final float[] expandRation) {
		for (int i = 0; i < expandRation.length; i++) {
			getDelegated().setExpandRatio(getDelegated().getComponent(i), expandRation[i]);
		}
	}

	@Override
	public float[] getExpandRatio() {
		int count = getDelegated().getComponentCount();
		float[] fs = new float[count];
		for (int i = 0; i < count; i++) {
			fs[i] = getDelegated().getExpandRatio(getDelegated().getComponent(i));
		}
		return fs;
	}

	@Override
	public boolean isSpaced() {
		return getDelegated().isSpacing();
	}

	@Override
	public void setSpaced(final boolean spaced) {
		getDelegated().setSpacing(spaced);
	}

	@Override
	public void setMarginVisible(final boolean margin) {
		getDelegated().setMargin(margin);
	}

	@Override
	public boolean isMarginVisible() {
		return getDelegated().getMargin().getBitMask() > 0;
	}

}