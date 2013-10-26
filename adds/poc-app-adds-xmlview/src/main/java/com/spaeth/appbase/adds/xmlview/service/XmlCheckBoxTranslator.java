package com.spaeth.appbase.adds.xmlview.service;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.CheckBox;
import com.spaeth.appbase.service.I18NSupport;

public class XmlCheckBoxTranslator extends AbstractXmlVisualComponentTranslator<CheckBox> {

	private final I18NSupport i18nSupport;

	public XmlCheckBoxTranslator(final FrontXmlTranslator frontTranslator, final I18NSupport i18nSupport) {
		super(frontTranslator);
		this.i18nSupport = i18nSupport;
	}

	@Override
	protected CheckBox createResult(final Element source, final XmlViewContext context) {
		return new CheckBox();
	}

	@Override
	protected void configure(final CheckBox result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		XmlViewTranslatorHelper.aggregateFieldComponentProperties(source, result, context, i18nSupport);
	}

}
