package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.CAPTION_PROPERTY_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.MULTI_SELECT_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateCollectionViewerComponentProperties;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateFieldComponentProperties;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readBooleanAttribute;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.List;
import com.spaeth.appbase.service.I18NSupport;

public class XmlListTranslator extends AbstractXmlVisualComponentTranslator<List> {

	private final I18NSupport i18nSupport;

	public XmlListTranslator(final FrontXmlTranslator frontXmlTranslator, final I18NSupport i18nSupport) {
		super(frontXmlTranslator);
		this.i18nSupport = i18nSupport;
	}

	@Override
	protected List createResult(final Element source, final XmlViewContext context) {
		return new List();
	}

	@Override
	protected void configure(final List result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		// field component
		aggregateFieldComponentProperties(source, result, context, i18nSupport);

		// collection viewer component
		aggregateCollectionViewerComponentProperties(source, result, context);

		// configure caption property
		result.setCaptionProperty(readAttribute(source, CAPTION_PROPERTY_ATTRIBUTE_NAME));

		// special properties
		result.setMultiSelect(readBooleanAttribute(source, MULTI_SELECT_ATTRIBUTE_NAME, false));
	}

}
