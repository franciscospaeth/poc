package com.spaeth.appbase.core.security.service;

public interface SecurityPermissionService {

	public static final SecurityPermissionService DUMMY = new SecurityPermissionService() {

		@Override
		public boolean hasPermission(final String securityConstraintIdentifier, final Object context) {
			return true;
		}

		@Override
		public boolean hasPermission(final String securityConstraintIdentifier) {
			return true;
		}
	};

	boolean hasPermission(String securityConstraintIdentifier);

	boolean hasPermission(String securityConstraintIdentifier, Object context);

}
