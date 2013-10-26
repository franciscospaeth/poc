package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readNameAttribute;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;

public class DefaultFrontXmlTranslator implements FrontXmlTranslator {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Map<String, XmlTranslator> translators;

	public DefaultFrontXmlTranslator(Map<String, XmlTranslator> translators) {
		super();
		this.translators = translators;
	}

	@Override
	public Object translate(Element source, XmlViewContext context) {
		String translatorName = source.getNodeName();

		logger.debug(String.format("requesting translation of a %s", translatorName));

		if (!translators.containsKey(translatorName)) {
			throw new IllegalArgumentException(String.format("translator not found for name '%s'", translatorName));
		}

		Object result = translators.get(translatorName).translate(source, context);

		if (result == null) {
			return null;
		}

		context.register(readNameAttribute(source, context), result);

		return result;
	}

}
