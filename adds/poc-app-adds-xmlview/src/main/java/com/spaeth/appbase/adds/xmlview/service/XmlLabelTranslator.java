package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.TEXT_SIZE_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateFieldComponentProperties;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readEnumValue;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.Label;
import com.spaeth.appbase.component.SizeDefinition;
import com.spaeth.appbase.service.I18NSupport;

public class XmlLabelTranslator extends AbstractXmlVisualComponentTranslator<Label> {

	private final I18NSupport i18nSupport;

	public XmlLabelTranslator(final FrontXmlTranslator frontXmlTranslator, final I18NSupport i18nSupport) {
		super(frontXmlTranslator);
		this.i18nSupport = i18nSupport;
	}

	@Override
	protected Label createResult(final Element source, final XmlViewContext context) {
		return new Label();
	}

	@Override
	protected void configure(final Label result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		aggregateFieldComponentProperties(source, result, context, i18nSupport);
		
		result.setTextSizeDefinition(readEnumValue(source, TEXT_SIZE_ATTRIBUTE_NAME, SizeDefinition.class,
				SizeDefinition.NORMAL));
	}

}
