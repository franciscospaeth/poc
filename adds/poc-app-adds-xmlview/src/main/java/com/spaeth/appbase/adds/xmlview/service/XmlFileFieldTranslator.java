package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.MESSAGE_ICON_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.NAME_DATASOURCE_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.FileField;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.service.I18NSupport;

public class XmlFileFieldTranslator extends AbstractXmlVisualComponentTranslator<FileField> {

	private final I18NSupport i18nSupport;

	public XmlFileFieldTranslator(final FrontXmlTranslator frontTranslator, final I18NSupport i18nSupport) {
		super(frontTranslator);
		this.i18nSupport = i18nSupport;
	}

	@Override
	protected FileField createResult(final Element source, final XmlViewContext context) {
		return new FileField();
	}

	@Override
	protected void configure(final FileField result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		XmlViewTranslatorHelper.aggregateFieldComponentProperties(source, result, context, i18nSupport);

		String dataSourcePath = readAttribute(source, NAME_DATASOURCE_ATTRIBUTE_NAME);
		if (dataSourcePath != null) {
			DataSource dataSource = context.getDataSource(dataSourcePath);

			if (dataSource == null) {
				throw new IllegalStateException(
						"no datasource was returned from context when requested datasource for " + dataSourcePath);
			}

			result.setNameDataSource(dataSource);
		}

		String streamProviderName = readAttribute(source, MESSAGE_ICON_ATTRIBUTE_NAME);

		if (streamProviderName != null) {
			StreamProvider streamProvider = context.get(streamProviderName, StreamProvider.class);
			result.setMessageIcon(streamProvider);
		}
	}

}
