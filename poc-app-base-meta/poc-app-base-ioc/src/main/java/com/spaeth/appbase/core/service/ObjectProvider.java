package com.spaeth.appbase.core.service;

public interface ObjectProvider {

	<T> T provide(Class<T> requiredClass, Repository repository);

}
