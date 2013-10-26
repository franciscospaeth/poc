package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.FLOW_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readEnumValue;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.VerticalLayout;
import com.spaeth.appbase.component.api.IOrderedLayout.VerticalLayoutFlow;

public class XmlVerticalLayoutTranslator extends AbstractXmlOrderedLayoutTranslator<VerticalLayout> {

	public XmlVerticalLayoutTranslator(FrontXmlTranslator frontTranslator) {
		super(frontTranslator);
	}

	@Override
	protected VerticalLayout createResult(Element source, XmlViewContext context) {
		return new VerticalLayout(readEnumValue(source, FLOW_ATTRIBUTE_NAME, VerticalLayoutFlow.class,
				VerticalLayoutFlow.TOP_DOWN));
	}

}
