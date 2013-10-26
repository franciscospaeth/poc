package com.spaeth.appbase.core.datasource.validation;

import java.util.Collection;

public class ValidationResult {

	private final Collection<ConstraintViolation> violations;

	public ValidationResult(final Collection<ConstraintViolation> violations) {
		super();
		this.violations = violations;
	}

	public boolean isValid() {
		return this.violations.isEmpty();
	}

	public int violationCount() {
		return this.violations.size();
	}

	public Collection<ConstraintViolation> getViolations() {
		return this.violations;
	}

}
