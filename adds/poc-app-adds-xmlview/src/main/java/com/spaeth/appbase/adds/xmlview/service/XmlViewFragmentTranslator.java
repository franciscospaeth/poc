package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.RESULT_ELEMENT_NAME;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewException;
import com.spaeth.appbase.component.api.IOrderedLayout;

public class XmlViewFragmentTranslator extends XmlViewTranslator {

	public XmlViewFragmentTranslator(FrontXmlTranslator frontXmlTranslator) {
		super(frontXmlTranslator);
	}

	@Override
	protected IOrderedLayout createResult(Element source, XmlViewContext context) {
		// interpret definition
		translateDefinitionNodes(source, context);

		// component container properties
		NodeList resultElements = source.getElementsByTagName(RESULT_ELEMENT_NAME);
		if (resultElements.getLength() != 1) {
			throw new XmlViewException(String.format(
					"a view fragment should have one and just one result element but %d were found",
					resultElements.getLength()));
		}

		NodeList children = resultElements.item(0).getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			Node item = children.item(i);
			if (!(item instanceof Element)) {
				continue;
			}
			return (IOrderedLayout)frontTranslator.translate((Element) item, context);
		}
		
		throw new IllegalStateException("not able to define component to be returned by fragment");
	}
	
}
