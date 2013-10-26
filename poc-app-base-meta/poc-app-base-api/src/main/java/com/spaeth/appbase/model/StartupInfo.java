package com.spaeth.appbase.model;

import java.io.Serializable;

/**
 * TODO: Needs to be renamed to something more meanful.
 * 
 * @author spaeth
 * 
 */
@SuppressWarnings("rawtypes")
public abstract class StartupInfo implements Callback<Serializable> {

	private final Class<?> expectedType;
	private final Callback callback;

	public StartupInfo() {
		this.callback = null;
		this.expectedType = null;
	}

	public <ExpectedResultType extends Serializable> StartupInfo(final Class<ExpectedResultType> resultType,
			final Callback<ExpectedResultType> callback) {
		this.expectedType = resultType;
		this.callback = callback;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void execute(final Serializable result) {
		// if no callback is configured just return
		if (callback == null) {
			return;
		}

		// if callback is configured but not expected type is returned
		if (result != null && !expectedType.isInstance(result)) {
			throw new IllegalArgumentException(expectedType + " is expected in order to invoke " + callback);
		}

		// otherwise, ready to execute
		callback.execute(result);
	}

}
