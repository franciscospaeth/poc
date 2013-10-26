package com.spaeth.appbase.adds.security.service;

import com.spaeth.appbase.core.security.service.SecurityPermissionService;
import com.spaeth.appbase.model.StartupInfo;

public class ApplicationControllerPermissionServicePermissionServiceAdapter implements SecurityPermissionService {

	private ApplicationControllerPermissionService applicationControllerPermissionService;

	public ApplicationControllerPermissionServicePermissionServiceAdapter(
			ApplicationControllerPermissionService applicationControllerPermissionService) {
		super();
		this.applicationControllerPermissionService = applicationControllerPermissionService;
	}

	@Override
	public boolean hasPermission(String securityConstraintIdentifier) {
		// it is not expected that the permission check occurs without a context
		// for this case
		return false;
	}

	@Override
	public boolean hasPermission(String securityConstraintIdentifier, Object context) {
		if (ApplicationControllerPermissionService.ACTION_PERMISSION_NAME.equals(securityConstraintIdentifier)) {
			if (!(context instanceof StartupInfo)) {
				throw new IllegalArgumentException(
						"in order to check a processing permission, context provided should be of type "
								+ StartupInfo.class.getSimpleName());
			}
			return applicationControllerPermissionService.hasPermission((StartupInfo) context);
		}
		return false;
	}

}
