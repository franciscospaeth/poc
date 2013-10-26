package com.spaeth.appbase.core.datasource.validation;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.ObjectUtils;

import com.spaeth.appbase.core.datasource.DataSource;

public class RegularExpressionValidator extends AbstractValidator implements Validator {

	private final String regularExpression;

	public RegularExpressionValidator(final String message, final String regularExpression) {
		super(message);
		this.regularExpression = regularExpression;
	}

	@Override
	public Collection<ConstraintViolation> validate(final DataSource dataSource) {
		if (dataSource.getType() != String.class) {
			return Collections.singleton(new ConstraintViolation(dataSource, ValidatorMessages.TYPE_IS_NOT_COMPATIBLE
					+ ": " + this.getClass().getSimpleName() + " <-> " + dataSource.getType()));
		}

		final String value = ObjectUtils.toString(dataSource.getModel(), "");

		if (!value.matches(this.regularExpression)) {
			return Collections.singleton(new ConstraintViolation(dataSource, getMessage()));
		}

		return Collections.emptySet();
	}

}
