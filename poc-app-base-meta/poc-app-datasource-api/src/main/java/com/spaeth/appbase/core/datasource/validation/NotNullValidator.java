package com.spaeth.appbase.core.datasource.validation;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.validator.GenericValidator;

import com.spaeth.appbase.core.datasource.DataSource;

public class NotNullValidator extends AbstractValidator implements Validator {

	private final boolean nullOrBlank;

	public NotNullValidator(final String message) {
		super(message);
		this.nullOrBlank = false;
	}

	public NotNullValidator(final String message, final boolean nullOrBlank) {
		super(message);
		this.nullOrBlank = nullOrBlank;
	}

	@Override
	public Collection<ConstraintViolation> validate(final DataSource dataSource) {
		if (dataSource.getType() == String.class) {
			if ((this.nullOrBlank && GenericValidator.isBlankOrNull((String) dataSource.getModel()))
					|| (dataSource.getModel() == null)) {
				return Collections.singleton(new ConstraintViolation(dataSource, getMessage()));
			}
		} else {
			if (dataSource.getModel() == null) {
				return Collections.singleton(new ConstraintViolation(dataSource, getMessage()));
			}
		}

		return Collections.emptySet();
	}

}
