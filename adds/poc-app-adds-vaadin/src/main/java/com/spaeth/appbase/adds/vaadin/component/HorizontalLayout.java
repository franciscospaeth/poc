package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.adds.vaadin.ComponentWrapperHelper;
import com.spaeth.appbase.component.DetacheableComponent;
import com.spaeth.appbase.component.api.IOrderedLayout;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;

public class HorizontalLayout extends OrderedLayout implements IOrderedLayout {

	private static final long serialVersionUID = 1L;

	private final HorizontalLayoutFlow flow;

	public HorizontalLayout(final HorizontalLayoutFlow flow) {
		this.flow = flow;
	}

	@Override
	protected AbstractOrderedLayout createDelegated() {
		return new com.vaadin.ui.HorizontalLayout();
	}

	@Override
	public void addComponent(final DetacheableComponent component) {
		super.addComponent(component);
		@SuppressWarnings("rawtypes")
		com.spaeth.appbase.adds.vaadin.component.DetacheableComponent unwrap = ComponentWrapperHelper.unwrap(component,
				com.spaeth.appbase.adds.vaadin.component.DetacheableComponent.class);

		com.vaadin.ui.Component vComponent = (Component) unwrap.getDelegated();

		Alignment addFlow = flow == HorizontalLayoutFlow.LEFT_TO_RIGHT ? Alignment.MIDDLE_LEFT : Alignment.MIDDLE_RIGHT;
		getDelegated().setComponentAlignment(vComponent, addFlow);
	}

}