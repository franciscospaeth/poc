package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.CAPTION_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.DATASOURCE_ATTRIBUTE_NAME;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readAttribute;
import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorHelper.readI18NAttribute;

import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.component.PageSelector;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.decorators.PagedDataSourceDecorator;
import com.spaeth.appbase.service.I18NSupport;

public class XmlPageSelectorTranslator extends AbstractXmlVisualComponentTranslator<PageSelector> {

	private final I18NSupport i18nSupport;

	public XmlPageSelectorTranslator(final FrontXmlTranslator frontTranslator, final I18NSupport i18nSupport) {
		super(frontTranslator);
		this.i18nSupport = i18nSupport;
	}

	@Override
	protected PageSelector createResult(final Element source, final XmlViewContext context) {
		return new PageSelector();
	}

	@Override
	protected void configure(final PageSelector result, final Element source, final XmlViewContext context) {
		super.configure(result, source, context);

		result.setCaption(readI18NAttribute(source, CAPTION_ATTRIBUTE_NAME, i18nSupport));

		// dataSource
		String dataSourcePath = readAttribute(source, DATASOURCE_ATTRIBUTE_NAME);
		if (dataSourcePath != null) {
			DataSource dataSource = context.getDataSource(dataSourcePath);

			if (dataSource == null) {
				throw new IllegalStateException(
						"no datasource was returned from context when requested datasource for " + dataSourcePath);
			}

			if (!(dataSource instanceof PagedDataSourceDecorator)) {
				throw new IllegalStateException("page status needs a " + PagedDataSourceDecorator.class + " but "
						+ dataSource.getClass() + " was given");
			}

			result.setPagedDataSource((PagedDataSourceDecorator) dataSource);
		}
	}

}
