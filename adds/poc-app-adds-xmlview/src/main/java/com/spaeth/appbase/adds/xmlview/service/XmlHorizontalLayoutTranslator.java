package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.FLOW_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readEnumValue;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.HorizontalLayout;
import com.spaeth.appbase.component.api.IOrderedLayout.HorizontalLayoutFlow;

public class XmlHorizontalLayoutTranslator extends AbstractXmlOrderedLayoutTranslator<HorizontalLayout> {

	public XmlHorizontalLayoutTranslator(FrontXmlTranslator frontTranslator) {
		super(frontTranslator);
	}

	@Override
	protected HorizontalLayout createResult(Element source, XmlViewContext context) {
		HorizontalLayoutFlow flow = readEnumValue(source, FLOW_ATTRIBUTE_NAME, HorizontalLayoutFlow.class,
				HorizontalLayoutFlow.LEFT_TO_RIGHT);
		return new HorizontalLayout(flow);
	}

}
