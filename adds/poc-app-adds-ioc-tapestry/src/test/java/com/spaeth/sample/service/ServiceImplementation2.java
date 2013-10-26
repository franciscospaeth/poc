package com.spaeth.sample.service;

public class ServiceImplementation2 implements ServiceInterface {

	@Override
	public String method(final Object echoParameter) {
		return echoParameter + " wrong binding performed echo";
	}

}
