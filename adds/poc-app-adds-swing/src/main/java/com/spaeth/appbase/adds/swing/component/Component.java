package com.spaeth.appbase.adds.swing.component;

public abstract class Component<ComponentClass> implements com.spaeth.appbase.component.Component {

	private static final long serialVersionUID = 1L;

	private com.spaeth.appbase.component.Component parent;
	private ComponentClass delegated;

	public Component() {
		setDelegated(createDelegated());
	}

	protected abstract ComponentClass createDelegated();

	@Override
	public com.spaeth.appbase.component.Component getParent() {
		return parent;
	}

	void internalSetParent(final com.spaeth.appbase.component.Component parent) {
		this.parent = parent;
	}

	public ComponentClass getDelegated() {
		return delegated;
	}

	public <M> M getDelegated(final Class<M> expectedClass) {
		return expectedClass.cast(delegated);
	}

	final void setDelegated(final ComponentClass delegated) {
		this.delegated = delegated;
		onInitialize();
	}

	protected void onInitialize() {
	}

}
