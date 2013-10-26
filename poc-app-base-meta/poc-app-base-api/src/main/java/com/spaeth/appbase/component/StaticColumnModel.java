package com.spaeth.appbase.component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.component.api.IDataGridColumn;
import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.DataSource;

public class StaticColumnModel implements ColumnModel {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<String, String> properties = new LinkedHashMap<String, String>();

	@Override
	public List<IDataGridColumn> getColumns(final CollectionDataSource dataSource) {
		List<IDataGridColumn> result = new ArrayList<IDataGridColumn>();

		for (Entry<String, String> e : properties.entrySet()) {
			DataSource ds = dataSource
					.getDataSource(StringUtils.splitByWholeSeparatorPreserveAllTokens(e.getKey(), "."));
			if (ds == null) {
				IllegalStateException ex = new IllegalStateException("column " + e.getKey() + " not found");
				logger.error(ex.getMessage(), ex);
				throw ex;
			}
			result.add(new DataGridColumn(ds.getType(), e.getKey(), e.getValue()));
		}

		return result;
	}

	public void addColumn(final String property, final String caption) {
		properties.put(property, caption);
	}

}
