package com.spaeth.appbase.adds.swing;

import java.util.HashMap;
import java.util.Map;

import com.spaeth.appbase.adds.swing.component.constructor.ComponentConstructor;
import com.spaeth.appbase.component.Component;
import com.spaeth.appbase.component.api.ComponentFactoryProvider;

@SuppressWarnings("rawtypes")
public class ComponentFactory implements com.spaeth.appbase.component.api.ComponentFactory {

	private Map<Class, ComponentConstructor> constructors = new HashMap<Class, ComponentConstructor>();

	public ComponentFactory() {
		ComponentFactoryProvider.configure(this);
	}

	public ComponentFactory(final Map<Class, ComponentConstructor> constructors) {
		this();
		this.constructors = constructors;
	}

	@Override
	public <M extends Component> M createComponent(final Class<M> componentClass, final Object... parameters) {
		ComponentConstructor componentConstructor = constructors.get(componentClass);

		if (componentConstructor == null) {
			throw new IllegalArgumentException(String.format("%s is not supported by component factory", componentClass.getName()));
		}

		return componentConstructor.create(parameters);
	}

	public void setConstructors(final Map<Class, ComponentConstructor> constructors) {
		this.constructors = constructors;
	}

}
