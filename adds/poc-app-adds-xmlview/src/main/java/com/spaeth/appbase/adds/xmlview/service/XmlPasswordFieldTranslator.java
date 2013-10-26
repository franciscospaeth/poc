package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateFieldComponentProperties;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.PasswordField;
import com.spaeth.appbase.service.I18NSupport;

public class XmlPasswordFieldTranslator extends AbstractXmlVisualComponentTranslator<PasswordField> {

	private final I18NSupport i18nSupport;

	public XmlPasswordFieldTranslator(final FrontXmlTranslator frontXmlTranslator, final I18NSupport i18nSupport) {
		super(frontXmlTranslator);
		this.i18nSupport = i18nSupport;
	}

	@Override
	protected PasswordField createResult(final Element source, final XmlViewContext context) {
		return new PasswordField();
	}

	@Override
	protected void configure(final PasswordField result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		// field component
		aggregateFieldComponentProperties(source, result, context, i18nSupport);
	}

}
