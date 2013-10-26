package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.adds.vaadin.ComponentWrapperHelper;
import com.spaeth.appbase.component.DetacheableComponent;
import com.spaeth.appbase.component.api.IOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;

public class VerticalLayout extends OrderedLayout implements IOrderedLayout {

	private static final long serialVersionUID = 1L;

	private final VerticalLayoutFlow flow;

	public VerticalLayout(final VerticalLayoutFlow flow) {
		this.flow = flow;
	}

	@Override
	public void addComponent(final DetacheableComponent component) {
		super.addComponent(component);
		@SuppressWarnings("rawtypes")
		com.spaeth.appbase.adds.vaadin.component.DetacheableComponent unwrap = ComponentWrapperHelper.unwrap(component,
				com.spaeth.appbase.adds.vaadin.component.DetacheableComponent.class);

		com.vaadin.ui.Component vComponent = (Component) unwrap.getDelegated();

		Alignment addFlow = flow == VerticalLayoutFlow.TOP_DOWN ? Alignment.TOP_LEFT : Alignment.BOTTOM_LEFT;
		getDelegated().setComponentAlignment(vComponent, addFlow);
	}

	@Override
	protected com.vaadin.ui.VerticalLayout createDelegated() {
		return new com.vaadin.ui.VerticalLayout();
	}

}
