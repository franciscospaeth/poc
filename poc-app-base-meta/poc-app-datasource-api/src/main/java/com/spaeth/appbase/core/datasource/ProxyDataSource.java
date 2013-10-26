package com.spaeth.appbase.core.datasource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.core.datasource.builder.DataSourceBuilder;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener;
import com.spaeth.appbase.core.datasource.event.CommitListener;
import com.spaeth.appbase.core.datasource.event.DataSourceEvent;
import com.spaeth.appbase.core.datasource.event.ValidationListener;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.spaeth.appbase.core.datasource.validation.ConstraintViolation;
import com.spaeth.appbase.core.datasource.validation.Validator;
import com.spaeth.appbase.core.security.model.AccessPolicy;

public class ProxyDataSource implements DataSource, AccessPolicyChangeListener, AccessPolicyManaged, ValidationListener,
		ValueChangeListener, Validator, CommitListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final DataSource owner;
	private DataSource dataSourceProxied = NullDataSource.INSTANCE;
	private final String name;
	private final DataSourceMeta meta;
	private ValueConverter converter = ValueConverter.DUMMY;
	/**
	 * true when it is used with the intention to show navigation result
	 */
	private final boolean navigationResultProxy;

	private final Map<String, DeferredDataSource> deferredDataSources = new HashMap<String, DeferredDataSource>();
	// TODO this should be removed or used as cache for
	// meta.getChildrenMeta(String ...)
	private final Map<String, ServingDirective> servingDirectives = new HashMap<String, ServingDirective>();

	private ProxyDataSourceLookup<DataSource, Object> proxyLookup;

	private final List<Validator> validators = new ArrayList<Validator>();
	private final List<ValueChangeListener> dataSourceValueChangeListeners = new ArrayList<ValueChangeListener>();
	private final List<AccessPolicyChangeListener> dataSourceAccessPolicyChangeListeners = new ArrayList<AccessPolicyChangeListener>();
	private final List<ValidationListener> dataSourceValidationListeners = new ArrayList<ValidationListener>();
	private final List<AccessPolicyProvider> dataSourceAccessPolicyProviders = new ArrayList<AccessPolicyProvider>();
	private final List<AccessPolicyManaged> dataSourceAccessPolicyManageds = new ArrayList<AccessPolicyManaged>();
	private final List<CommitListener> dataSourceCommitListeners = new ArrayList<CommitListener>();

	private Object lastLookedUpValue = null;

	/**
	 * @param name
	 * @param proxiedMeta
	 * @param navigationResultProxy
	 *            will let it active even in readOnly mode
	 */
	public ProxyDataSource(final String name, final DataSourceMeta proxiedMeta, final boolean navigationResultProxy) {
		this.name = name;
		this.meta = proxiedMeta;
		this.owner = null;
		this.navigationResultProxy = navigationResultProxy;
	}

	/**
	 * @param name
	 * @param owner
	 * @param proxiedMeta
	 * @param navigationResultProxy
	 *            will let it active even in readOnly mode
	 */
	public ProxyDataSource(final String name, final DataSource owner, final DataSourceMeta proxiedMeta, final boolean navigationResultProxy) {
		this.name = name;
		this.owner = owner;
		this.meta = proxiedMeta;
		this.navigationResultProxy = navigationResultProxy;
	}

	@Override
	public void commit() {
		dataSourceProxied.commit();
	}

	public void addServingDirective(final String name, final ServingDirective servingDirective) {
		servingDirectives.put(name, servingDirective);
	}

	DataSource getRealDataSource() {
		return dataSourceProxied;
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
	public void addDataSourceCommitListener(final CommitListener dataSourceCommitListener) {
		dataSourceCommitListeners.add(dataSourceCommitListener);
	}

	@Override
	public void removeDataSourceCommitListener(final CommitListener dataSourceCommitListener) {
		dataSourceCommitListeners.remove(dataSourceCommitListener);
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
	public void addValidator(final Validator validator) {
		validators.add(validator);
	}

	@Override
	public void removeValidator(final Validator validator) {
		validators.remove(validator);
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

	@Override
	public Object getModel() {
		return dataSourceProxied.getModel();
	}

	@Override
	public Object get() {
		return converter.getValueFromModel(this, getModel());
	}

	@Override
	public void setModel(final Object newValue) {
		proxyLookup.getProviderDataSource().removeDataSourceValueChangeListener(this);
		proxyLookup.getProviderDataSource().addDataSourceValueChangeListener(this);
		DataSource dataSource = proxyLookup.getDataSource(newValue);
		if (dataSource == null) {
			dataSource = NullDataSource.INSTANCE;
		}

		Object oldValue = getModel();

		setDataSourceProxied(dataSource);
		this.lastLookedUpValue = newValue;

		// event propagation
		DataSourceValueChangedEvent event = new DataSourceValueChangedEvent(DataSourceEventCause.SET, this, oldValue, newValue);
		for (ValueChangeListener dsvcl : dataSourceValueChangeListeners) {
			dsvcl.onChange(event);
		}
		if ((getOwner() != null) && !DataSourceUtils.isSuperior(event.getSource(), this)) {
			getOwner().process(event);
		}
	}

	@Override
	public void set(final Object value) {
		setModel(converter.getModelFromValue(this, value));
	}

	@Override
	public void createNew() {
		dataSourceProxied.createNew();
	}

	@Override
	public void reset() {
		dataSourceProxied.reset();
	}

	@Override
	public void reset(final Object value) {
		dataSourceProxied.reset(value);
	}

	@Override
	public DataSource getOwner() {
		return owner;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<?> getType() {
		return dataSourceProxied.getType();
	}

	@Override
	public boolean isModified() {
		return dataSourceProxied.isModified();
	}

	@Override
	public Collection<String> getDataSourceNames() {
		if (dataSourceProxied == null) {
			return Collections.emptySet();
		}
		return dataSourceProxied.getDataSourceNames();
	}

	@Override
	public DataSource getDataSource(final String... name) {
		String normalizedName = StringUtils.join(name, ".");

		DeferredDataSource result = deferredDataSources.get(normalizedName);

		if (result == null) {
			result = createDeferredDataSourceFor(name);
			deferredDataSources.put(normalizedName, result);
		}
		
		DataSource ds = dataSourceProxied.getDataSource(name);
		if (ds == null) {
			if (servingDirectives.get(StringUtils.join(name, ".")) == ServingDirective.COLLECTION) {
				ds = NullCollectionDataSource.INSTANCE;
			} else {
				ds = NullDataSource.INSTANCE;
			}
		}

		result.setReferredDataSource(ds);

		return result;
	}

	private DeferredDataSource createDeferredDataSourceFor(final String... name) {
		DataSourceMeta childrenMeta = meta.getChildrenMeta(name);

		if (childrenMeta == null) {
			throw new IllegalArgumentException("not able to load data source address to " + StringUtils.join(name, '.')
					+ " do it relly exist?");
		}

		if (childrenMeta.getServingDirective() == ServingDirective.COLLECTION) {
			return new DeferredCollectionDataSource(this, name);
		}
		return new DeferredDataSource(this, name);
	}

	@Override
	public Collection<DataSource> getDataSources() {
		HashSet<DataSource> dataSources = new HashSet<DataSource>();
		for (DataSourceMeta dsm : getMeta().getChildrenMeta()) {
			if (deferredDataSources.containsKey(dsm.getName())) {
				dataSources.add(deferredDataSources.get(dsm.getName()));
			} else {
				DeferredDataSource result = createDeferredDataSourceFor(dsm.getName());
				dataSources.add(result);
				deferredDataSources.put(dsm.getName(), result);
			}
		}
		return dataSources;
	}

	@Override
	public Collection<ConstraintViolation> validate() {
		return dataSourceProxied.validate();
	}

	@Override
	public void setAccessPolicy(final AccessPolicy accessPolicy) {
		dataSourceProxied.setAccessPolicy(accessPolicy);
	}

	@Override
	public DataSourceMeta getMeta() {
		return meta;
	}

	@Override
	public AccessPolicy getAccessPolicy() {
		AccessPolicy result = AccessPolicy.WRITE;
		if (!navigationResultProxy) {
			result = dataSourceProxied.getAccessPolicy();
		}

		if (result.getValue() == 0) {
			return result;
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
	public void process(final DataSourceEvent event) {
		dataSourceProxied.process(event);
		if ((getOwner() != null) && !DataSourceUtils.isSuperior(event.getSource(), dataSourceProxied)) {
			getOwner().process(event);
		}
	}

	@Override
	public DataSourceBuilder<?> getBuilder() {
		return dataSourceProxied.getBuilder();
	}

	public void setDataSourceProxied(final DataSource dataSourceProxied) {
		dataSourceProxied.removeDataSourceAccessPolicyChangeListener(this);
		dataSourceProxied.removeDataSourceAccessPolicyManaged(this);
		dataSourceProxied.removeDataSourceValidationListener(this);
		dataSourceProxied.removeDataSourceValueChangeListener(this);
		dataSourceProxied.removeValidator(this);
		dataSourceProxied.removeDataSourceCommitListener(this);

		this.dataSourceProxied = dataSourceProxied;
		for (Entry<String, DeferredDataSource> dse : deferredDataSources.entrySet()) {
			DataSource ds = dataSourceProxied.getDataSource(StringUtils.splitByWholeSeparator(dse.getKey(), "."));
			if (ds == null) {
				if (servingDirectives.get(StringUtils.join(name, ".")) == ServingDirective.COLLECTION) {
					ds = NullCollectionDataSource.INSTANCE;
				} else {
					ds = NullDataSource.INSTANCE;
				}
			}
			dse.getValue().setReferredDataSource(ds);
		}

		dataSourceProxied.addDataSourceAccessPolicyChangeListener(this);
		dataSourceProxied.addDataSourceAccessPolicyManaged(this);
		dataSourceProxied.addDataSourceValidationListener(this);
		dataSourceProxied.addDataSourceValueChangeListener(this);
		dataSourceProxied.addValidator(this);
		dataSourceProxied.addDataSourceCommitListener(this);
	}

	@Override
	public Collection<ConstraintViolation> validate(final DataSource dataSource) {
		ArrayList<ConstraintViolation> result = new ArrayList<ConstraintViolation>();

		for (Validator v : validators) {
			result.addAll(v.validate(this));
		}

		return result;
	}

	@Override
	public void onChange(final DataSourceValueChangedEvent event) {
		boolean comesFromDataSourceProvider = event.getSource().getCoreDataSource()
				.equals(proxyLookup.getProviderDataSource().getCoreDataSource());
		boolean isQualifiedToLookupRefresh = Arrays.asList(DataSourceEventCause.SET, DataSourceEventCause.RESET,
				DataSourceEventCause.COLLECTION_ELEMENT_REMOVED, DataSourceEventCause.EVENT_CASCADE).contains(event.getCause());
		if (comesFromDataSourceProvider && isQualifiedToLookupRefresh) {
			if (proxyLookup.getDataSource(lastLookedUpValue) == null) {
				setModel(null);
			}
		}
		for (final ValueChangeListener l : this.dataSourceValueChangeListeners) {
			try {
				l.onChange(event);
			} catch (final Exception e) {
				logger.warn("error notifying data changed to {} due to: {}", String.valueOf(l), e.getMessage());
				logger.debug(e.getMessage(), e);
			}
		}
	}

	@Override
	public void onValidate(final ValidationEvent event) {
		for (ValidationListener validationListener : dataSourceValidationListeners) {
			validationListener.onValidate(event);
		}
	}

	@Override
	public void refreshAccessPolicy() {
		for (AccessPolicyManaged dsapm : dataSourceAccessPolicyManageds) {
			dsapm.refreshAccessPolicy();
		}
	}

	@Override
	public void onAccessPolicyChanged(final AccessPolicyChangeEvent event) {
		for (AccessPolicyChangeListener l : dataSourceAccessPolicyChangeListeners) {
			try {
				l.onAccessPolicyChanged(event);
			} catch (Exception e) {
				System.err.format("error while notifying read only change to %s", String.valueOf(l));
			}
		}
	}

	public void setProxyLookup(final ProxyDataSourceLookup<DataSource, Object> proxyLookup) {
		this.proxyLookup = proxyLookup;
	}

	@Override
	public void onPostCommit(final CommitEvent event) {
		for (final CommitListener l : this.dataSourceCommitListeners) {
			l.onPostCommit(event);
		}
	}

	@Override
	public void onPreCommit(final CommitEvent event) throws CommitException {
		for (final CommitListener l : this.dataSourceCommitListeners) {
			l.onPreCommit(event);
		}
	}

	@Override
	public DataSourceDiff getDiff() {
		return dataSourceProxied.getDiff();
	}

	@Override
	public DataSource getCoreDataSource() {
		return dataSourceProxied.getCoreDataSource();
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
