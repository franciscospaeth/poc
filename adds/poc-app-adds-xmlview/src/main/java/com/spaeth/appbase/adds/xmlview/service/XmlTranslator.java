package com.spaeth.appbase.adds.xmlview.service;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;

public interface XmlTranslator {

	Object translate(Element source, XmlViewContext context);
	
}
