package com.spaeth.appbase.adds.xmlview.exception;

public class XmlViewElementNotInterpretable extends XmlViewException {

	private static final long serialVersionUID = 1L;

	public XmlViewElementNotInterpretable(String elementName, String expectedReturnType, String reason) {
		super(String.format("not able to interpret element '%s' to '%s' due to: %s", elementName, expectedReturnType,
				reason));
	}

}
