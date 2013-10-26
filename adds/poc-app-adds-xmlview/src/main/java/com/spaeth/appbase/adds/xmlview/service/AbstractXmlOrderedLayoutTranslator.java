package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.SHOW_MARGIN_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.SPACED_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateComponentContainerContent;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateLayout;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateVisualComponentProperties;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readBooleanAttribute;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.api.IOrderedLayout;

public abstract class AbstractXmlOrderedLayoutTranslator<TranslatedObject extends IOrderedLayout> extends AbstractXmlTranslator<TranslatedObject> {

	public AbstractXmlOrderedLayoutTranslator(final FrontXmlTranslator frontTranslator) {
		super(frontTranslator);
	}

	@Override
	protected void configure(final TranslatedObject result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		result.setSpaced(readBooleanAttribute(source, SPACED_ATTRIBUTE_NAME, false));
		result.setMarginVisible(readBooleanAttribute(source, SHOW_MARGIN_ATTRIBUTE_NAME, false));

		// visual component properties
		aggregateVisualComponentProperties(source, result);

		// component container properties
		aggregateComponentContainerContent(source, result, context);

		// configure expand ratio
		aggregateLayout(source, result);
	}

}