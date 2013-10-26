package com.spaeth.appbase.adds.swing.component.constructor;

public interface ComponentConstructor {

	<M> M create(Object... parameters);

}
