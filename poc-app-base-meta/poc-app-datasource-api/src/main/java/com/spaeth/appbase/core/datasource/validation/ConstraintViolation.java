package com.spaeth.appbase.core.datasource.validation;

import com.spaeth.appbase.core.datasource.DataSource;

public class ConstraintViolation {

	private final DataSource source;
	private final String message;

	public ConstraintViolation(final DataSource source, final String message) {
		super();
		this.source = source;
		this.message = message;
	}

	public DataSource getSource() {
		return this.source;
	}

	public String getMessage() {
		return this.message;
	}

}
