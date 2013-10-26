package com.spaeth.appbase.core.datasource.validation;

public class AbstractValidator {

	private final String message;

	public AbstractValidator(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

}
