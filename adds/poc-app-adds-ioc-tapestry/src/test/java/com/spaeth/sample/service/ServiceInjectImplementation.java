package com.spaeth.sample.service;

import javax.inject.Inject;

import com.spaeth.appbase.core.service.ChainBuilder;
import com.spaeth.sample.MyMarker;

public class ServiceInjectImplementation implements ServiceInjectInterface {

	@Inject
	@MyMarker
	private ServiceInterface service;
	@Inject
	private ChainBuilder chainBuilder;

	@Override
	public String t() {
		return this.service.method("teste" + String.valueOf(this.chainBuilder != null));
	}

}
