package com.spaeth.datasource.simple;

import java.util.ArrayList;
import java.util.List;

import com.spaeth.appbase.model.AccessPolicy;
import com.spaeth.datasource.DataSourceListener;
import com.spaeth.datasource.security.DataSourceSecurityConstraint;
import com.spaeth.datasource.validation.Validator;

public class SimpleDataSourceBuilder {

	private final SimpleDataSourceBuilder simpleDataSourceBuilder;
	private String name;
	private Object initialValue;
	private AccessPolicy accessibility = null;
	private Class<?> type = null;
	private final List<DataSourceListener> dataSourceListeners = new ArrayList<DataSourceListener>();
	private final List<SimpleDataSourceBuilder> propertyDataSourceBuilders = new ArrayList<SimpleDataSourceBuilder>();
	private SimpleDataSourceBuilderHelper builderHelper;
	private boolean initialValueSet = false;
	private final List<Validator> validators = new ArrayList<Validator>();
	private final List<DataSourceSecurityConstraint> securityConstraints = new ArrayList<DataSourceSecurityConstraint>();
	private PropagationStrategy propagationStrategy = null;

	public SimpleDataSourceBuilder() {
		this.simpleDataSourceBuilder = null;
	}

	public SimpleDataSourceBuilder(final SimpleDataSourceBuilder simpleDataSourceBuilder) {
		this.simpleDataSourceBuilder = simpleDataSourceBuilder;
	}

	public SimpleDataSourceBuilder withName(final String name) {
		this.name = name;
		return this;
	}

	public SimpleDataSourceBuilder withAccessibilityPolicy(final AccessPolicy securityAccessibility) {
		this.accessibility = securityAccessibility;
		return this;
	}

	public SimpleDataSourceBuilder withType(final Class<?> type) {
		this.type = type;
		return this;
	}

	public SimpleDataSourceBuilder withInitialValue(final Object initialValue) {
		this.initialValue = initialValue;
		this.initialValueSet = true;
		return this;
	}

	public SimpleDataSourceBuilder withListener(final DataSourceListener dataSourceListener) {
		if (dataSourceListener == null) {
			throw new NullPointerException("Provided listener should not be null!");
		}
		this.dataSourceListeners.add(dataSourceListener);
		return this;
	}

	public SimpleDataSourceBuilder withValidator(final Validator validator) {
		this.validators.add(validator);
		return this;
	}

	public SimpleDataSourceBuilder add() {
		if (this.simpleDataSourceBuilder == null) {
			throw new NullPointerException("The property being built is already the root.");
		}
		return this.simpleDataSourceBuilder.withProperty(this);
	}

	public SimpleDataSourceBuilder newProperty(final String name) {
		return new SimpleDataSourceBuilder(this).withName(name);
	}

	public SimpleDataSourceBuilder newProperty() {
		return new SimpleDataSourceBuilder(this);
	}

	public SimpleDataSourceBuilder withProperty(final SimpleDataSourceBuilder simpleDataSourceBuilder) {
		this.propertyDataSourceBuilders.add(simpleDataSourceBuilder);
		return this;
	}

	public SimpleDataSourceBuilder withSimpleDataSourceBuilderHelper(
			final SimpleDataSourceBuilderHelper simpleDataSourceBuilderHelper) {
		this.builderHelper = simpleDataSourceBuilderHelper;
		return this;
	}

	public SimpleDataSourceBuilder withPropagationStrategy(final PropagationStrategy propagationStrategy) {
		this.propagationStrategy = propagationStrategy;
		return this;
	}

	private SimpleDataSource build(final SimpleDataSource owner) {

		// default values
		Class<?> type = this.type;
		Object initialValue = this.initialValue;
		AccessPolicy accessibility = this.accessibility == null ? AccessPolicy.WRITE
				: this.accessibility;

		if (this.builderHelper != null) {
			// type retrieval
			if (this.type == null) {
				type = this.builderHelper.getTypeFor(owner, this.name);
			}
			// initial value
			if (!this.initialValueSet) {
				initialValue = this.builderHelper.getInitialValueFor(owner, this.name);
			}
			// readOnly
			if (this.accessibility == null) {
				final AccessPolicy acc = this.builderHelper.getSecurityAccessibility(owner, this.name);
				if (acc != null) {
					accessibility = acc;
				}
			}
		}

		// building it
		final SimpleDataSource simpleDataSource = new SimpleDataSource(owner, this.name, accessibility, type,
				initialValue);

		for (final DataSourceSecurityConstraint sc : this.securityConstraints) {
			simpleDataSource.addSecurityConstraint(sc);
		}

		for (final DataSourceListener dspl : this.dataSourceListeners) {
			simpleDataSource.addDataSourceListener(dspl);
		}

		for (final Validator v : this.validators) {
			simpleDataSource.addValidator(v);
		}

		if (this.propagationStrategy != null) {
			simpleDataSource.setPropagationStrategy(this.propagationStrategy);
		} else {
			simpleDataSource.setPropagationStrategy(owner.getPropagationStrategy());
		}

		for (final SimpleDataSourceBuilder dsb : this.propertyDataSourceBuilders) {
			if (dsb.propagationStrategy == null) {
				dsb.propagationStrategy = this.propagationStrategy;
			}
			if ((dsb.builderHelper == null) && (this.builderHelper != null)) {
				dsb.withSimpleDataSourceBuilderHelper(this.builderHelper);
			}
			simpleDataSource.addProperty(dsb.build(simpleDataSource));
		}

		return simpleDataSource;
	}

	public SimpleDataSource build() {
		return build(null);
	}

}
