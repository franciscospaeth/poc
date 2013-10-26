package com.spaeth.appbase.adds.xmlview.exception;

public class XmlViewComponentNotFoundException extends XmlViewException {

	private static final long serialVersionUID = 1L;

	public XmlViewComponentNotFoundException(String name) {
		super(String.format("%s component was not found", name));
	}
}
