package com.spaeth.appbase.core.datasource.validation;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.validator.GenericValidator;

import com.spaeth.appbase.core.datasource.DataSource;

public class IntegerValidator extends AbstractValidator implements Validator {

	private final Integer min;
	private final Integer max;

	public IntegerValidator(final String message, final Integer min, final Integer max) {
		super(message);
		this.min = min;
		this.max = max;
	}

	public IntegerValidator(final String message, final Integer max) {
		super(message);
		this.max = max;
		this.min = null;
	}

	@Override
	public Collection<ConstraintViolation> validate(final DataSource dataSource) {
		if (dataSource.getType() != Integer.class) {
			return Collections.singleton(new ConstraintViolation(dataSource, ValidatorMessages.TYPE_IS_NOT_COMPATIBLE
					+ ": " + this.getClass().getSimpleName() + " <-> " + dataSource.getType()));
		}

		Integer value = null;
		try {
			value = (Integer) dataSource.getModel();
		} catch (final ClassCastException c) {
			return Collections.singleton(new ConstraintViolation(dataSource,
					ValidatorMessages.VALUE_IS_NOT_PASSIVE_OF_CONVERSION));
		}

		if (value == null) {
			return Collections.emptySet();
		}

		if ((this.max != null) && !GenericValidator.maxValue(value, this.max)) {
			return Collections.singleton(new ConstraintViolation(dataSource, getMessage()));
		}

		if ((this.min != null) && !GenericValidator.minValue(value, this.min)) {
			return Collections.singleton(new ConstraintViolation(dataSource, getMessage()));
		}

		return Collections.emptySet();
	}

}