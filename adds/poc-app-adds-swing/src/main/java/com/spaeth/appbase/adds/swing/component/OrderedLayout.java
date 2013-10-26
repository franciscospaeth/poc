package com.spaeth.appbase.adds.swing.component;

import javax.swing.JPanel;

import com.spaeth.appbase.adds.swing.component.customized.layout.OrderedLayoutManager;
import com.spaeth.appbase.component.api.IOrderedLayout;

public abstract class OrderedLayout extends ComponentContainer<JPanel> implements IOrderedLayout {

	private static final long serialVersionUID = 1L;
	private float[] expandRatio;

	@Override
	public void setExpandRation(final float[] expandRatio) {
		this.expandRatio = expandRatio;
		for (int i = 0; i < expandRatio.length; i++) {
			OrderedLayoutManager<?> layout = (OrderedLayoutManager<?>) getDelegated().getLayout();
			layout.setExpandRatio(getDelegated().getComponent(i), expandRatio[i]);
		}
	}

	@Override
	public float[] getExpandRatio() {
		return expandRatio;
	}

	@Override
	public boolean isSpaced() {
		return true;
	}

	@Override
	public void setSpaced(final boolean spaced) {
	}

	@Override
	public void setMarginVisible(final boolean margin) {
	}

	@Override
	public boolean isMarginVisible() {
		return false;
	}

}