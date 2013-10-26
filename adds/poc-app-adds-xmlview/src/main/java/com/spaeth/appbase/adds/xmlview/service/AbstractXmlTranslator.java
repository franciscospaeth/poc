package com.spaeth.appbase.adds.xmlview.service;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;


public abstract class AbstractXmlTranslator<TranslatedObject> implements XmlTranslator {

	protected final FrontXmlTranslator frontTranslator;
	
	AbstractXmlTranslator(FrontXmlTranslator frontTranslator) {
		super();
		this.frontTranslator = frontTranslator;
	}
	
	@Override
	public final TranslatedObject translate(Element source, XmlViewContext context) {
		TranslatedObject result = createResult(source, context);
		
		configure(result, source, context);
		
		return result;
	}

	protected abstract TranslatedObject createResult(Element source, XmlViewContext context);
	
	protected void configure(TranslatedObject result, Element source, XmlViewContext context){
	}

}
