package com.spaeth.appbase.core.model.action;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.annotations.ActionParam;
import com.spaeth.appbase.event.ActionUpdateEvent;
import com.spaeth.appbase.model.ActionParameters;

public class MethodAction extends AbstractAction {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 1L;

	private final Method method;
	private final Object target;

	public MethodAction(final Object target, final Method method) {
		super();
		this.method = method;
		this.target = target;
	}

	@Override
	public void internalExecute(final ActionParameters actionParameters) {
		try {
			logger.info("executed method action '{}'", method);

			Class<?>[] parametersType = method.getParameterTypes();
			Object[] parameters = new Object[parametersType.length];

			Annotation[][] parameterAnnotations = this.method.getParameterAnnotations();

			for (int i = 0; i < parameterAnnotations.length; i ++) {
				Annotation[] a = parameterAnnotations[i];
				int p = Arrays.binarySearch(parameterAnnotations, ActionParam.class);
				if (p == -1) {
					continue;
				}
				ActionParam ap = (ActionParam) a[p];
				parameters[i] = actionParameters.getValue(ap.value(), parametersType[i]);
			}

			logger.debug("invoking '{}' with following parameters: {}", method, parameters);

			this.method.invoke(this.target, parameters);
		} catch (final RuntimeException e) {
			throw e;
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (final InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected ActionUpdateEvent createActionEvent() {
		return new ActionUpdateEvent(this.target, this);
	}

}