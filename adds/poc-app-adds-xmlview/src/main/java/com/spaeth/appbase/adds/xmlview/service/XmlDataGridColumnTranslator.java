package com.spaeth.appbase.adds.xmlview.service;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.DataGridColumn;

public class XmlDataGridColumnTranslator extends AbstractXmlTranslator<DataGridColumn> {

	public XmlDataGridColumnTranslator(FrontXmlTranslator frontXmlTranslator) {
		super(frontXmlTranslator);
	}

	@Override
	protected DataGridColumn createResult(Element source, XmlViewContext context) {
		throw new RuntimeException("not yet implemented");
	}

}
