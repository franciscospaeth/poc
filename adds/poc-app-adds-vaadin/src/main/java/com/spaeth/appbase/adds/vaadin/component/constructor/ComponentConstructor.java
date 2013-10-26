package com.spaeth.appbase.adds.vaadin.component.constructor;

public interface ComponentConstructor {

	<M> M create(Object ... parameters);
	
}
