package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.ICON_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.TEXT_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.extractMenuItemsFromElement;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readI18NAttribute;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.Menu;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.service.I18NSupport;

public class XmlMenuTranslator extends AbstractXmlTranslator<Menu> {

	private final I18NSupport i18nSupport;

	public XmlMenuTranslator(final FrontXmlTranslator frontTranslator, final I18NSupport i18nSupport) {
		super(frontTranslator);
		this.i18nSupport = i18nSupport;
	}

	@Override
	protected Menu createResult(final Element source, final XmlViewContext context) {
		return new Menu();
	}

	@Override
	protected void configure(final Menu result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		result.setOptions(extractMenuItemsFromElement(source, context));
		result.setText(readI18NAttribute(source, TEXT_ATTRIBUTE_NAME, i18nSupport));

		String streamProviderName = readAttribute(source, ICON_ATTRIBUTE_NAME);

		if (streamProviderName != null) {
			StreamProvider streamProvider = context.get(streamProviderName, StreamProvider.class);
			result.setIcon(streamProvider);
		}
	}

}
