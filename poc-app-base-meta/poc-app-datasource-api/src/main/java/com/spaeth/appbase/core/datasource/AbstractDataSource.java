package com.spaeth.appbase.core.datasource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.spaeth.appbase.core.datasource.builder.DataSourceBuilder;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener.AccessPolicyChangeEvent;
import com.spaeth.appbase.core.datasource.event.CommitListener;
import com.spaeth.appbase.core.datasource.event.CommitListener.CommitEvent;
import com.spaeth.appbase.core.datasource.event.CommitListener.CommitException;
import com.spaeth.appbase.core.datasource.event.DataSourceEvent;
import com.spaeth.appbase.core.datasource.event.ValidationListener;
import com.spaeth.appbase.core.datasource.event.ValidationListener.ValidationEvent;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener.DataSourceValueChangedEvent;
import com.spaeth.appbase.core.datasource.validation.ConstraintViolation;
import com.spaeth.appbase.core.datasource.validation.Validator;
import com.spaeth.appbase.core.security.model.AccessPolicy;

/**
 * Abstract implementation of datasource.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 */
public abstract class AbstractDataSource<BuilderType extends DataSourceBuilder<?>> implements DataSource {

	private final DataSource owner;
	private final DataSourceMeta meta;

	private AccessPolicy accessPolicy = AccessPolicy.WRITE;
	private CreateTemplate<Object, DataSource> createTemplate;

	private final Map<String, DataSource> dataSources = new HashMap<String, DataSource>();

	private final List<Validator> validators = new ArrayList<Validator>();
	private final List<ValueChangeListener> dataSourceValueChangeListeners = new ArrayList<ValueChangeListener>();
	private final List<AccessPolicyChangeListener> dataSourceAccessPolicyChangeListeners = new ArrayList<AccessPolicyChangeListener>();
	private final List<ValidationListener> dataSourceValidationListeners = new ArrayList<ValidationListener>();
	private final List<AccessPolicyProvider> dataSourceAccessPolicyProviders = new ArrayList<AccessPolicyProvider>();
	private final List<AccessPolicyManaged> dataSourceAccessPolicyManageds = new ArrayList<AccessPolicyManaged>();
	private final List<CommitListener> dataSourceCommitListeners = new ArrayList<CommitListener>();

	private ValueConverter converter = ValueConverter.DUMMY;

	public AbstractDataSource(final DataSourceMeta meta) {
		super();

		if (meta == null) {
			throw new NullPointerException("meta for a datasource should not be null");
		}

		this.owner = null;
		this.meta = meta;
		setCreateTemplate(new SimpleCreateTemplate<Object, DataSource>(HashSet.class));
	}

	public AbstractDataSource(final DataSource owner, final DataSourceMeta meta) {
		super();

		if (meta == null) {
			throw new NullPointerException("meta for a datasource should not be null");
		}

		this.owner = owner;
		this.meta = meta;
		setCreateTemplate(new SimpleCreateTemplate<Object, DataSource>(HashSet.class));
	}

	public void addDataSource(final DataSource property) {
		this.dataSources.put(property.getName(), property);
	}

	@Override
	public Collection<String> getDataSourceNames() {
		return dataSources.keySet();
	}

	@Override
	public Collection<DataSource> getDataSources() {
		return dataSources.values();
	}

	@Override
	public DataSource getDataSource(final String... name) {
		if ((name == null) || (name[0] == null)) {
			throw new NullPointerException("property name should not be null");
		}

		final DataSource ds = this.dataSources.get(name[0]);

		if (ds == null) {
			throw new NullPointerException("property " + name[0] + " was not found within properties " + this.dataSources.keySet()
					+ " on DataSource: " + this);
		}

		if (name.length == 1) {
			return ds;
		} else {
			return ds.getDataSource(Arrays.copyOfRange(name, 1, name.length));
		}
	}

	@Override
	public void createNew() {
		createTemplate.create(this);
		// TODO: think this doesn't make sense
		// if (createTemplate != null) {
		// setModel(createTemplate.create(this));
		// }
		// for (DataSource ds : getDataSources()) {
		// ds.createNew();
		// }
	}

	@Override
	public String getName() {
		return meta.getName();
	}

	@Override
	public Class<?> getType() {
		return meta.getType();
	}

	@Override
	public DataSource getOwner() {
		return owner;
	}

	@Override
	public void addDataSourceAccessPolicyChangeListener(final AccessPolicyChangeListener dataSourceAccessPolicyChangeListener) {
		dataSourceAccessPolicyChangeListeners.add(dataSourceAccessPolicyChangeListener);
	}

	@Override
	public void removeDataSourceAccessPolicyChangeListener(final AccessPolicyChangeListener dataSourceAccessPolicyChangeListener) {
		dataSourceAccessPolicyChangeListeners.remove(dataSourceAccessPolicyChangeListener);
	}

	@Override
	public void addDataSourceValueChangeListener(final ValueChangeListener dataSourceValueChangeListener) {
		dataSourceValueChangeListeners.add(dataSourceValueChangeListener);
	}

	@Override
	public void removeDataSourceValueChangeListener(final ValueChangeListener dataSourceValueChangeListener) {
		dataSourceValueChangeListeners.remove(dataSourceValueChangeListener);
	}

	@Override
	public void addDataSourceValidationListener(final ValidationListener dataSourceValidationListener) {
		dataSourceValidationListeners.add(dataSourceValidationListener);
	}

	@Override
	public void removeDataSourceValidationListener(final ValidationListener dataSourceValidationListener) {
		dataSourceValidationListeners.remove(dataSourceValidationListener);
	}

	@Override
	public void addDataSourceCommitListener(final CommitListener dataSourceCommitListener) {
		dataSourceCommitListeners.add(dataSourceCommitListener);
	}

	@Override
	public void removeDataSourceCommitListener(final CommitListener dataSourceCommitListener) {
		dataSourceCommitListeners.remove(dataSourceCommitListener);
	}

	@Override
	public void addValidator(final Validator validator) {
		validators.add(validator);
	}

	@Override
	public void removeValidator(final Validator validator) {
		validators.remove(validator);
	}

	protected void fireOnPostCommitListener(final CommitEvent event) {
		for (final CommitListener l : this.dataSourceCommitListeners) {
			l.onPostCommit(event);
		}
	}

	protected void fireOnPreCommitListener(final CommitEvent event) throws CommitException {
		for (final CommitListener l : this.dataSourceCommitListeners) {
			l.onPreCommit(event);
		}
	}

	protected void fireOnChangeListener(final DataSourceValueChangedEvent event) {
		propagateChangedDown(event);

		notifyOnChangeListeners(event);

		propagateChangesUp(event);
	}

	protected void notifyOnChangeListeners(final DataSourceValueChangedEvent event) {
		ValueChangeListener listeners[] = this.dataSourceValueChangeListeners.toArray(new ValueChangeListener[] {});
		for (final ValueChangeListener l : listeners) {
			try {
				l.onChange(event);
			} catch (final Exception e) {
				System.err.format("error notifying data changed to %s due to: %s", String.valueOf(l), e.getMessage());
				e.printStackTrace();
			}
		}
	}

	protected void propagateChangesUp(final DataSourceValueChangedEvent dsce) {
		if ((getOwner() != null) && !DataSourceUtils.isSuperior(dsce.getSource(), this)) {
			getOwner().process(dsce);
		}
	}

	protected abstract void propagateChangedDown(DataSourceValueChangedEvent dsce);

	protected void propagateAccessPolicyChange(final AccessPolicyChangeEvent dsapc) {
		for (AccessPolicyChangeListener l : dataSourceAccessPolicyChangeListeners) {
			try {
				l.onAccessPolicyChanged(dsapc);
			} catch (Exception e) {
				System.err.format("erro while notifying read only change to %s", String.valueOf(l));
			}
		}
		for (final DataSource ds : getDataSources()) {
			ds.process(dsapc);
		}
	}

	protected void fireOnValidate(final DataSource dataSource, final Collection<ConstraintViolation> result) {
		for (ValidationListener validationListener : dataSourceValidationListeners) {
			validationListener.onValidate(new ValidationEvent(dataSource, result));
		}
	}

	@Override
	public BuilderType getBuilder() {
		BuilderType createdBuilder = createBuilder();
		configureBuilder(createdBuilder);
		return createdBuilder;
	}

	protected void configureBuilder(final BuilderType createdBuilder) {
		createdBuilder.setValueConverter(converter);
		createdBuilder.setName(getName());
		createdBuilder.setType(getType());
		createdBuilder.setAccessPolicy(accessPolicy);

		for (DataSource ds : getDataSources()) {
			createdBuilder.addDataSource(ds.getBuilder());
		}

		for (ValidationListener dsvl : dataSourceValidationListeners) {
			createdBuilder.addValidationListener(dsvl);
		}

		for (AccessPolicyChangeListener dsapcl : dataSourceAccessPolicyChangeListeners) {
			createdBuilder.addAccessPolicyChangeListener(dsapcl);
		}

		for (ValueChangeListener dsvcl : dataSourceValueChangeListeners) {
			createdBuilder.addValueChangeListener(dsvcl);
		}

		for (AccessPolicyProvider dsapp : dataSourceAccessPolicyProviders) {
			createdBuilder.addAccessPolicyProvider(dsapp);
		}

		for (AccessPolicyManaged dsapm : dataSourceAccessPolicyManageds) {
			createdBuilder.addAccessPolicyManaged(dsapm);
		}

		for (Validator v : validators) {
			createdBuilder.addValidator(v);
		}

		for (CommitListener cl : dataSourceCommitListeners) {
			createdBuilder.addCommitListener(cl);
		}
	}

	@Override
	public void addDataSourceAccessPolicyProvider(final AccessPolicyProvider dataSourceAccessPolicyProvider) {
		dataSourceAccessPolicyProviders.add(dataSourceAccessPolicyProvider);
	}

	@Override
	public void removeDataSourceAccessPolicyProvider(final AccessPolicyProvider dataSourceAccessPolicyProvider) {
		dataSourceAccessPolicyProviders.remove(dataSourceAccessPolicyProvider);
	}

	@Override
	public void addDataSourceAccessPolicyManaged(final AccessPolicyManaged dataSourceAccessPolicyManaged) {
		dataSourceAccessPolicyManageds.add(dataSourceAccessPolicyManaged);
	}

	@Override
	public void removeDataSourceAccessPolicyManaged(final AccessPolicyManaged dataSourceAccessPolicyManaged) {
		dataSourceAccessPolicyManageds.remove(dataSourceAccessPolicyManaged);
	}

	protected abstract BuilderType createBuilder();

	protected Collection<ConstraintViolation> executeValidators() {
		final Set<ConstraintViolation> violations = new HashSet<ConstraintViolation>();
		for (final Validator v : this.validators) {
			violations.addAll(v.validate(this));
		}
		return violations;
	}

	@Override
	public void setAccessPolicy(final AccessPolicy accessPolicy) {
		if (this.accessPolicy == accessPolicy) {
			return;
		}

		final AccessPolicy oldAccessPolicy = this.accessPolicy;

		this.accessPolicy = accessPolicy;

		fireOnReadOnlyChange(this, oldAccessPolicy, this.accessPolicy);
	}

	protected void fireOnReadOnlyChange(final DataSource dataSource, final AccessPolicy oldAccessPolicy, final AccessPolicy newAccessPolicy) {
		AccessPolicyChangeEvent dsapc = new AccessPolicyChangeEvent(dataSource, oldAccessPolicy, newAccessPolicy);
		propagateAccessPolicyChange(dsapc);

		for (AccessPolicyManaged dsapm : dataSourceAccessPolicyManageds) {
			dsapm.refreshAccessPolicy();
		}
	}

	public void setCreateTemplate(final CreateTemplate<Object, DataSource> createTemplate) {
		if (createTemplate == null) {
			throw new NullPointerException();
		}
		this.createTemplate = createTemplate;
	}

	@Override
	public final AccessPolicy getAccessPolicy() {
		AccessPolicy result = this.accessPolicy;
		DataSource owner = getOwner();
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
	public Object getModel() {
		return getInternal();
	}

	@Override
	public Object get() {

		checkReadPermission();

		return converter.getValueFromModel(this, getModel());

	}

	protected abstract Object getInternal();

	protected void checkReadPermission() {
		if (getAccessPolicy() == AccessPolicy.NONE) {
			throw new IllegalStateException("no permission to read dataSource value");
		}
	}

	protected void checkWritePermission() {
		if (getAccessPolicy() != AccessPolicy.WRITE) {
			throw new IllegalStateException("no permission to write datasource value");
		}
	}

	@Override
	public void setModel(final Object value) {
		// core
		setInternal(this, value);

	}

	@Override
	public void set(final Object value) {
		// security
		checkWritePermission();

		setModel(converter.getModelFromValue(this, value));
	}

	protected abstract void setInternal(DataSource abstractDataSource, Object value);

	@Override
	public void process(final DataSourceEvent event) {
		if (event instanceof DataSourceValueChangedEvent) {
			processDataSourceValueChangeEvent((DataSourceValueChangedEvent) event);
		} else if (event instanceof AccessPolicyChangeEvent) {
			processDataSourceAccessPolicyChangeEvent((AccessPolicyChangeEvent) event);
		}
	}

	protected void processDataSourceValueChangeEvent(final DataSourceValueChangedEvent event) {
		if (DataSourceUtils.isInferior(event.getSource(), this)) {
			notifyOnChangeListeners(event);
			propagateChangesUp(event);
		}
	}

	protected void processDataSourceAccessPolicyChangeEvent(final AccessPolicyChangeEvent event) {
		if (DataSourceUtils.isSuperior(event.getSource(), this)) {
			propagateAccessPolicyChange(event);
		}
	}

	@Override
	public Collection<ConstraintViolation> validate() {
		final Collection<ConstraintViolation> result = new HashSet<ConstraintViolation>();
		result.addAll(executeValidators());

		for (final DataSource ds : getDataSources()) {
			result.addAll(ds.validate());
		}

		fireOnValidate(this, result);

		return result;
	}

	@Override
	public void accept(final DataSourceVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public DataSourceMeta getMeta() {
		return meta;
	}

	@Override
	public String toString() {
		String ownerString = "ROOT";
		if (owner != null) {
			ownerString = owner.toString();
		}
		return String.format("DataSource[%s](%s) <- {%s}", this.getClass().getName(), meta.getName(), ownerString);
	}

	@Override
	public DataSource getCoreDataSource() {
		return this;
	}

	@Override
	public void setValueConverter(final ValueConverter valueConverter) {
		if (valueConverter == null) {
			this.converter = ValueConverter.DUMMY;
		} else {
			this.converter = valueConverter;
		}
	}

	@Override
	public ValueConverter getValueConverter() {
		return converter;
	}

}
