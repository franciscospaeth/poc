package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.*;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewAttributeExpectedException;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.stream.ClassPathStreamProvider;
import com.spaeth.appbase.service.ResourceLoader;

public class ResourceLoaderStreamProviderTranslator extends AbstractXmlTranslator<StreamProvider> {

	private ResourceLoader resourceLoader;
	
	public ResourceLoaderStreamProviderTranslator(FrontXmlTranslator frontTranslator, ResourceLoader resourceLoader) {
		super(frontTranslator);
		this.resourceLoader = resourceLoader;
	}

	@Override
	protected StreamProvider createResult(Element source, XmlViewContext context) {
		String resourcePath = XmlViewTranslatorHelper.readAttribute(source, RESOURCE_PATH_ATTRIBUTE_NAME);
		
		if (StringUtils.isEmpty(resourcePath)) {
			throw new XmlViewAttributeExpectedException(ClassPathStreamProvider.class, RESOURCE_PATH_ATTRIBUTE_NAME);
		}
		
		return new ClassPathStreamProvider(resourceLoader, resourcePath);
	}
	
}
