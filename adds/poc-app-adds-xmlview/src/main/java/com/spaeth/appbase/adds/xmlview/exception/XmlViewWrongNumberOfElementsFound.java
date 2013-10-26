package com.spaeth.appbase.adds.xmlview.exception;

public class XmlViewWrongNumberOfElementsFound extends XmlViewInitializationException {

	private static final long serialVersionUID = 1L;
	
	public XmlViewWrongNumberOfElementsFound(String elementName, int expected) {
		super(String.format("wrong number of elements of type %s found, %d was expected", elementName, expected));
	}

}
