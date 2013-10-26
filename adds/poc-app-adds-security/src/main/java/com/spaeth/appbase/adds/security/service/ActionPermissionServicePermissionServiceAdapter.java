package com.spaeth.appbase.adds.security.service;

import com.spaeth.appbase.adds.security.model.ActionSecurityContext;
import com.spaeth.appbase.core.security.service.SecurityPermissionService;

public class ActionPermissionServicePermissionServiceAdapter implements SecurityPermissionService {

	private ActionPermissionService actionPermissionService;

	@Override
	public boolean hasPermission(String securityConstraintIdentifier) {
		// it is not expected that the permission check occurs without a context for this case
		return false;
	}

	@Override
	public boolean hasPermission(String securityConstraintIdentifier, Object context) {
		if (ActionPermissionService.ACTION_PERMISSION_NAME.equals(securityConstraintIdentifier)) {
			if (!(context instanceof ActionSecurityContext)) {
				throw new IllegalArgumentException(
						"in order to check an action access permission, context provided should be of type "
								+ ActionSecurityContext.class.getSimpleName());
			}
			return actionPermissionService.hasPermission((ActionSecurityContext) context);
		}
		return false;
	}

}
