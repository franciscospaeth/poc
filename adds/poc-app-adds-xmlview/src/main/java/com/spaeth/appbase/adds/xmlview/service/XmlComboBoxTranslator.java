package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.CAPTION_PROPERTY_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateCollectionViewerComponentProperties;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateFieldComponentProperties;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.ComboBox;
import com.spaeth.appbase.service.I18NSupport;

public class XmlComboBoxTranslator extends AbstractXmlVisualComponentTranslator<ComboBox> {

	private final I18NSupport i18nSupport;

	public XmlComboBoxTranslator(final FrontXmlTranslator frontXmlTranslator, final I18NSupport i18nSupport) {
		super(frontXmlTranslator);
		this.i18nSupport = i18nSupport;
	}

	@Override
	protected ComboBox createResult(final Element source, final XmlViewContext context) {
		return new ComboBox();
	}

	@Override
	protected void configure(final ComboBox result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		// collection viewer component
		aggregateCollectionViewerComponentProperties(source, result, context);

		// configure caption property
		result.setCaptionProperty(readAttribute(source, CAPTION_PROPERTY_ATTRIBUTE_NAME));

		// field component
		aggregateFieldComponentProperties(source, result, context, i18nSupport);

	}

}
