package com.spaeth.appbase.core.marker;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public class MarkerUtils {

	private MarkerUtils() {
	}

	public static final boolean isMarker(final Class<? extends Annotation> clazz) {
		return clazz.getAnnotation(Marker.class) != null;
	}

	public static final Class<? extends Annotation> getFirstMarker(final AnnotatedElement clazz) {
		for (Annotation c : clazz.getAnnotations()) {
			if (c.annotationType().getAnnotation(Marker.class) != null) {
				return c.annotationType();
			}
		}
		return null;
	}

}
