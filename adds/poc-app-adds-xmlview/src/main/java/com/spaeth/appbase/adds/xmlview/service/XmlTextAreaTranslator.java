package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateFieldComponentProperties;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.TextArea;
import com.spaeth.appbase.service.I18NSupport;

public class XmlTextAreaTranslator extends AbstractXmlVisualComponentTranslator<TextArea> {

	private final I18NSupport i18nSupport;

	public XmlTextAreaTranslator(final FrontXmlTranslator frontXmlTranslator, final I18NSupport i18nSupport) {
		super(frontXmlTranslator);
		this.i18nSupport = i18nSupport;
	}

	@Override
	protected TextArea createResult(final Element source, final XmlViewContext context) {
		return new TextArea();
	}

	@Override
	protected void configure(final TextArea result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		// field component
		aggregateFieldComponentProperties(source, result, context, i18nSupport);

	}

}
