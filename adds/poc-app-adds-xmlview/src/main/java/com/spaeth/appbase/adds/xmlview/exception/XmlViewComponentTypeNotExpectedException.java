package com.spaeth.appbase.adds.xmlview.exception;

public class XmlViewComponentTypeNotExpectedException extends XmlViewException {

	private static final long serialVersionUID = 1L;

	public XmlViewComponentTypeNotExpectedException(String name, Class<?> expected, Class<?> found) {
		super(String.format("%s component was expected to be an implementation of %s, but %s was returned", name,
				expected.getClass().getName(), found.getClass().getName()));
	}

}
