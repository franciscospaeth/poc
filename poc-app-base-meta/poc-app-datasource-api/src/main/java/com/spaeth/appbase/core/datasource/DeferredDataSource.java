package com.spaeth.appbase.core.datasource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.spaeth.appbase.core.datasource.builder.DataSourceBuilder;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener.AccessPolicyChangeEvent;
import com.spaeth.appbase.core.datasource.event.CommitListener;
import com.spaeth.appbase.core.datasource.event.DataSourceEvent;
import com.spaeth.appbase.core.datasource.event.ValidationListener;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener.DataSourceEventCause;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener.DataSourceValueChangedEvent;
import com.spaeth.appbase.core.datasource.validation.ConstraintViolation;
import com.spaeth.appbase.core.datasource.validation.Validator;
import com.spaeth.appbase.core.security.model.AccessPolicy;

class DeferredDataSource implements DataSource {

	protected final ProxyDataSource referenceDataSource;
	protected DataSource dataSource = NullDataSource.INSTANCE;

	private final List<ValueChangeListener> dataSourceValueChangeListeners = new ArrayList<ValueChangeListener>();
	private final List<AccessPolicyProvider> dataSourceAccessPolicyProviders = new ArrayList<AccessPolicyProvider>();
	private final List<AccessPolicyChangeListener> dataSourceAccessPolicyChangeListeners = new ArrayList<AccessPolicyChangeListener>();
	private final List<ValidationListener> dataSourceValidationListeners = new ArrayList<ValidationListener>();
	private final List<CommitListener> dataSourceCommitListeners = new ArrayList<CommitListener>();

	private AccessPolicy accessPolicy = AccessPolicy.WRITE;

	protected final String[] path;

	public DeferredDataSource(final ProxyDataSource referenceDataSource, final String[] path) {
		this.referenceDataSource = referenceDataSource;
		this.path = path;
	}

	@Override
	public void commit() {
		getReferredDataSource().commit();
	}

	protected void setReferredDataSource(final DataSource dataSource) {
		for (ValueChangeListener dataSourceValueChangeListener : dataSourceValueChangeListeners) {
			getReferredDataSource().removeDataSourceValueChangeListener(dataSourceValueChangeListener);
		}
		for (AccessPolicyChangeListener dataSourceAccessPolicyChangeListener : dataSourceAccessPolicyChangeListeners) {
			getReferredDataSource().removeDataSourceAccessPolicyChangeListener(dataSourceAccessPolicyChangeListener);
		}
		for (ValidationListener dataSourceValidationListener : dataSourceValidationListeners) {
			getReferredDataSource().removeDataSourceValidationListener(dataSourceValidationListener);
		}
		for (CommitListener dataSourceCommitListener : dataSourceCommitListeners) {
			getReferredDataSource().removeDataSourceCommitListener(dataSourceCommitListener);
		}
		for (AccessPolicyProvider dsapp : dataSourceAccessPolicyProviders) {
			getReferredDataSource().removeDataSourceAccessPolicyProvider(dsapp);
		}
		this.dataSource = dataSource;
		for (ValueChangeListener dataSourceValueChangeListener : dataSourceValueChangeListeners) {
			getReferredDataSource().addDataSourceValueChangeListener(dataSourceValueChangeListener);
		}
		for (AccessPolicyChangeListener dataSourceAccessPolicyChangeListener : dataSourceAccessPolicyChangeListeners) {
			getReferredDataSource().addDataSourceAccessPolicyChangeListener(dataSourceAccessPolicyChangeListener);
		}
		for (ValidationListener dataSourceValidationListener : dataSourceValidationListeners) {
			getReferredDataSource().addDataSourceValidationListener(dataSourceValidationListener);
		}
		for (CommitListener dataSourceCommitListener : dataSourceCommitListeners) {
			getReferredDataSource().addDataSourceCommitListener(dataSourceCommitListener);
		}
		for (AccessPolicyProvider dsapp : dataSourceAccessPolicyProviders) {
			getReferredDataSource().addDataSourceAccessPolicyProvider(dsapp);
		}

		// notify
		for (ValueChangeListener dataSourceValueChangeListener : dataSourceValueChangeListeners) {
			dataSourceValueChangeListener.onChange(new DataSourceValueChangedEvent(DataSourceEventCause.EVENT_CASCADE, this,
					getReferredDataSource().getModel(), getReferredDataSource().getModel()));
		}
	}

	protected DataSource getReferredDataSource() {
		return dataSource;
	}

	@Override
	public Object getModel() {
		return getReferredDataSource().getModel();
	}

	@Override
	public Object get() {
		return getReferredDataSource().get();
	}

	@Override
	public void setModel(final Object value) {
		getReferredDataSource().setModel(value);
	}

	@Override
	public void set(final Object value) {
		getReferredDataSource().set(value);
	}

	@Override
	public void createNew() {
		getReferredDataSource().createNew();
	}

	@Override
	public void reset() {
		getReferredDataSource().reset();
	}

	@Override
	public void reset(final Object value) {
		getReferredDataSource().reset(value);
	}

	@Override
	public DataSource getOwner() {
		return getReferredDataSource().getOwner();
	}

	@Override
	public String getName() {
		return getMeta().getName();
	}

	@Override
	public Class<?> getType() {
		return getMeta().getType();
	}

	@Override
	public boolean isModified() {
		return getReferredDataSource().isModified();
	}

	@Override
	public Collection<String> getDataSourceNames() {
		Collection<String> result = new ArrayList<String>();
		for (DataSourceMeta dsm : getMeta().getChildrenMeta()) {
			result.add(dsm.getName());
		}
		return result;
	}

	@Override
	public DataSource getDataSource(final String... name) {
		return referenceDataSource.getDataSource(ArrayUtils.addAll(new String[] { getName() }, name));
	}

	@Override
	public Collection<DataSource> getDataSources() {
		return getReferredDataSource().getDataSources();
	}

	@Override
	public Collection<ConstraintViolation> validate() {
		return getReferredDataSource().validate();
	}

	@Override
	public void setAccessPolicy(final AccessPolicy accessPolicy) {
		AccessPolicy oldAccessPolicy = this.accessPolicy;
		this.accessPolicy = accessPolicy;
		if (accessPolicy != oldAccessPolicy) {
			for (AccessPolicyChangeListener dataSourceAccessPolicyChangeListener : dataSourceAccessPolicyChangeListeners) {
				dataSourceAccessPolicyChangeListener
						.onAccessPolicyChanged(new AccessPolicyChangeEvent(this, oldAccessPolicy, accessPolicy));
			}
		}
	}

	@Override
	public AccessPolicy getAccessPolicy() {

		// TODO this should be removed but properties shouldn't be READ just
		// because are NULL (proxies for instance)
		if (referenceDataSource.getRealDataSource() == NullDataSource.INSTANCE) {
			return AccessPolicy.READ;
		}

		// calculate regular access policy
		AccessPolicy result = this.accessPolicy;

		if (this.dataSource.getAccessPolicy().getValue() < result.getValue()) {
			result = this.dataSource.getAccessPolicy();
		}

		DataSource owner = referenceDataSource;
		if ((owner != null) && (result.getValue() > owner.getAccessPolicy().getValue())) {
			result = owner.getAccessPolicy();
		}
		for (final AccessPolicyProvider app : this.dataSourceAccessPolicyProviders) {
			final AccessPolicy securityConstraintAP = app.getAccessPolicy(this);
			if (result.getValue() > securityConstraintAP.getValue()) {
				result = securityConstraintAP;
			}
		}
		return result;
	}

	@Override
	public void accept(final DataSourceVisitor dataSourceVisitor) {
		dataSourceVisitor.visit(this);
	}

	@Override
	public void addDataSourceValueChangeListener(final ValueChangeListener dataSourceValueChangeListener) {
		this.dataSourceValueChangeListeners.add(dataSourceValueChangeListener);
		getReferredDataSource().addDataSourceValueChangeListener(dataSourceValueChangeListener);
	}

	@Override
	public void removeDataSourceValueChangeListener(final ValueChangeListener dataSourceValueChangeListener) {
		getReferredDataSource().removeDataSourceValueChangeListener(dataSourceValueChangeListener);
		this.dataSourceValueChangeListeners.remove(dataSourceValueChangeListener);
	}

	@Override
	public void addDataSourceCommitListener(final CommitListener dataSourceCommitListener) {
		getReferredDataSource().addDataSourceCommitListener(dataSourceCommitListener);
		dataSourceCommitListeners.add(dataSourceCommitListener);
	}

	@Override
	public void removeDataSourceCommitListener(final CommitListener dataSourceCommitListener) {
		getReferredDataSource().removeDataSourceCommitListener(dataSourceCommitListener);
		dataSourceCommitListeners.remove(dataSourceCommitListener);
	}

	@Override
	public void addDataSourceAccessPolicyChangeListener(final AccessPolicyChangeListener dataSourceAccessPolicyChangeListener) {
		this.dataSourceAccessPolicyChangeListeners.add(dataSourceAccessPolicyChangeListener);
		getReferredDataSource().addDataSourceAccessPolicyChangeListener(dataSourceAccessPolicyChangeListener);
	}

	@Override
	public void removeDataSourceAccessPolicyChangeListener(final AccessPolicyChangeListener dataSourceAccessPolicyChangeListener) {
		getReferredDataSource().removeDataSourceAccessPolicyChangeListener(dataSourceAccessPolicyChangeListener);
		this.dataSourceAccessPolicyChangeListeners.remove(dataSourceAccessPolicyChangeListener);
	}

	@Override
	public void addDataSourceAccessPolicyProvider(final AccessPolicyProvider dataSourceAccessPolicyProvider) {
		getReferredDataSource().addDataSourceAccessPolicyProvider(dataSourceAccessPolicyProvider);
	}

	@Override
	public void removeDataSourceAccessPolicyProvider(final AccessPolicyProvider dataSourceAccessPolicyProvider) {
		getReferredDataSource().removeDataSourceAccessPolicyProvider(dataSourceAccessPolicyProvider);
	}

	@Override
	public void addDataSourceAccessPolicyManaged(final AccessPolicyManaged dataSourceAccessPolicyManaged) {
		getReferredDataSource().addDataSourceAccessPolicyManaged(dataSourceAccessPolicyManaged);
	}

	@Override
	public void removeDataSourceAccessPolicyManaged(final AccessPolicyManaged dataSourceAccessPolicyManaged) {
		getReferredDataSource().removeDataSourceAccessPolicyManaged(dataSourceAccessPolicyManaged);
	}

	@Override
	public void addDataSourceValidationListener(final ValidationListener dataSourceValidationListener) {
		this.dataSourceValidationListeners.add(dataSourceValidationListener);
		getReferredDataSource().addDataSourceValidationListener(dataSourceValidationListener);
	}

	@Override
	public void removeDataSourceValidationListener(final ValidationListener dataSourceValidationListener) {
		getReferredDataSource().removeDataSourceValidationListener(dataSourceValidationListener);
		this.dataSourceValidationListeners.remove(dataSourceValidationListener);
	}

	@Override
	public void addValidator(final Validator validator) {
		getReferredDataSource().addValidator(validator);
	}

	@Override
	public void removeValidator(final Validator validator) {
		getReferredDataSource().removeValidator(validator);
	}

	@Override
	public void process(final DataSourceEvent event) {
		getReferredDataSource().process(event);
	}

	@Override
	public DataSourceBuilder<?> getBuilder() {
		return getReferredDataSource().getBuilder();
	}

	@Override
	public DataSourceMeta getMeta() {
		DataSourceMeta meta = referenceDataSource.getMeta();
		pathFor: for (String pathPart : path) {
			for (DataSourceMeta dsm : meta.getChildrenMeta()) {
				if (pathPart.equals(dsm.getName())) {
					meta = dsm;
					continue pathFor;
				}
			}
			throw new IllegalStateException("not able to found the required meta under path: " + Arrays.toString(path) + " from parent: "
					+ referenceDataSource.getName());
		}
		return meta;
	}

	@Override
	public DataSourceDiff getDiff() {
		return getReferredDataSource().getDiff();
	}

	@Override
	public String toString() {
		return "deferred datasource to: " + StringUtils.join(path, '.');
	}

	@Override
	public DataSource getCoreDataSource() {
		return dataSource.getCoreDataSource();
	}

	@Override
	public ValueConverter getValueConverter() {
		return getReferredDataSource().getValueConverter();
	}

	@Override
	public void setValueConverter(final ValueConverter valueConverter) {
		getReferredDataSource().setValueConverter(valueConverter);
	}

}
