package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.STREAM_PROVIDER_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewAttributeExpectedException;
import com.spaeth.appbase.component.Image;
import com.spaeth.appbase.component.StreamProvider;

public class XmlImageTranslator extends AbstractXmlVisualComponentTranslator<Image> {

	public XmlImageTranslator(FrontXmlTranslator frontTranslator) {
		super(frontTranslator);
	}

	@Override
	protected Image createResult(Element source, XmlViewContext context) {
		return new Image();
	}
	
	@Override
	protected void configure(Image result, Element source, XmlViewContext context) {
		super.configure(result, source, context);

		String streamProviderName = readAttribute(source, STREAM_PROVIDER_ATTRIBUTE_NAME);
		
		if (StringUtils.isEmpty(streamProviderName)) {
			throw new XmlViewAttributeExpectedException(Image.class, STREAM_PROVIDER_ATTRIBUTE_NAME);
		}
		
		StreamProvider streamProvider = context.get(streamProviderName, StreamProvider.class);
		result.setStreamProvider(streamProvider);
	}

}
