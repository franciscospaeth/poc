package com.spaeth.appbase.adds.xmlview.service;

import static com.spaeth.appbase.adds.xmlview.service.XmlViewTranslatorConstants.DATASOURCE_ATTRIBUTE_NAME;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewAttributeExpectedException;
import com.spaeth.appbase.adds.xmlview.exception.XmlViewException;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.stream.DataSourceStreamProvider;
import com.spaeth.appbase.core.datasource.DataSource;

public class DataSourceStreamProviderTranslator extends AbstractXmlTranslator<StreamProvider> {

	public DataSourceStreamProviderTranslator(FrontXmlTranslator frontTranslator) {
		super(frontTranslator);
	}

	@Override
	protected StreamProvider createResult(Element source, XmlViewContext context) {
		String dataSourceName = XmlViewTranslatorHelper.readAttribute(source, DATASOURCE_ATTRIBUTE_NAME);

		if (StringUtils.isEmpty(dataSourceName)) {
			throw new XmlViewAttributeExpectedException(DataSourceStreamProvider.class, DATASOURCE_ATTRIBUTE_NAME);
		}

		DataSource dataSource = context.getDataSource(dataSourceName);

		if (dataSource == null) {
			throw new XmlViewException(String.format(
					"data source '%s' is not accessible to be used as a stream provider", dataSourceName));
		}

		return new DataSourceStreamProvider(dataSource);
	}

}
