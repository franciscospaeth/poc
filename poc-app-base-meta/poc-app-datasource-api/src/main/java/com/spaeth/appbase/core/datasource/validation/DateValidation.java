package com.spaeth.appbase.core.datasource.validation;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import com.spaeth.appbase.core.datasource.DataSource;

public class DateValidation extends AbstractValidator implements Validator {

	private final Date min;
	private final Date max;

	public DateValidation(final String message) {
		super(message);
		this.min = null;
		this.max = null;
	}

	public DateValidation(final String message, final Date min, final Date max) {
		super(message);
		this.min = min;
		this.max = max;
	}

	@Override
	public Collection<ConstraintViolation> validate(final DataSource dataSource) {
		if (dataSource.getType() != Date.class) {
			return Collections.singleton(new ConstraintViolation(dataSource, ValidatorMessages.TYPE_IS_NOT_COMPATIBLE
					+ ": " + this.getClass().getSimpleName() + " <-> " + dataSource.getType()));
		}

		Date value = null;
		try {
			value = (Date) dataSource.getModel();
		} catch (final ClassCastException c) {
			return Collections.singleton(new ConstraintViolation(dataSource,
					ValidatorMessages.VALUE_IS_NOT_PASSIVE_OF_CONVERSION));
		}

		if (value == null) {
			return Collections.emptySet();
		}

		if ((this.max != null) && this.max.after(value)) {
			return Collections.singleton(new ConstraintViolation(dataSource, getMessage()));
		}

		if ((this.min != null) && this.min.before(value)) {
			return Collections.singleton(new ConstraintViolation(dataSource, getMessage()));
		}

		return Collections.emptySet();
	}

}
