package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.TEXT_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.TEXT_SIZE_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readEnumValue;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.SizeDefinition;
import com.spaeth.appbase.component.StaticLabel;

public class XmlStaticLabelTranslator extends AbstractXmlVisualComponentTranslator<StaticLabel> {

	public XmlStaticLabelTranslator(final FrontXmlTranslator frontXmlTranslator) {
		super(frontXmlTranslator);
	}

	@Override
	protected StaticLabel createResult(final Element source, final XmlViewContext context) {
		return new StaticLabel();
	}

	@Override
	protected void configure(final StaticLabel result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		String text = readAttribute(source, TEXT_ATTRIBUTE_NAME);

		// text is taken from text attribute, and when not present from content
		if (text != null) {
			result.setText(text);
		} else {
			result.setText(source.getTextContent());
		}

		result.setTextSizeDefinition(readEnumValue(source, TEXT_SIZE_ATTRIBUTE_NAME, SizeDefinition.class,
				SizeDefinition.NORMAL));
	}

}
