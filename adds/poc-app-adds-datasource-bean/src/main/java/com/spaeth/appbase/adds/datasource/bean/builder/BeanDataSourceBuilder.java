package com.spaeth.appbase.adds.datasource.bean.builder;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.spaeth.appbase.adds.datasource.bean.BeanDataSource;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.builder.AbstractDataSourceBuilder;
import com.spaeth.appbase.core.datasource.builder.BaseDataSourceBuilder;
import com.spaeth.appbase.core.datasource.builder.DataSourceBuilder;
import com.spaeth.appbase.core.datasource.builder.ValueDataSourceBuilder;

public class BeanDataSourceBuilder extends AbstractDataSourceBuilder<BeanDataSourceBuilder> implements
		ValueDataSourceBuilder<BeanDataSourceBuilder> {

	public BeanDataSourceBuilder() {
		super();
	}

	public static BeanDataSourceBuilder create() {
		return new BeanDataSourceBuilder();
	}

	public BeanDataSourceBuilder(final String name) {
		super(name);
	}

	public BeanDataSourceBuilder(final Class<?> type) {
		super(type);
	}

	public BeanDataSourceBuilder(final String name, final Class<?> type) {
		super(name, type);
	}

	@Override
	protected BeanDataSource create(final DataSource owner) {
		return new BeanDataSource(owner, name, autoCalculateTypeIfNeeded(owner), initialValue);
	}

	@Override
	protected void initialize(final DataSource result) {
		result.getMeta().initialize();
		super.initialize(result);
	}

	@Override
	protected void preBuildCheck(final DataSource owner) {
		super.preBuildCheck(owner);
		PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(autoCalculateTypeIfNeeded(owner));
		List<String> propNames = new ArrayList<String>();

		for (PropertyDescriptor pd : props) {
			propNames.add(pd.getName());
		}

		for (BaseDataSourceBuilder<?> dsb : dataSources) {
			if (!(dsb instanceof BeanDataSourceBuilder)) {
				continue;
			}
			if (!propNames.contains(((DataSourceBuilder<?>) dsb).getName())) {
				throw new IllegalStateException("type of owner's datasource (" + type + ") doesn't hold a property named '"
						+ ((DataSourceBuilder<?>) dsb).getName() + "'");
			}
		}
	}

}