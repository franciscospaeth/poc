package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.CLOSABLE_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.ICON_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.SHOW_MARGIN_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.SPACED_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.TITLE_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateComponentContainerContent;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateLayout;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readBooleanAttribute;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spaeth.appbase.adds.xmlview.ViewLayout;
import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewException;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.api.IOrderedLayout;

public class XmlViewTranslator extends AbstractXmlVisualComponentTranslator<IOrderedLayout> {

	public XmlViewTranslator(final FrontXmlTranslator frontXmlTranslator) {
		super(frontXmlTranslator);
	}

	@Override
	protected IOrderedLayout createResult(final Element source, final XmlViewContext context) {
		// interpret definition
		translateDefinitionNodes(source, context);

		boolean closable = readBooleanAttribute(source, CLOSABLE_ATTRIBUTE_NAME, true);
		String title = readAttribute(source, TITLE_ATTRIBUTE_NAME);

		String streamProviderName = readAttribute(source, ICON_ATTRIBUTE_NAME);
		StreamProvider streamProvider = null;
		if (streamProviderName != null) {
			streamProvider = context.get(streamProviderName, StreamProvider.class);
		}

		return new ViewLayout(streamProvider, title, closable);
	}

	@Override
	protected void configure(final IOrderedLayout result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		// component container properties
		NodeList contentElements = source.getElementsByTagName(XmlViewTranslatorConstants.CONTENT_ELEMENT_NAME);
		if (contentElements.getLength() == 1) {
			aggregateComponentContainerContent((Element) (contentElements.item(0)), result, context);
		} else {
			throw new XmlViewException(String.format(
					"a view should have one and just one content element but %d were found",
					contentElements.getLength()));
		}

		result.setSpaced(readBooleanAttribute(source, SPACED_ATTRIBUTE_NAME, false));
		result.setMarginVisible(readBooleanAttribute(source, SHOW_MARGIN_ATTRIBUTE_NAME, false));

		// aggregate layout properties
		aggregateLayout(source, result);

	}

	protected void translateDefinitionNodes(final Element source, final XmlViewContext context) {
		NodeList definition = source.getElementsByTagName(XmlViewTranslatorConstants.DEFINITION_ELEMENT_NAME);
		if (definition.getLength() == 1) {
			Element definitionElement = (Element) definition.item(0);
			NodeList childNodes = definitionElement.getChildNodes();

			for (int i = 0; i < childNodes.getLength(); i++) {
				Node item = childNodes.item(i);
				if (!(item instanceof Element)) {
					continue;
				}
				frontTranslator.translate((Element) item, context);
			}
		} else {
			throw new XmlViewException(String.format(
					"a view should have one and just one definition element but %d were found", definition.getLength()));
		}
	}

}
