package com.spaeth.reflection;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;

import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.Registry;
import org.junit.Assert;

import com.spaeth.appbase.adds.tapestry.services.TapestryReflectionRegistryBuilder;
import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.sample.service.ServiceImplementation;
import com.spaeth.sample.service.ServiceInterface;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Test {

	@org.junit.Test
	public void t() {
		boolean[][] t = new boolean[2][2];
		for (int i = 0; i < 2; i++) {
			System.out.println(t[i]);
		}
		boolean[][] a = t.clone();
		for (int i = 0; i < 2; i++) {
			System.out.println(a[i]);
		}
	}
	
	protected AnnotationProvider annotationProvider() {
		return new AnnotationProvider() {
			@Override
			@Principal
			public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
				try {
					return this.getClass().getMethod("getAnnotation", Class.class).getAnnotation(annotationClass);
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}
		};
	}

	@org.junit.Test
	public void testRegularUseOfBinding() {
		final Registry registry = TapestryReflectionRegistryBuilder.buildAndStartup((Collection) Collections
				.singleton(ModuleWithBinding.class));
		Assert.assertNotNull(registry.getService(ServiceImplementation.class).method("echo"));
		Assert.assertNotNull(registry.getObject(ServiceInterface.class, annotationProvider()).method("echo"));
	}

	@org.junit.Test
	public void testRegularUseOfBuilding() {
		final Registry registry = TapestryReflectionRegistryBuilder.buildAndStartup((Collection) Collections
				.singleton(ModuleWithBuilder.class));
		Assert.assertNotNull(registry.getService(ServiceImplementation.class).method("echo"));
		Assert.assertNotNull(registry.getObject(ServiceInterface.class, annotationProvider()).method("t"));
	}

	@org.junit.Test
	public void testRegularUseOfConfiguration() {
		final Registry registry = TapestryReflectionRegistryBuilder.buildAndStartup((Collection) Collections
				.singleton(ModuleWithConfiguration.class));
		Assert.assertNotNull(registry.getObject(ServiceInterface.class, annotationProvider()).method("t"));
	}

}
