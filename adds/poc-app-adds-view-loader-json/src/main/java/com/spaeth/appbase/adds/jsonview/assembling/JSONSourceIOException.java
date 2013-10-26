package com.spaeth.appbase.adds.jsonview.assembling;

public class JSONSourceIOException extends JSONViewException {

	private static final long serialVersionUID = 1L;

	public JSONSourceIOException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public JSONSourceIOException(final String message) {
		super(message);
	}

	public JSONSourceIOException(final Throwable cause) {
		super(cause);
	}

}
