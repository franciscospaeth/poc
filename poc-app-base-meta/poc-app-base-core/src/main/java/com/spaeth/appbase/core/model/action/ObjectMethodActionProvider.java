package com.spaeth.appbase.core.model.action;

public class ObjectMethodActionProvider extends MethodActionProvider<Object> {

	public ObjectMethodActionProvider(final Object actionHolder) {
		super(actionHolder);
	}

	@Override
	protected Class<?> getActionHolderClass() {
		return this.actionHolder.getClass();
	}

}
