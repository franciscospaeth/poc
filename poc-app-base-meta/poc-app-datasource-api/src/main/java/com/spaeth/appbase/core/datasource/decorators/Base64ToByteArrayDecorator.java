package com.spaeth.appbase.core.datasource.decorators;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.builder.DataSourceBuilder;
import com.spaeth.appbase.core.datasource.decorators.builder.Base64ToByteArrayDecoratorBuilder;

public class Base64ToByteArrayDecorator extends AbstractDataSourceDecorator<DataSource> {

	public Base64ToByteArrayDecorator(final DataSource decorated) {
		super(decorated);
		if (decorated.getType() != String.class) {
			throw new IllegalArgumentException("the given dataSource cannot be decorated with base64 functionality because type MUST be "
					+ String.class + " but found " + decorated.getType());
		}
	}

	@Override
	public void setModel(final Object value) {
		decorated.setModel(Base64.encodeBase64((byte[]) value));
	}

	@Override
	public Object getModel() {
		if (StringUtils.isEmpty(ObjectUtils.toString(decorated.getModel()).trim())) {
			return null;
		}
		return Base64.decodeBase64(String.valueOf(decorated.getModel()));
	}

	@Override
	protected DataSourceBuilder<?> decorate(final DataSourceBuilder<?> builder) {
		builder.addDecorator(new Base64ToByteArrayDecoratorBuilder());
		return builder;
	}

}
