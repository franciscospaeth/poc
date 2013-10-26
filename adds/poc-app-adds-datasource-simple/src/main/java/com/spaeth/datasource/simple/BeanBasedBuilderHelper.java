package com.spaeth.datasource.simple;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

import com.spaeth.appbase.model.AccessPolicy;
import com.spaeth.datasource.DataSourceException;

public class BeanBasedBuilderHelper implements SimpleDataSourceBuilderHelper {

	private final Object bean;

	public BeanBasedBuilderHelper(final Object bean) {
		super();
		this.bean = bean;
	}

	@Override
	public Object getInitialValueFor(final SimpleDataSource dataSource, final String propertyName) {
		try {
			if ((propertyName == null) && (dataSource == null)) {
				return this.bean;
			}
			return PropertyUtils.getProperty(this.bean, getPropertyPath(dataSource, propertyName));
		} catch (final NestedNullException e) {
			return null;
		} catch (final Exception e) {
			throw new DataSourceException(e);
		}
	}

	@Override
	public Class<?> getTypeFor(final SimpleDataSource dataSource, final String propertyName) {
		Class<?> type = this.bean.getClass();

		if ((dataSource == null) && (propertyName == null)) {
			return type;
		}

		if (dataSource != null) {
			type = dataSource.getType();
		}

		final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(type);
		for (final PropertyDescriptor pd : propertyDescriptors) {
			if (propertyName.equals(pd.getName())) {
				return pd.getPropertyType();
			}
		}
		throw new IllegalArgumentException("Property with name " + propertyName + " was not found in type wrapped by "
				+ dataSource);
	}

	@Override
	public AccessPolicy getSecurityAccessibility(final SimpleDataSource dataSource, final String property) {
		if ((dataSource == null) && (property == null)) {
			return null;
		}
		try {
			return PropertyUtils.getPropertyDescriptor(this.bean, getPropertyPath(dataSource, property))
					.getWriteMethod() == null ? AccessPolicy.READ : AccessPolicy.WRITE;
		} catch (NestedNullException e) {
			return null;
		} catch (final Exception e) {
			throw new IllegalArgumentException("Property with name " + property + " is not expected for bean "
					+ this.bean + " configuring datasource " + dataSource + " because: " + e.getMessage(), e);
		}
	}

	protected String getPropertyPath(final SimpleDataSource dataSource, final String path) {
		if (dataSource.getOwner() != null) {
			return getPropertyPath(dataSource.getOwner(), dataSource.getName() + "." + path);
		} else {
			return path;
		}
	}

}
