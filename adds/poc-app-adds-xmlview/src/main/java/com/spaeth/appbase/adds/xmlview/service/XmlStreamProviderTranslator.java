package com.spaeth.appbase.adds.xmlview.service;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewException;
import com.spaeth.appbase.service.ResourceLoader;

public class XmlStreamProviderTranslator implements XmlTranslator {

	private final Map<String, XmlTranslator> translators = new HashMap<String, XmlTranslator>();

	public XmlStreamProviderTranslator(final FrontXmlTranslator frontTranslator, final ResourceLoader resourceLoader) {
		translators.put("RESOURCE-LOADER", new ResourceLoaderStreamProviderTranslator(frontTranslator, resourceLoader));
		translators.put("DATA-SOURCE", new DataSourceStreamProviderTranslator(frontTranslator));
	}

	@Override
	public Object translate(final Element source, final XmlViewContext context) {
		String providerType = XmlViewTranslatorHelper.readAttribute(source,
				XmlViewTranslatorConstants.TYPE_ATTRIBUTE_NAME);

		XmlTranslator streamProviderTranslator = translators.get(providerType);

		if (streamProviderTranslator == null) {
			throw new XmlViewException(String.format("stream provider '%s' is not recognized, supported types are: %s",
					providerType, translators.keySet()));
		}

		return streamProviderTranslator.translate(source, context);
	}

}
