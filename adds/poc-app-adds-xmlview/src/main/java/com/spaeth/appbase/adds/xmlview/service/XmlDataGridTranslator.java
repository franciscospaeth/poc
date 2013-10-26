package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.CAPTION_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.COLUMNS_ELEMENT_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.COLUMN_ELEMENT_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.NAME_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateCollectionViewerComponentProperties;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.aggregateFieldComponentProperties;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readI18NAttribute;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.AutoColumnModel;
import com.spaeth.appbase.component.DataGrid;
import com.spaeth.appbase.component.StaticColumnModel;
import com.spaeth.appbase.service.I18NSupport;

public class XmlDataGridTranslator extends AbstractXmlVisualComponentTranslator<DataGrid> {

	private final I18NSupport i18nSupport;

	public XmlDataGridTranslator(final FrontXmlTranslator frontXmlTranslator, final I18NSupport i18nSupport) {
		super(frontXmlTranslator);
		this.i18nSupport = i18nSupport;
	}

	@Override
	protected DataGrid createResult(final Element source, final XmlViewContext context) {
		return new DataGrid();
	}

	@Override
	protected void configure(final DataGrid result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		NodeList nodes = source.getElementsByTagName(COLUMNS_ELEMENT_NAME);
		if (nodes.getLength() == 0) {
			result.setColumnModel(new AutoColumnModel());
		} else {
			nodes = ((Element) (nodes.item(0))).getElementsByTagName(COLUMN_ELEMENT_NAME);
			StaticColumnModel columnModel = new StaticColumnModel();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node item = nodes.item(i);
				String caption = readI18NAttribute((Element) item, CAPTION_ATTRIBUTE_NAME, i18nSupport);
				String propertyName = readAttribute((Element) item, NAME_ATTRIBUTE_NAME);
				columnModel.addColumn(propertyName, caption);
			}
			result.setColumnModel(columnModel);
		}

		// collection viewer component
		aggregateCollectionViewerComponentProperties(source, result, context);

		// field component
		aggregateFieldComponentProperties(source, result, context, i18nSupport);

	}

}
