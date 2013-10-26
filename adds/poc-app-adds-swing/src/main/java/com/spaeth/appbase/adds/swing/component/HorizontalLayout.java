package com.spaeth.appbase.adds.swing.component;

import javax.swing.JPanel;

import com.spaeth.appbase.adds.swing.component.customized.layout.HLayoutFlow;
import com.spaeth.appbase.adds.swing.component.customized.layout.HLayoutManager;
import com.spaeth.appbase.component.api.IOrderedLayout;

public class HorizontalLayout extends OrderedLayout implements IOrderedLayout {

	private static final long serialVersionUID = 1L;
	private HLayoutManager layout;

	public HorizontalLayout(final HorizontalLayoutFlow flow) {
	}

	@Override
	protected JPanel createDelegated() {
		layout = new HLayoutManager(HLayoutFlow.LEFT_TO_RIGHT, 0);
		return new JPanel(layout);
	}

	@Override
	public void setSpaced(final boolean spaced) {
		super.setSpaced(spaced);
		// layout.setGap(spaced ? 10 : 0);
	}

}