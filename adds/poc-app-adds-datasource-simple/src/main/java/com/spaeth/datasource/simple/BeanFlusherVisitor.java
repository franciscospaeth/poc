package com.spaeth.datasource.simple;

import java.util.Stack;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.datasource.DataSource;
import com.spaeth.datasource.DataSourceVisitor;

public class BeanFlusherVisitor implements DataSourceVisitor {

	private final Logger logger = LoggerFactory.getLogger(SimpleDataSource.class);

	private final Stack<String> visitingStack = new Stack<String>();
	private final Object bean;

	private boolean valid = false;

	public BeanFlusherVisitor(final Object bean) {
		if (bean == null) {
			throw new NullPointerException("Bean in which properties shall be flushed to should not be null.");
		}
		this.bean = bean;
		this.visitingStack.push("");
	}

	@Override
	public void visit(final DataSource dataSource) {
		if (!this.valid) {
			this.valid = dataSource.validate().isValid();
			if (!this.valid) {
				this.logger.debug("Flusher visiting process was interrupted due to invalid datasource.");
				return;
			}
		}

		this.logger.debug("visiting element with prefix '{}'", this.visitingStack.peek());

		if (dataSource.isChanged() && !"".equals(this.visitingStack.peek())) {
			try {
				PropertyUtils.setNestedProperty(this.bean, this.visitingStack.peek(), dataSource.get());
			} catch (final Exception e) {
				this.logger.error(e.getMessage(), e);
			}
		} else {
			this.logger.debug("element with prefix '{}' was ignored", this.visitingStack.peek());
		}

		if (dataSource.getPropertyNames().size() > 0 && dataSource.get() != null) {
			for (final String propertyName : dataSource.getPropertyNames()) {
				final StringBuilder visitingProperty = new StringBuilder(this.visitingStack.peek());
				if (!("".equals(visitingProperty.toString()))) {
					visitingProperty.append('.');
				}
				visitingProperty.append(propertyName);
				this.visitingStack.push(visitingProperty.toString());
				dataSource.getProperty(propertyName).accept(this);
				this.visitingStack.pop();
			}
		}
	}

}
