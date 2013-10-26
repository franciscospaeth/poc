package com.spaeth.appbase.core.security.model;

public enum AccessPolicy {

	NONE(0), READ(1), WRITE(2);

	private int value;

	private AccessPolicy(final int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

}
