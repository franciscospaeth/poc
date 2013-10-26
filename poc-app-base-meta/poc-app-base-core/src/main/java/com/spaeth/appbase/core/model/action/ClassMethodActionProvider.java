package com.spaeth.appbase.core.model.action;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassMethodActionProvider extends MethodActionProvider<Class<?>> {

	public ClassMethodActionProvider(final Class<?> actionHolder) {
		super(actionHolder);
	}

	@Override
	protected boolean classify(final Method method) {
		return Modifier.isStatic(method.getModifiers()) && super.classify(method);
	}

	@Override
	protected Class<?> getActionHolderClass() {
		return this.actionHolder;
	}

}
