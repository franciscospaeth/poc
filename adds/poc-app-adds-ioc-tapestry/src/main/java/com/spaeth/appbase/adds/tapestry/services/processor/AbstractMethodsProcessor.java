package com.spaeth.appbase.adds.tapestry.services.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class AbstractMethodsProcessor {

	public Class<? extends Annotation> expectsToBeAnnotateWith;

	public AbstractMethodsProcessor(final Class<? extends Annotation> expectsToBeAnnotateWith) {
		super();
		this.expectsToBeAnnotateWith = expectsToBeAnnotateWith;
	}

	public final void process(final Method method) {
		if (!method.isAnnotationPresent(expectsToBeAnnotateWith)) {
			return;
		}
		processInternal(method);
	}

	protected abstract void processInternal(Method method);

}
