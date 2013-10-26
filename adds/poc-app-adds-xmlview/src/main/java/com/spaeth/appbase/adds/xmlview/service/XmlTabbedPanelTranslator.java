package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.*;

import java.util.List;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewComponentTypeNotExpectedException;
import com.spaeth.appbase.component.Component;
import com.spaeth.appbase.component.TabbedPanel;
import com.spaeth.appbase.component.api.IPanelTab;
import com.spaeth.appbase.component.api.ITabbedPanel;

public class XmlTabbedPanelTranslator extends AbstractXmlVisualComponentTranslator<TabbedPanel> {

	public XmlTabbedPanelTranslator(FrontXmlTranslator frontTranslator) {
		super(frontTranslator);
	}

	@Override
	protected TabbedPanel createResult(Element source, XmlViewContext context) {
		return new TabbedPanel();
	}

	@Override
	protected void configure(TabbedPanel result, Element source, XmlViewContext context) {
		super.configure(result, source, context);

		// interpret tabs inside tabbed panel
		List<Component> components = extractComponentsFromElement(source, context);
		for (Component c : components) {
			if (c instanceof IPanelTab) {
				result.addTab((IPanelTab) c);
			} else {
				throw new XmlViewComponentTypeNotExpectedException("child of " + ITabbedPanel.class.getName(),
						IPanelTab.class, c.getClass());
			}
		}
	}

}
