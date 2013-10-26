package com.spaeth.appbase.service;

public interface I18NSupport {

	String getString(String name);

	String getMessage(String name, Object... parameters);

}
