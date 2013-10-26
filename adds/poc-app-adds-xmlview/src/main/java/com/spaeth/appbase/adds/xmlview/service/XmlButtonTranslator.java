package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.ICON_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.ON_ACTION_PERFORMED_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.SIZE_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.TEXT_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readEnumValue;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readI18NAttribute;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.Button;
import com.spaeth.appbase.component.SizeDefinition;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.service.I18NSupport;

public class XmlButtonTranslator extends AbstractXmlVisualComponentTranslator<Button> {

	private final I18NSupport i18nSupport;

	public XmlButtonTranslator(final FrontXmlTranslator frontXmlTranslator, final I18NSupport i18nSupport) {
		super(frontXmlTranslator);
		this.i18nSupport = i18nSupport;
	}

	@Override
	protected Button createResult(final Element source, final XmlViewContext context) {
		return new Button();
	}

	@Override
	protected void configure(final Button result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		String onActionPerformed = readAttribute(source, ON_ACTION_PERFORMED_ATTRIBUTE_NAME);
		if (StringUtils.isNotEmpty(onActionPerformed)) {
			result.setAction(context.getAction(onActionPerformed));
		}

		result.setText(readI18NAttribute(source, TEXT_ATTRIBUTE_NAME, i18nSupport));

		String streamProviderName = readAttribute(source, ICON_ATTRIBUTE_NAME);

		if (streamProviderName != null) {
			StreamProvider streamProvider = context.get(streamProviderName, StreamProvider.class);
			result.setIcon(streamProvider);
		}

		result.setSize(readEnumValue(source, SIZE_ATTRIBUTE_NAME, SizeDefinition.class, SizeDefinition.NORMAL));

	}

}
