package com.spaeth.appbase.adds.swing.component;

import javax.swing.JPanel;

import com.spaeth.appbase.adds.swing.component.customized.layout.VLayoutFlow;
import com.spaeth.appbase.adds.swing.component.customized.layout.VLayoutManager;
import com.spaeth.appbase.component.api.IOrderedLayout;

public class VerticalLayout extends OrderedLayout implements IOrderedLayout {

	private static final long serialVersionUID = 1L;

	private final VerticalLayoutFlow flow;

	public VerticalLayout(final VerticalLayoutFlow flow) {
		this.flow = flow;
	}

	@Override
	protected JPanel createDelegated() {
		return new JPanel(new VLayoutManager(flow == VerticalLayoutFlow.TOP_DOWN ? VLayoutFlow.TOP_DOWN : VLayoutFlow.BOTTOM_UP, 0));
	}

}
