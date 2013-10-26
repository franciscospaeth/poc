package com.spaeth.appbase.core.service;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Static implementation of {@link RepositoryBuilder} that expects a class with
 * a specific name. From this class the build method will be statically invoked
 * with the modules that shall be loaded and the expected return is a
 * {@link Repository}.
 * 
 * @author "Francisco Spaeth (francisco.spaeth@gmail.com)"
 * 
 */
public class RepositoryBuilder {

	private static final Logger log = LoggerFactory.getLogger(RepositoryBuilder.class);

	public static final String staticExpectedClassName = "com.spaeth.appbase.RepositoryBuilderImpl";

	public static FrontRepository build(final Collection<Class<? extends Module>> modules) {
		try {
			final Class<?> builder = ClassUtils.getClass(staticExpectedClassName);

			final Method method = MethodUtils.getAccessibleMethod(builder, "build", Collection.class);

			if ((method == null) || !Modifier.isStatic(method.getModifiers())) {
				final NoSuchMethodException ex = new NoSuchMethodException("builder class '" + staticExpectedClassName
						+ "' shall implement public static Repository build(Collection<Class<? extends Module>> modules) method.");
				log.error(ex.getMessage(), ex);
				throw ex;
			}

			if (!FrontRepository.class.isAssignableFrom(method.getReturnType())) {
				final NoSuchMethodException ex = new NoSuchMethodException("builder class '" + staticExpectedClassName
						+ "' shall present build method with '" + FrontRepository.class.getName() + "' result type but is presenting '"
						+ method.getReturnType().getClass().getName() + "' instead");
				log.error(ex.getMessage(), ex);
				throw ex;
			}

			// converting to object array
			for (final Class<? extends Module> m : modules) {
				if (!Module.class.isAssignableFrom(m)) {
					throw new IllegalArgumentException("module " + m.getName() + " provided to repository builder is not a module class");
				}
			}

			final Object result = method.invoke(null, modules);

			if (result == null) {
				throw new NullPointerException("repository returned by '" + staticExpectedClassName + "', was null");
			}

			return (FrontRepository) result;
		} catch (final ClassNotFoundException e) {
			final ClassNotFoundException ex = new ClassNotFoundException("builder class shall be implemented under: "
					+ staticExpectedClassName);
			log.error(ex.getMessage(), ex);
			throw new RuntimeException(ex);
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
