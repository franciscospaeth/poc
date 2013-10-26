package com.spaeth.appbase.core.datasource.decorators;

import java.util.Collection;

import com.spaeth.appbase.core.datasource.AccessPolicyManaged;
import com.spaeth.appbase.core.datasource.AccessPolicyProvider;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.DataSourceDiff;
import com.spaeth.appbase.core.datasource.DataSourceMeta;
import com.spaeth.appbase.core.datasource.DataSourceVisitor;
import com.spaeth.appbase.core.datasource.ValueConverter;
import com.spaeth.appbase.core.datasource.builder.DataSourceBuilder;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener;
import com.spaeth.appbase.core.datasource.event.CommitListener;
import com.spaeth.appbase.core.datasource.event.DataSourceEvent;
import com.spaeth.appbase.core.datasource.event.ValidationListener;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.spaeth.appbase.core.datasource.validation.ConstraintViolation;
import com.spaeth.appbase.core.datasource.validation.Validator;
import com.spaeth.appbase.core.security.model.AccessPolicy;

public abstract class AbstractDataSourceDecorator<DataSourceType extends DataSource> implements DataSource {

	protected final DataSourceType decorated;

	public AbstractDataSourceDecorator(final DataSourceType decorated) {
		super();
		this.decorated = decorated;
	}

	@Override
	public Object getModel() {
		return decorated.getModel();
	}

	@Override
	public Object get() {
		return decorated.get();
	}

	@Override
	public void setModel(final Object value) {
		decorated.setModel(value);
	}

	@Override
	public void set(final Object value) {
		decorated.set(value);
	}

	@Override
	public void createNew() {
		decorated.createNew();
	}

	@Override
	public void commit() {
		decorated.commit();
	}

	@Override
	public void reset() {
		decorated.reset();
	}

	@Override
	public void reset(final Object value) {
		decorated.reset(value);
	}

	@Override
	public DataSource getOwner() {
		return decorated.getOwner();
	}

	@Override
	public String getName() {
		return decorated.getName();
	}

	@Override
	public Class<?> getType() {
		return decorated.getType();
	}

	@Override
	public boolean isModified() {
		return decorated.isModified();
	}

	@Override
	public Collection<String> getDataSourceNames() {
		return decorated.getDataSourceNames();
	}

	@Override
	public DataSource getDataSource(final String... name) {
		return decorated.getDataSource(name);
	}

	@Override
	public Collection<DataSource> getDataSources() {
		return decorated.getDataSources();
	}

	@Override
	public Collection<ConstraintViolation> validate() {
		return decorated.validate();
	}

	@Override
	public void setAccessPolicy(final AccessPolicy accessPolicy) {
		decorated.setAccessPolicy(accessPolicy);
	}

	@Override
	public AccessPolicy getAccessPolicy() {
		return decorated.getAccessPolicy();
	}

	@Override
	public void accept(final DataSourceVisitor dataSourceVisitor) {
		decorated.accept(dataSourceVisitor);
	}

	@Override
	public void addDataSourceValueChangeListener(final ValueChangeListener dataSourceValueChangeListener) {
		decorated.addDataSourceValueChangeListener(dataSourceValueChangeListener);
	}

	@Override
	public void removeDataSourceValueChangeListener(final ValueChangeListener dataSourceValueChangeListener) {
		decorated.removeDataSourceValueChangeListener(dataSourceValueChangeListener);
	}

	@Override
	public void addDataSourceAccessPolicyChangeListener(final AccessPolicyChangeListener dataSourceAccessPolicyChangeListener) {
		decorated.addDataSourceAccessPolicyChangeListener(dataSourceAccessPolicyChangeListener);
	}

	@Override
	public void removeDataSourceAccessPolicyChangeListener(final AccessPolicyChangeListener dataSourceAccessPolicyChangeListener) {
		decorated.removeDataSourceAccessPolicyChangeListener(dataSourceAccessPolicyChangeListener);
	}

	@Override
	public void addDataSourceAccessPolicyProvider(final AccessPolicyProvider dataSourceAccessPolicyProvider) {
		decorated.addDataSourceAccessPolicyProvider(dataSourceAccessPolicyProvider);
	}

	@Override
	public void removeDataSourceAccessPolicyProvider(final AccessPolicyProvider dataSourceAccessPolicyProvider) {
		decorated.removeDataSourceAccessPolicyProvider(dataSourceAccessPolicyProvider);
	}

	@Override
	public void addDataSourceAccessPolicyManaged(final AccessPolicyManaged dataSourceAccessPolicyManaged) {
		decorated.addDataSourceAccessPolicyManaged(dataSourceAccessPolicyManaged);
	}

	@Override
	public void removeDataSourceAccessPolicyManaged(final AccessPolicyManaged dataSourceAccessPolicyManaged) {
		decorated.removeDataSourceAccessPolicyManaged(dataSourceAccessPolicyManaged);
	}

	@Override
	public void addDataSourceValidationListener(final ValidationListener dataSourceValidationListener) {
		decorated.addDataSourceValidationListener(dataSourceValidationListener);
	}

	@Override
	public void removeDataSourceValidationListener(final ValidationListener dataSourceValidationListener) {
		decorated.removeDataSourceValidationListener(dataSourceValidationListener);
	}

	@Override
	public void addValidator(final Validator validator) {
		decorated.addValidator(validator);
	}

	@Override
	public void removeValidator(final Validator validator) {
		decorated.removeValidator(validator);
	}

	@Override
	public void process(final DataSourceEvent event) {
		decorated.process(event);
	}

	@Override
	public DataSourceBuilder<?> getBuilder() {
		return decorated.getBuilder();
	}

	protected abstract DataSourceBuilder<?> decorate(DataSourceBuilder<?> builder);

	@Override
	public DataSourceMeta getMeta() {
		return decorated.getMeta();
	}

	@Override
	public void addDataSourceCommitListener(final CommitListener dataSourceCommitListener) {
		decorated.addDataSourceCommitListener(dataSourceCommitListener);
	}

	@Override
	public void removeDataSourceCommitListener(final CommitListener dataSourceCommitListener) {
		decorated.removeDataSourceCommitListener(dataSourceCommitListener);
	}

	@Override
	public DataSourceDiff getDiff() {
		return decorated.getDiff();
	}

	@Override
	public DataSource getCoreDataSource() {
		return decorated.getCoreDataSource();
	}

	@Override
	public ValueConverter getValueConverter() {
		return decorated.getValueConverter();
	}

	@Override
	public void setValueConverter(final ValueConverter valueConverter) {
		decorated.setValueConverter(valueConverter);
	}

}
