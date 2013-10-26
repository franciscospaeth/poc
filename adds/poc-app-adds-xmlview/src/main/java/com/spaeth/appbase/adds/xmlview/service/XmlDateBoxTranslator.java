package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.RESOLUTION_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateFieldComponentProperties;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readEnumValue;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.DateBox;
import com.spaeth.appbase.component.api.IDateBox.DateResolution;
import com.spaeth.appbase.service.I18NSupport;

public class XmlDateBoxTranslator extends AbstractXmlVisualComponentTranslator<DateBox> {

	private final I18NSupport i18nSupport;

	public XmlDateBoxTranslator(final FrontXmlTranslator frontTranslator, final I18NSupport i18nSupport) {
		super(frontTranslator);
		this.i18nSupport = i18nSupport;
	}

	@Override
	protected DateBox createResult(final Element source, final XmlViewContext context) {
		return new DateBox();
	}

	@Override
	protected void configure(final DateBox result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		// field component
		aggregateFieldComponentProperties(source, result, context, i18nSupport);

		DateResolution resolution = readEnumValue(source, RESOLUTION_ATTRIBUTE_NAME, DateResolution.class);
		if (resolution != null) {
			result.setDateResolution(resolution);
		}
	}
}
