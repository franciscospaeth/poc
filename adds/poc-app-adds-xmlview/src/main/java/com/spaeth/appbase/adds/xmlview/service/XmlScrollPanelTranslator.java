package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.*;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.ScrollPanel;

public class XmlScrollPanelTranslator extends AbstractXmlVisualComponentTranslator<ScrollPanel> {

	public XmlScrollPanelTranslator(FrontXmlTranslator frontTranslator) {
		super(frontTranslator);
	}

	@Override
	protected ScrollPanel createResult(Element source, XmlViewContext context) {
		return new ScrollPanel();
	}

	@Override
	protected void configure(ScrollPanel result, Element source, XmlViewContext context) {
		super.configure(result, source, context);
	
		// component container properties
		aggregateComponentContainerContent(source, result, context);
	}

}
