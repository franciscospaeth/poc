package com.spaeth.sample.service;

public class ServiceImplementation implements ServiceInterface {

	@Override
	public String method(final Object echoParameter) {
		return echoParameter + " was echoed";
	}

}
