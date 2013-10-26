package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.RESOURCE_PATH_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readNameAttribute;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.ScopedViewXmlContext;
import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewAttributeExpectedException;
import com.spaeth.appbase.component.Component;

public class XmlFragmentTranslator extends AbstractXmlTranslator<Component> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public XmlFragmentTranslator(FrontXmlTranslator frontTranslator) {
		super(frontTranslator);
	}
	
	@Override
	protected Component createResult(Element source, XmlViewContext context) {
		String resourceName = readAttribute(source, RESOURCE_PATH_ATTRIBUTE_NAME);
		
		if (StringUtils.isEmpty(resourceName)) {
			throw new XmlViewAttributeExpectedException("fragment", RESOURCE_PATH_ATTRIBUTE_NAME);
		}
		
		String componentName = readNameAttribute(source, context);
		
		logger.debug("initialized scoped view for {}", componentName);
		
		Component result = new ScopedViewXmlContext(componentName, context).translate(resourceName, Component.class);

		logger.debug("finalized scoped view for {}", componentName);

		return result;
	}

}
