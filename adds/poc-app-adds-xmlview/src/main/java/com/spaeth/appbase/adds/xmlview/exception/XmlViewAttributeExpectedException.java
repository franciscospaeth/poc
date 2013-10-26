package com.spaeth.appbase.adds.xmlview.exception;

public class XmlViewAttributeExpectedException extends XmlViewException {

	private static final long serialVersionUID = 1L;

	public XmlViewAttributeExpectedException(Class<?> resultClass, String attributeName) {
		super(String.format("in order to instantiate a '%s', the attribute '%s' is mandatory", resultClass, attributeName));
	}
	
	public XmlViewAttributeExpectedException(String virtualName, String attributeName) {
		super(String.format("in order to instantiate a '%s', the attribute '%s' is mandatory", virtualName, attributeName));
	}
	
}
