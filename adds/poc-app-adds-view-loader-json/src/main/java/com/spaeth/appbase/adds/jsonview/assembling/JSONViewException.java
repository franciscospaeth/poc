package com.spaeth.appbase.adds.jsonview.assembling;

public class JSONViewException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JSONViewException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public JSONViewException(final String message) {
		super(message);
	}

	public JSONViewException(final Throwable cause) {
		super(cause);
	}

}
