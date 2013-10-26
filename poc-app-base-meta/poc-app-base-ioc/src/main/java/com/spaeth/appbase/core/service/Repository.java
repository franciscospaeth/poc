package com.spaeth.appbase.core.service;

import java.lang.annotation.Annotation;

public interface Repository {

	<M> M getInstance(Class<M> clazz);

	<M> M getInstance(Class<M> clazz, Class<? extends Annotation> marker);

	<M> M newInstance(Class<M> clazz);

}
