package com.spaeth.appbase;

import java.util.HashMap;
import java.util.Map;

import com.spaeth.appbase.core.service.StateHolder;

public class StaticStateHolder implements StateHolder {

	private final Map<Class<?>, Object> holdedState = new HashMap<Class<?>, Object>();

	@Override
	public <M> M getObject(final Class<M> objectClass) {
		return objectClass.cast(holdedState.get(objectClass));
	}

	@Override
	public <M> void addObject(final M object, final Class<? super M> objectClass) {
		holdedState.put(objectClass, object);
	}

	@Override
	public <M> M removeObject(final Class<M> objectClass) {
		return objectClass.cast(holdedState.remove(objectClass));
	}

	@Override
	public void clear() {
		holdedState.clear();
	}

}
