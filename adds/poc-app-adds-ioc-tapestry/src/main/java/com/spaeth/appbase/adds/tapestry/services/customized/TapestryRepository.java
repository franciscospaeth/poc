package com.spaeth.appbase.adds.tapestry.services.customized;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.ObjectLocator;

import com.spaeth.appbase.core.service.Repository;

public class TapestryRepository implements Repository {

	private final ObjectLocator objectLocator;

	public TapestryRepository(final ObjectLocator registry) {
		super();
		this.objectLocator = registry;
	}

	@Override
	public <M> M getInstance(final Class<M> clazz) {
		return this.objectLocator.getService(clazz);
	}

	@Override
	public <M> M getInstance(final Class<M> clazz, final Class<? extends Annotation> marker) {
		return this.objectLocator.getObject(clazz, new AnnotationProvider() {
			@SuppressWarnings("unchecked")
			@Override
			public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
				if (marker.equals(annotationClass)) {
					return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] { marker }, new InvocationHandler() {
						@Override
						public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
							return null;
						}
					});
				}
				return null;
			}
		});
	}

	@Override
	public <M> M newInstance(final Class<M> clazz) {
		return this.objectLocator.autobuild(clazz);
	}

	public ObjectLocator getObjectLocator() {
		return this.objectLocator;
	}

}
