package com.spaeth.appbase.component;

public class CompositeComponent<ExtendedComponentType extends Component> implements
		WrappedComponent<ExtendedComponentType> {

	private static final long serialVersionUID = 1L;

	private ExtendedComponentType root;

	public CompositeComponent() {
	}

	protected final void initialize(final ExtendedComponentType root) {
		if (root == null || !(root instanceof WrappedComponent)) {
			throw new IllegalArgumentException("root component needs to be an instance of " + WrappedComponent.class);
		}

		if (this.root != null) {
			throw new IllegalStateException("root component was already set");
		}

		checkInitialization(root);

		this.root = root;
	}

	protected void checkInitialization(final ExtendedComponentType root) {
	}

	private void checkRootComponentInitialized() {
		if (root == null) {
			throw new IllegalStateException("root component was't initialized yet");
		}
	}

	@Override
	public final Component getParent() {
		checkRootComponentInitialized();
		return root.getParent();
	}

	@SuppressWarnings("unchecked")
	@Override
	public final Class<? extends Component> getComponentClass() {
		checkRootComponentInitialized();
		return WrappedComponent.class.cast(root).getComponentClass();
	}

	@SuppressWarnings("unchecked")
	@Override
	public final ExtendedComponentType getDelegated() {
		checkRootComponentInitialized();
		return (ExtendedComponentType) WrappedComponent.class.cast(root).getDelegated();
	}

	protected ExtendedComponentType getRoot() {
		return root;
	}

}
