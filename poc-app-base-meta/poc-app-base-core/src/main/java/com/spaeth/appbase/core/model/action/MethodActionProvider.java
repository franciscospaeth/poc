package com.spaeth.appbase.core.model.action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.model.Action;
import com.spaeth.appbase.service.ActionProvider;

public abstract class MethodActionProvider<ActionHolderType> implements ActionProvider {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Map<String, Action> actions = new HashMap<String, Action>();
	protected final ActionHolderType actionHolder;

	public MethodActionProvider(final ActionHolderType actionHolder) {
		this.actionHolder = actionHolder;
		indexAnnotatedMethods();
	}

	private void indexAnnotatedMethods() {
		for (final Method m : getMethods()) {
			final com.spaeth.appbase.annotations.Action meta = m
					.getAnnotation(com.spaeth.appbase.annotations.Action.class);

			this.logger.debug("indexing action {}", meta);
			// final String caption = meta.caption();
			final String name = meta.name();
			// final String icon = meta.icon();
			final boolean enable = meta.enable();

			this.logger.debug("accessibility of action {} is {}", meta);
			final MethodAction action = new MethodAction(this.actionHolder, m);
			// action.setCaption(caption);
			action.setEnabled(enable);
			// action.setIcon(icon);

			this.logger.debug("adding action {} to indexed action list as result of {}", action, meta);
			this.actions.put(name, action);
		}
	}

	protected Method[] getMethods() {
		final ArrayList<Method> result = new ArrayList<Method>();

		final Method[] methods = getActionHolderClass().getMethods();
		for (final Method method : methods) {
			if (!classify(method)) {
				continue;
			}
			result.add(method);
		}

		return result.toArray(new Method[] {});
	}

	protected abstract Class<?> getActionHolderClass();

	protected boolean classify(final Method method) {
		return method.isAnnotationPresent(com.spaeth.appbase.annotations.Action.class);
	}

	@Override
	public Action getAction(final String actionName) {
		return this.actions.get(actionName);
	}

}
