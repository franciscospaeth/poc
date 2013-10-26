package com.spaeth.datasource.simple;

import com.spaeth.appbase.model.AccessPolicy;

public interface SimpleDataSourceBuilderHelper {

	Object getInitialValueFor(SimpleDataSource dataSource, String property);

	Class<?> getTypeFor(SimpleDataSource dataSource, String property);

	AccessPolicy getSecurityAccessibility(SimpleDataSource dataSource, String property);

}
