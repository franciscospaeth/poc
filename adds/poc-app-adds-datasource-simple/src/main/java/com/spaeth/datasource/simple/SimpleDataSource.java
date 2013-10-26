package com.spaeth.datasource.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.SecurityManaged;
import com.spaeth.appbase.model.AccessPolicy;
import com.spaeth.datasource.DataSource;
import com.spaeth.datasource.DataSourceListener;
import com.spaeth.datasource.DataSourceVisitor;
import com.spaeth.datasource.security.DataSourceSecurityConstraint;
import com.spaeth.datasource.validation.ConstraintViolation;
import com.spaeth.datasource.validation.ValidationResult;
import com.spaeth.datasource.validation.Validator;

public class SimpleDataSource implements DataSource {

	private final Logger logger = LoggerFactory
			.getLogger(SimpleDataSource.class);

	private final SimpleDataSource owner;
	private AccessPolicy securityConstraintDataAccessPolicy = AccessPolicy.WRITE;
	private AccessPolicy dataAccessPolicy = AccessPolicy.WRITE;
	private String name;
	private Object initialValue;
	private Object value;
	private Class<?> type;
	private final Map<String, SimpleDataSource> properties = new HashMap<String, SimpleDataSource>();
	private final List<DataSourceListener> listeners = new ArrayList<DataSourceListener>();
	private final List<DataSourceSecurityConstraint> securityConstraints = new ArrayList<DataSourceSecurityConstraint>();
	private final List<SecurityManaged> cascadeSecurityEvaluation = new ArrayList<SecurityManaged>();
	private final List<Validator> validators = new ArrayList<Validator>();
	private PropagationStrategy propagationStrategy = DefaultSimplePropagationStrategy.INSTANCE;

	SimpleDataSource(final SimpleDataSource owner) {
		super();
		this.owner = owner;
	}

	SimpleDataSource(final SimpleDataSource owner, final String name,
			final AccessPolicy dataAccessPolicy, final Class<?> type,
			final Object initialValue) {
		super();
		this.owner = owner;
		this.name = name;
		this.dataAccessPolicy = dataAccessPolicy;
		this.type = type;
		this.initialValue = initialValue;
		this.value = initialValue;
	}

	public void setSecurityConstraintDataAccessPolicy(
			final AccessPolicy securityConstraintDataAccessPolicy) {
		final AccessPolicy newSA = getDataAccessPolicy();
		this.securityConstraintDataAccessPolicy = securityConstraintDataAccessPolicy;
		final AccessPolicy sa = newSA;
		if (sa != newSA) {
			fireOnReadOnlyChangeListener(this, sa, newSA);
		}
	}

	AccessPolicy getSecurityConstraintDataAccessPolicy() {
		return this.securityConstraintDataAccessPolicy;
	}

	@Override
	public AccessPolicy getDataAccessPolicy() {
		AccessPolicy result = this.dataAccessPolicy;
		if (this.dataAccessPolicy.getValue() > this.securityConstraintDataAccessPolicy
				.getValue()) {
			result = this.securityConstraintDataAccessPolicy;
		}
		if ((this.owner != null)
				&& (result.getValue() > this.owner.getDataAccessPolicy()
						.getValue())) {
			result = this.owner.getDataAccessPolicy();
		}
		return result;
	}

	@Override
	public void setDataAccessPolicy(final AccessPolicy dataAccessPolicy) {
		if (this.dataAccessPolicy == dataAccessPolicy) {
			return;
		}

		final AccessPolicy oldSecurityAccessibility = this.dataAccessPolicy;

		this.dataAccessPolicy = dataAccessPolicy;

		fireOnReadOnlyChangeListener(this, oldSecurityAccessibility,
				this.dataAccessPolicy);
	}

	public void setPropagationStrategy(
			final PropagationStrategy propagationStrategy) {
		this.propagationStrategy = propagationStrategy;
	}

	public PropagationStrategy getPropagationStrategy() {
		return this.propagationStrategy;
	}

	@Override
	public boolean isChanged() {
		if (this.value == this.initialValue) {
			return false;
		}
		if ((this.initialValue == null) ^ (this.value == null)) {
			return true;
		}
		return !this.initialValue.equals(this.value);
	}

	@Override
	public SimpleDataSource getOwner() {
		return this.owner;
	}

	@Override
	public Class<?> getType() {
		return this.type;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object get() {
		// security
		if (getDataAccessPolicy() == AccessPolicy.NONE) {
			throw new IllegalStateException(
					"no permission to read dataSource value");
		}

		// core
		return getCore();
	}

	Object getCore() {
		return this.value;
	}

	@Override
	public void set(final Object value) {
		// change needed?
		if (ObjectUtils.equals(value, this.value)) {
			return;
		}

		// security
		if (getDataAccessPolicy() != AccessPolicy.WRITE) {
			throw new IllegalStateException(
					"no permission to write datasource value");
		}

		// core
		setCore(this, value);
	}

	void setCore(final DataSource eventGenerator, final Object value) {
		// changing
		final Object oldValue = this.value;
		this.value = value;

		// notifying
		fireOnChangeListener(eventGenerator, oldValue, value);

		// notify each child that its value could be changed based on parent, if
		// comes from parent he is responsible to
		// update all tree, no no action is needed
		if ((this.propagationStrategy != null)) {
			this.propagationStrategy.propagate(this, oldValue, value);
		}

	}

	protected void fireOnReadOnlyChangeListener(final DataSource sender,
			final AccessPolicy oldSecurityAccessibility,
			final AccessPolicy newDataAccessPolicy) {
		for (final DataSourceListener dsl : this.listeners) {
			try {
				dsl.onSecurityAccessibilityChanged(sender,
						oldSecurityAccessibility, newDataAccessPolicy);
			} catch (final Exception e) {
				this.logger.error(e.getMessage(), e);
			}
		}
		for (final SimpleDataSource ds : this.properties.values()) {
			ds.fireOnReadOnlyChangeListener(sender, oldSecurityAccessibility,
					newDataAccessPolicy);
		}
	}

	protected void fireOnChangeListener(final DataSource sender,
			final Object oldValue, final Object newValue) {
		// refresh security for all related
		refreshSecurityEvaluations();

		// notify listeners
		for (final DataSourceListener dsl : this.listeners) {
			try {
				dsl.onDataChanged(sender, oldValue, newValue);
			} catch (final Exception e) {
				this.logger.error(
						"error notifying data changed to {} due to: {}",
						new String[] { String.valueOf(dsl), e.getMessage() });
			}
		}

		// notify parent to let it now that he is changed
		if ((this.owner != null) && !isMySuperiorDataSource(sender)) {
			this.owner.fireOnChangeListener(sender, oldValue, newValue);
		}

	}

	@Override
	public void refreshSecurityEvaluations() {
		AccessPolicy result = AccessPolicy.WRITE;
		for (final DataSourceSecurityConstraint sc : this.securityConstraints) {
			final AccessPolicy securityConstraintSA = sc
					.getAccessibility(this);
			if (result.getValue() > securityConstraintSA.getValue()) {
				result = securityConstraintSA;
			}
		}
		setSecurityConstraintDataAccessPolicy(result);
		for (final SecurityManaged sm : this.cascadeSecurityEvaluation) {
			sm.refreshSecurityEvaluations();
		}
	}

	private boolean isMySuperiorDataSource(final DataSource sender) {
		if (this.owner == null) {
			return false;
		}
		if (sender == this.owner) {
			return true;
		}
		return this.owner.isMySuperiorDataSource(sender);
	}

	@Override
	public Collection<String> getPropertyNames() {
		return this.properties.keySet();
	}

	@Override
	public DataSource getProperty(final String... name) {

		if ((name == null) || (name[0] == null)) {
			throw new NullPointerException("property name should not be null");
		}

		final DataSource ds = this.properties.get(name[0]);

		if (ds == null) {
			throw new NullPointerException("Property " + Arrays.toString(name)
					+ " was not found within properties "
					+ this.properties.keySet() + " on DataSource: " + this);
		}

		if (name.length == 1) {
			return ds;
		} else {
			return ds.getProperty(Arrays.copyOfRange(name, 1, name.length));
		}
	}

	public void addProperty(final DataSource property) {
		this.properties.put(property.getName(), (SimpleDataSource) property);
	}

	@Override
	public void addDataSourceListener(final DataSourceListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeDataSourceListener(final DataSourceListener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void accept(final DataSourceVisitor visitor) {
		visitor.visit(this);
	}

	private Collection<ConstraintViolation> executeValidation() {
		final Set<ConstraintViolation> violations = new HashSet<ConstraintViolation>();
		for (final Validator v : this.validators) {
			violations.addAll(v.validate(this));
		}
		return violations;
	}

	@Override
	public ValidationResult validate() {
		final Collection<ConstraintViolation> result = new HashSet<ConstraintViolation>();
		final Collection<ConstraintViolation> violations = executeValidation();
		notifyValidation(violations);
		result.addAll(violations);

		for (final DataSource ds : this.properties.values()) {
			result.addAll(ds.validate().getViolations());
		}

		return new ValidationResult(result);
	}

	private void notifyValidation(
			final Collection<ConstraintViolation> violations) {
		for (final DataSourceListener dsl : this.listeners) {
			dsl.onValidate(this, violations);
		}
	}

	public void addValidator(final Validator validator) {
		this.validators.add(validator);
	}

	public void removeValidator(final Validator validator) {
		this.validators.remove(validator);
	}

	public void addSecurityConstraint(
			final DataSourceSecurityConstraint... securityConstraints) {
		Collections.addAll(this.securityConstraints, securityConstraints);
		refreshSecurityEvaluations();
	}

	public void removeSecurityConstraint(
			final DataSourceSecurityConstraint... securityConstraints) {
		final List<DataSourceSecurityConstraint> l = Arrays
				.asList(securityConstraints);
		CollectionUtils.subtract(this.securityConstraints, l);
		refreshSecurityEvaluations();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public static final class DefaultSimplePropagationStrategy implements
			PropagationStrategy {

		public static final DefaultSimplePropagationStrategy INSTANCE = new DefaultSimplePropagationStrategy();

		private DefaultSimplePropagationStrategy() {
		}

		@Override
		public void propagate(final SimpleDataSource dataSource,
				final Object oldValue, final Object newValue) {
			for (final String dsn : dataSource.getPropertyNames()) {
				final SimpleDataSource ds = (SimpleDataSource) dataSource
						.getProperty(dsn);
				ds.fireOnChangeListener(dataSource, oldValue, newValue);
			}
		}

	}

}
