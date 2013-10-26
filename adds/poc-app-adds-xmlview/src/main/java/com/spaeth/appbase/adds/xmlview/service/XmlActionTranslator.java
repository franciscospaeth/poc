package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.ENABLED_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.NAME_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.TYPE_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readBooleanAttribute;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.adds.xmlview.model.JavaScriptAction;
import com.spaeth.appbase.model.Action;

public class XmlActionTranslator extends AbstractXmlTranslator<Action> {

	public XmlActionTranslator(final FrontXmlTranslator frontTranslator) {
		super(frontTranslator);
	}

	@Override
	protected Action createResult(final Element source, final XmlViewContext context) {
		String actionSource = readAttribute(source, TYPE_ATTRIBUTE_NAME);

		if (XmlViewTranslatorConstants.ACTION_PROVIDED_NAME.equals(actionSource)) {
			String actionName = readAttribute(source, NAME_ATTRIBUTE_NAME);
			if (actionName != null) {
				return context.getAction(actionName);
			}
		} else if (XmlViewTranslatorConstants.ACTION_JAVA_SCRIPT_NAME.equals(actionSource)) {
			String script = source.getTextContent();
			JavaScriptAction result = new JavaScriptAction(script, context);

			// default properties
			result.setEnabled(readBooleanAttribute(source, ENABLED_ATTRIBUTE_NAME, true));

			return result;
		}

		throw new IllegalArgumentException(String.format("unsupported action type '%s'", actionSource));
	}
}
