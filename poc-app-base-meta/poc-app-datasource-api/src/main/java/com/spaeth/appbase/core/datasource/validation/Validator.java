package com.spaeth.appbase.core.datasource.validation;

import java.util.Collection;

import com.spaeth.appbase.core.datasource.DataSource;

public interface Validator {

	Collection<ConstraintViolation> validate(DataSource dataSource);

}
