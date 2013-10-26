package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateVisualComponentProperties;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.VisualComponent;

public abstract class AbstractXmlVisualComponentTranslator<TranslatedObject extends VisualComponent> extends AbstractXmlTranslator<TranslatedObject> {

	AbstractXmlVisualComponentTranslator(FrontXmlTranslator frontTranslator) {
		super(frontTranslator);
	}

	@Override
	protected void configure(TranslatedObject result, Element source, XmlViewContext context) {
		super.configure(result, source, context);
		
		aggregateVisualComponentProperties(source, result);
	}

}
