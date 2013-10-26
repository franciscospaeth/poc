package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.ICON_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.ON_ACTION_PERFORMED_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.TEXT_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readI18NAttribute;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.MenuOption;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.service.I18NSupport;

public class XmlMenuOptionTranslator extends AbstractXmlTranslator<MenuOption> {

	private I18NSupport i18nSupport;

	public XmlMenuOptionTranslator(final FrontXmlTranslator frontXmlTranslator, final I18NSupport i18nSupport) {
		super(frontXmlTranslator);
	}

	@Override
	protected MenuOption createResult(final Element source, final XmlViewContext context) {
		return new MenuOption();
	}

	@Override
	protected void configure(final MenuOption result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		String onSelectionPerformed = readAttribute(source, ON_ACTION_PERFORMED_ATTRIBUTE_NAME);
		if (StringUtils.isNotEmpty(onSelectionPerformed)) {
			result.setAction(context.getAction(onSelectionPerformed));
		}

		result.setText(readI18NAttribute(source, TEXT_ATTRIBUTE_NAME, i18nSupport));

		String streamProviderName = readAttribute(source, ICON_ATTRIBUTE_NAME);

		if (streamProviderName != null) {
			StreamProvider streamProvider = context.get(streamProviderName, StreamProvider.class);
			result.setIcon(streamProvider);
		}
	}

}
