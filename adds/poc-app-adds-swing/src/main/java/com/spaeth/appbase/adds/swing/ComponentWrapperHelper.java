package com.spaeth.appbase.adds.swing;

import com.spaeth.appbase.component.AbstractComponent;
import com.spaeth.appbase.component.Component;
import com.spaeth.appbase.component.WrappedComponent;

public class ComponentWrapperHelper {

	private ComponentWrapperHelper() {
	}

	public static final <M extends Component> M unwrap(final Component component, final Class<M> expectedClass) {

		// expected is already unwrapped and will be returned as it was
		// presented
		if (expectedClass.isInstance(component)) {
			return expectedClass.cast(component);
		}

		// converts to a component wrapper from api
		Component delegated = null;
		if (component instanceof WrappedComponent<?>) {
			delegated = ((WrappedComponent<?>) component).getDelegated();
		} else {
			throw new IllegalArgumentException(String.format(
					"given argument must be the same as %s or a wrapper extending from %s", expectedClass,
					AbstractComponent.class));
		}

		// unwrapping the component from api wrapper
		if (!expectedClass.isInstance(delegated)) {
			throw new IllegalArgumentException(String.format("%s should be wrapping an instance of %s", component,
					expectedClass.getName()));
		}

		return expectedClass.cast(delegated);
	}

}
