package com.spaeth.appbase.adds.xmlview.service;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.MenuBar;

public class XmlMenuBarTranslator extends AbstractXmlVisualComponentTranslator<MenuBar>{

	public XmlMenuBarTranslator(FrontXmlTranslator frontTranslator) {
		super(frontTranslator);
	}

	@Override
	protected MenuBar createResult(Element source, XmlViewContext context) {
		return new MenuBar();
	}
	
	@Override
	protected void configure(MenuBar result, Element source, XmlViewContext context) {
		super.configure(result, source, context);

		result.setOptions(XmlViewTranslatorHelper.extractMenuItemsFromElement(source, context));
	}
	
}