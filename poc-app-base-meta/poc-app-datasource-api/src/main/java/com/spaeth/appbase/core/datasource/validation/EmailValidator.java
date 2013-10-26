package com.spaeth.appbase.core.datasource.validation;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.validator.GenericValidator;

import com.spaeth.appbase.core.datasource.DataSource;

public class EmailValidator extends AbstractValidator implements Validator {

	public EmailValidator(final String message) {
		super(message);
	}

	@Override
	public Collection<ConstraintViolation> validate(final DataSource dataSource) {
		if (dataSource.getType() != String.class) {
			return Collections.singleton(new ConstraintViolation(dataSource, ValidatorMessages.TYPE_IS_NOT_COMPATIBLE
					+ ": " + this.getClass().getSimpleName() + " <-> " + dataSource.getType()));
		}

		String value = null;
		try {
			value = (String) dataSource.getModel();
		} catch (final ClassCastException c) {
			return Collections.singleton(new ConstraintViolation(dataSource,
					ValidatorMessages.VALUE_IS_NOT_PASSIVE_OF_CONVERSION));
		}

		if (!GenericValidator.isEmail(value)) {
			return Collections.singleton(new ConstraintViolation(dataSource, getMessage()));
		}

		return Collections.emptySet();
	}

}
