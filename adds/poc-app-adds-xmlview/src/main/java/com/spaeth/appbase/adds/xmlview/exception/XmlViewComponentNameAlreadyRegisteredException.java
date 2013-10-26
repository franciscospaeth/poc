package com.spaeth.appbase.adds.xmlview.exception;

public class XmlViewComponentNameAlreadyRegisteredException extends XmlViewException {

	private static final long serialVersionUID = 1L;

	public XmlViewComponentNameAlreadyRegisteredException(String name) {
		super(String.format("%s component was already found in ", name));
	}

}
