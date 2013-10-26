package com.spaeth.appbase.core.datasource;

import java.util.Collection;
import java.util.Collections;

import com.spaeth.appbase.core.datasource.builder.DataSourceBuilder;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener;
import com.spaeth.appbase.core.datasource.event.CommitListener;
import com.spaeth.appbase.core.datasource.event.DataSourceEvent;
import com.spaeth.appbase.core.datasource.event.ValidationListener;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.spaeth.appbase.core.datasource.validation.ConstraintViolation;
import com.spaeth.appbase.core.datasource.validation.Validator;
import com.spaeth.appbase.core.security.model.AccessPolicy;

class NullDataSource implements DataSource {

	public static final DataSource INSTANCE = new NullDataSource();

	protected NullDataSource() {
	}

	@Override
	public Object getModel() {
		return null;
	}

	@Override
	public Object get() {
		return null;
	}

	@Override
	public void commit() {
	}

	@Override
	public void setModel(final Object value) {
	}

	@Override
	public void set(final Object value) {
	}

	@Override
	public void createNew() {
	}

	@Override
	public void reset() {
	}

	@Override
	public void reset(final Object value) {
	}

	@Override
	public DataSource getOwner() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Class<?> getType() {
		return Object.class;
	}

	@Override
	public boolean isModified() {
		return false;
	}

	@Override
	public Collection<String> getDataSourceNames() {
		return Collections.emptySet();
	}

	@Override
	public DataSource getDataSource(final String... name) {
		return null;
	}

	@Override
	public Collection<DataSource> getDataSources() {
		return Collections.emptySet();
	}

	@Override
	public Collection<ConstraintViolation> validate() {
		return Collections.emptySet();
	}

	@Override
	public void setAccessPolicy(final AccessPolicy accessPolicy) {
	}

	@Override
	public AccessPolicy getAccessPolicy() {
		return AccessPolicy.WRITE;
	}

	@Override
	public void accept(final DataSourceVisitor dataSourceVisitor) {
	}

	@Override
	public void addDataSourceValueChangeListener(final ValueChangeListener dataSourceValueChangeListener) {
	}

	@Override
	public void removeDataSourceValueChangeListener(final ValueChangeListener dataSourceValueChangeListener) {
	}

	@Override
	public void addDataSourceAccessPolicyChangeListener(final AccessPolicyChangeListener dataSourceAccessPolicyChangeListener) {
	}

	@Override
	public void removeDataSourceAccessPolicyChangeListener(final AccessPolicyChangeListener dataSourceAccessPolicyChangeListener) {
	}

	@Override
	public void addDataSourceAccessPolicyProvider(final AccessPolicyProvider dataSourceAccessPolicyProvider) {
	}

	@Override
	public void removeDataSourceAccessPolicyProvider(final AccessPolicyProvider dataSourceAccessPolicyProvider) {
	}

	@Override
	public void addDataSourceAccessPolicyManaged(final AccessPolicyManaged dataSourceAccessPolicyManaged) {
	}

	@Override
	public void removeDataSourceAccessPolicyManaged(final AccessPolicyManaged dataSourceAccessPolicyManaged) {
	}

	@Override
	public void addDataSourceValidationListener(final ValidationListener dataSourceValidationListener) {
	}

	@Override
	public void removeDataSourceValidationListener(final ValidationListener dataSourceValidationListener) {
	}

	@Override
	public void addValidator(final Validator validator) {
	}

	@Override
	public void removeValidator(final Validator validator) {
	}

	@Override
	public void process(final DataSourceEvent event) {
	}

	@Override
	public DataSourceBuilder<?> getBuilder() {
		return null;
	}

	@Override
	public DataSourceMeta getMeta() {
		return null;
	}

	@Override
	public void addDataSourceCommitListener(final CommitListener dataSourceCommitListener) {
	}

	@Override
	public void removeDataSourceCommitListener(final CommitListener dataSourceCommitListener) {
	}

	@Override
	public DataSourceDiff getDiff() {
		return DataSourceDiff.DUMMY;
	}

	@Override
	public DataSource getCoreDataSource() {
		return this;
	}

	@Override
	public ValueConverter getValueConverter() {
		return ValueConverter.DUMMY;
	}

	@Override
	public void setValueConverter(final ValueConverter valueConverter) {
	}

}
