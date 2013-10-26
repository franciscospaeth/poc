package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.ON_ACTION_PERFORMED_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateFieldComponentProperties;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.TextField;
import com.spaeth.appbase.service.I18NSupport;

public class XmlTextFieldTranslator extends AbstractXmlVisualComponentTranslator<TextField> {

	private final I18NSupport i18nSupport;

	public XmlTextFieldTranslator(final FrontXmlTranslator frontXmlTranslator, final I18NSupport i18nSupport) {
		super(frontXmlTranslator);
		this.i18nSupport = i18nSupport;
	}

	@Override
	protected TextField createResult(final Element source, final XmlViewContext context) {
		return new TextField();
	}

	@Override
	protected void configure(final TextField result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		// field component
		aggregateFieldComponentProperties(source, result, context, i18nSupport);

		String onActionPerformed = readAttribute(source, ON_ACTION_PERFORMED_ATTRIBUTE_NAME);
		if (StringUtils.isNotEmpty(onActionPerformed)) {
			result.setAction(context.getAction(onActionPerformed));
		}

	}

}
