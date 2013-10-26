package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.CAPTION_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.CLOSABLE_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.ICON_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateComponentContainerContent;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readBooleanAttribute;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.PanelTab;
import com.spaeth.appbase.component.StreamProvider;

public class XmlPanelTabTranslator extends AbstractXmlTranslator<PanelTab> {

	public XmlPanelTabTranslator(final FrontXmlTranslator frontTranslator) {
		super(frontTranslator);
	}

	@Override
	protected PanelTab createResult(final Element source, final XmlViewContext context) {
		return new PanelTab();
	}

	@Override
	protected void configure(final PanelTab result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		result.setVisible(readBooleanAttribute(source, XmlViewTranslatorConstants.VISIBLE_ATTRIBUTE_NAME, true));

		result.setClosable(readBooleanAttribute(source, CLOSABLE_ATTRIBUTE_NAME, true));
		result.setCaption(readAttribute(source, CAPTION_ATTRIBUTE_NAME));

		// component container
		aggregateComponentContainerContent(source, result, context);

		String streamProviderName = readAttribute(source, ICON_ATTRIBUTE_NAME);

		if (streamProviderName != null) {
			StreamProvider streamProvider = context.get(streamProviderName, StreamProvider.class);
			result.setIcon(streamProvider);
		}
	}
}
