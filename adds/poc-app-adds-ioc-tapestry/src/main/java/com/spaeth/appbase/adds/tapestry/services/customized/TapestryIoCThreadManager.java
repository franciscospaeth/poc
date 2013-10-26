package com.spaeth.appbase.adds.tapestry.services.customized;

import javax.inject.Inject;

import org.apache.tapestry5.ioc.services.PerthreadManager;

import com.spaeth.appbase.core.service.IoCThreadManager;

public class TapestryIoCThreadManager implements IoCThreadManager {

	@Inject
	private PerthreadManager perthreadManager;

	@Override
	public void cleanup() {
		perthreadManager.cleanup();
	}

}
