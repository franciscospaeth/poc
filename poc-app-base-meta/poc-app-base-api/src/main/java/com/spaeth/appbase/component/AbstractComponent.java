package com.spaeth.appbase.component;

import static com.spaeth.appbase.component.api.ComponentFactoryProvider.getComponentFactory;

public abstract class AbstractComponent<ComponentType extends Component> implements Component, WrappedComponent<ComponentType> {

	private static final long serialVersionUID = 1L;

	private final ComponentType delegated;

	@SuppressWarnings("unchecked")
	protected AbstractComponent(final Object... parameters) {
		delegated = (ComponentType) getComponentFactory().createComponent(getComponentClass(), parameters);
	}

	@Override
	public ComponentType getDelegated() {
		return delegated;
	}

	@Override
	public Class<? extends Component> getComponentClass() {
		return this.getClass();
	}

	@Override
	public Component getParent() {
		return delegated.getParent();
	}

}
