package com.spaeth.sample.service;

import javax.inject.Inject;

import com.spaeth.sample.MyMarker;

public class ServiceImplementationWithMarker implements ServiceInterface2 {

	@MyMarker
	@Inject
	ServiceInterface serviceImplementation;

	@Override
	public String method(final Object echoParameter) {
		return echoParameter + " wrong binding performed echo";
	}

	@Override
	public ServiceInterface getService() {
		return serviceImplementation;
	}

}
