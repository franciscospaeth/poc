package com.spaeth.appbase.core.security.service;

public class UserNamePasswordAuthenticationParameter implements AuthenticationParameter {

	private final String userName;
	private final String password;

	public UserNamePasswordAuthenticationParameter(final String userName, final String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

}