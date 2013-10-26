package com.spaeth.appbase.core.datasource.validation;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.validator.GenericValidator;

import com.spaeth.appbase.core.datasource.DataSource;

public class StringSizeValidator extends AbstractValidator implements Validator {

	private final Integer minLength;
	private final Integer maxLength;

	public StringSizeValidator(final String message, final Integer minLength, final Integer maxLength) {
		super(message);
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	public StringSizeValidator(final String message, final Integer maxLength) {
		super(message);
		this.maxLength = maxLength;
		this.minLength = null;
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

		if (value == null) {
			return Collections.emptySet();
		}

		if ((this.maxLength != null) && !GenericValidator.maxLength(value, this.maxLength)) {
			return Collections.singleton(new ConstraintViolation(dataSource, getMessage()));
		}

		if ((this.minLength != null) && !GenericValidator.minLength(value, this.minLength)) {
			return Collections.singleton(new ConstraintViolation(dataSource, getMessage()));
		}

		return Collections.emptySet();
	}

}
