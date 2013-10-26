package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.Component;

public interface ComponentFactory {

	<M extends Component> M createComponent(Class<M> componentClass, Object ... parameters);
	
}
