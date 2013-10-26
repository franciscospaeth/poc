package com.spaeth.appbase.adds.security.service;

import com.spaeth.appbase.core.security.service.SecurityPermissionService;
import com.spaeth.appbase.model.StartupInfo;
import com.spaeth.appbase.service.ApplicationController;

public class SecuredApplicationControllerDecorator implements ApplicationController {

	private SecurityPermissionService permissionService;
	private ApplicationController decorated;
	
	public SecuredApplicationControllerDecorator(ApplicationController decorated, SecurityPermissionService permissionService) {
		this.permissionService = permissionService;
		this.decorated = decorated;
	}
	
	@Override
	public void process(StartupInfo startupInfo) {
		if (!permissionService.hasPermission(ApplicationControllerPermissionService.ACTION_PERMISSION_NAME, startupInfo)) {
			throw new IllegalArgumentException(String.format("permission not granted to process '%s'", startupInfo));
		}
		decorated.process(startupInfo);
	}

}
