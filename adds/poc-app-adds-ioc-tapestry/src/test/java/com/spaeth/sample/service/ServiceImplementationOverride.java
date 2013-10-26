package com.spaeth.sample.service;

public class ServiceImplementationOverride extends ServiceImplementation {

	@Override
	public String method(final Object echoParameter) {
		return "overrided: " + super.method(echoParameter);
	}

}
