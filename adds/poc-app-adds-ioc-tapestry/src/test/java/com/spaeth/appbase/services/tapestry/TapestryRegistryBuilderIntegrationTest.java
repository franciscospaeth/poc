//package com.spaeth.appbase.services.tapestry;
//
//import java.lang.annotation.Annotation;
//import java.util.Collection;
//import java.util.Collections;
//
//import org.apache.tapestry5.ioc.AnnotationProvider;
//import org.apache.tapestry5.ioc.Registry;
//import org.junit.Assert;
//import org.junit.Test;
//
//import com.spaeth.appbase.adds.tapestry.services.deprecated.TapestryRegistryBuilder;
//import com.spaeth.appbase.core.marker.Principal;
//import com.spaeth.sample.ModuleRequires;
//import com.spaeth.sample.ModuleWithBinder;
//import com.spaeth.sample.ModuleWithBuilder;
//import com.spaeth.sample.ModuleWithConfiguration;
//import com.spaeth.sample.ModuleWithDecorator;
//import com.spaeth.sample.ModuleWithInjection;
//import com.spaeth.sample.ModuleWithOverriding;
//import com.spaeth.sample.MyMarker;
//import com.spaeth.sample.service.ServiceImplementation;
//import com.spaeth.sample.service.ServiceInjectInterface;
//import com.spaeth.sample.service.ServiceInterface;
//import com.spaeth.sample.service.ServiceInterface2;
//
//@SuppressWarnings({ "unchecked", "rawtypes" })
//public class TapestryRegistryBuilderIntegrationTest {
//
//	@Test
//	public void testRegularUseOfBuilders() {
//		final Registry registry = TapestryRegistryBuilder.buildAndStartup((Collection) Collections.singleton(ModuleWithBuilder.class));
//		Assert.assertNotNull(registry.getObject(ServiceInterface.class, annotationProvider()));
//		Assert.assertNotNull(registry.getObject(ServiceInterface.class, annotationProvider()));
//	}
//
//	protected AnnotationProvider annotationProvider() {
//		return new AnnotationProvider() {
//			@Override
//			@Principal
//			public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
//				try {
//					return this.getClass().getMethod("getAnnotation", Class.class).getAnnotation(annotationClass);
//				} catch (Exception e) {
//					throw new IllegalStateException(e);
//				}
//			}
//		};
//	}
//
//	@Test
//	public void testRegularUseOfBinding() {
//		final Registry registry = TapestryRegistryBuilder.buildAndStartup((Collection) Collections.singleton(ModuleWithBinder.class));
//		Assert.assertNotNull(registry.getService(ServiceImplementation.class));
//		Assert.assertNotNull(registry.getObject(ServiceInterface.class, annotationProvider()));
//	}
//
//	@Test
//	public void testUseOfBindingWithMarkers() {
//		final Registry registry = TapestryRegistryBuilder.buildAndStartup((Collection) Collections.singleton(ModuleWithBinder.class));
//		Assert.assertNotNull(registry.getService(ServiceImplementation.class));
//		Assert.assertNotNull(registry.getObject(ServiceInterface.class, annotationProvider()));
//		ServiceInterface obj = registry.getObject(ServiceInterface.class, new AnnotationProvider() {
//			@Override
//			@MyMarker
//			public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
//				try {
//					return this.getClass().getMethod("getAnnotation", Class.class).getAnnotation(annotationClass);
//				} catch (Exception e) {
//					throw new RuntimeException(e);
//				}
//			}
//		});
//		Assert.assertNotNull(obj);
//		ServiceInterface2 service2 = registry.getService(ServiceInterface2.class);
//		Assert.assertNotNull(service2);
//		Assert.assertNotNull(service2.getService());
//		Assert.assertSame(service2.getService(), obj);
//	}
//
//	@Test
//	public void testRegularUseOfConfiguration() {
//		final Registry registry = TapestryRegistryBuilder
//				.buildAndStartup((Collection) Collections.singleton(ModuleWithConfiguration.class));
//		Assert.assertNotNull(registry.getService(ServiceInterface.class).method("test"));
//	}
//
//	@Test
//	public void testRegularUseOfDecoration() {
//		final Registry registry = TapestryRegistryBuilder.buildAndStartup((Collection) Collections.singleton(ModuleWithDecorator.class));
//		Assert.assertNotNull(registry.getService(ServiceInterface.class).method("test"));
//		Assert.assertNotNull(registry.getService(ServiceInterface.class).method("test"));
//	}
//
//	@Test
//	public void testRegularUseOfRequires() {
//		final Registry registry = TapestryRegistryBuilder.buildAndStartup((Collection) Collections.singleton(ModuleRequires.class));
//		Assert.assertNotNull(registry.getService(ServiceImplementation.class).method("test"));
//		Assert.assertNotNull(registry.getObject(ServiceInterface.class, annotationProvider()).method("test"));
//	}
//
//	@Test
//	public void testRegularUseOfOverride() {
//		final Registry registry = TapestryRegistryBuilder.buildAndStartup((Collection) Collections.singleton(ModuleWithOverriding.class));
//		Assert.assertNotNull(registry.getObject(ServiceInterface.class, null).method("test"));
//		Assert.assertEquals("overrided: test was echoed", registry.getObject(ServiceInterface.class, null).method("test"));
//	}
//
//	@Test
//	public void testRegularUseOfInjection() {
//		final Registry registry = TapestryRegistryBuilder.buildAndStartup((Collection) Collections.singleton(ModuleWithInjection.class));
//		Assert.assertEquals("testetrue was echoed", registry.getObject(ServiceInjectInterface.class, null).t());
//	}
//
// }
