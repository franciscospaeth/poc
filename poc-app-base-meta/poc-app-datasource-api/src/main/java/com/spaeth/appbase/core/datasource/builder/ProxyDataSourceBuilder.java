package com.spaeth.appbase.core.datasource.builder;

import java.util.ArrayList;
import java.util.List;

import com.spaeth.appbase.core.datasource.AccessPolicyManaged;
import com.spaeth.appbase.core.datasource.AccessPolicyProvider;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.DataSourceHolder;
import com.spaeth.appbase.core.datasource.DataSourceMeta;
import com.spaeth.appbase.core.datasource.ProxyDataSource;
import com.spaeth.appbase.core.datasource.ProxyDataSourceCollectionLookup;
import com.spaeth.appbase.core.datasource.ProxyDataSourceLookup;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener;
import com.spaeth.appbase.core.datasource.event.ValidationListener;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.spaeth.appbase.core.datasource.validation.Validator;

public class ProxyDataSourceBuilder implements BaseDataSourceBuilder<ProxyDataSourceBuilder> {

	private ProxyDataSourceLookup<DataSource, Object> dataSourceProxyLookup;
	private String name = null;
	private String[] proxyingPath;

	private boolean navigationResultProxy = true;

	private final List<Validator> validators = new ArrayList<Validator>();
	private final List<ValueChangeListener> valueChangeListeners = new ArrayList<ValueChangeListener>();
	private final List<AccessPolicyChangeListener> accessPolicyChangeListeners = new ArrayList<AccessPolicyChangeListener>();
	private final List<ValidationListener> validationListeners = new ArrayList<ValidationListener>();
	private final List<AccessPolicyProvider> accessPolicyProviders = new ArrayList<AccessPolicyProvider>();
	private final List<AccessPolicyManaged> accessPolicyManageds = new ArrayList<AccessPolicyManaged>();

	public ProxyDataSourceBuilder(final String name, final String... proxyingPath) {
		super();
		this.name = name;
		this.proxyingPath = proxyingPath;
	}

	public ProxyDataSourceBuilder(final String name, final DataSourceHolder dataSourceHolder, final String... proxyingPath) {
		super();
		this.name = name;
		this.proxyingPath = proxyingPath;
		this.dataSourceProxyLookup = new ProxyDataSourceCollectionLookup(dataSourceHolder, proxyingPath);
	}

	@Override
	public DataSource build(final DataSource owner) {
		final ProxyDataSource result = new ProxyDataSource(name, owner, owner.getDataSource(proxyingPath).getMeta(), navigationResultProxy);
		result.setProxyLookup(dataSourceProxyLookup);

		for (DataSourceMeta dsm : owner.getDataSource(proxyingPath).getMeta().getChildrenMeta()) {
			result.addServingDirective(dsm.getName(), dsm.getServingDirective());
		}

		for (ValueChangeListener l : valueChangeListeners) {
			result.addDataSourceValueChangeListener(l);
		}

		for (Validator v : validators) {
			result.addValidator(v);
		}

		for (AccessPolicyChangeListener l : accessPolicyChangeListeners) {
			result.addDataSourceAccessPolicyChangeListener(l);
		}

		for (ValidationListener l : validationListeners) {
			result.addDataSourceValidationListener(l);
		}

		for (AccessPolicyProvider p : accessPolicyProviders) {
			result.addDataSourceAccessPolicyProvider(p);
		}

		for (AccessPolicyManaged m : accessPolicyManageds) {
			result.addDataSourceAccessPolicyManaged(m);
		}

		return result;
	}

	@Override
	public DataSource build() {
		return build(null);
	}

	public ProxyDataSourceBuilder setProxyLookup(final ProxyDataSourceLookup<DataSource, Object> dataSourceProxyLookup) {
		this.dataSourceProxyLookup = dataSourceProxyLookup;
		return this;
	}

	@Override
	public ProxyDataSourceBuilder setName(final String name) {
		this.name = name;
		return this;
	}

	public ProxyDataSourceBuilder setProxyPath(final String... path) {
		this.proxyingPath = path;
		return this;
	}

	public ProxyDataSourceBuilder addAccessPolicyProvider(final AccessPolicyProvider dataSourceAccessPolicyProvider) {
		this.accessPolicyProviders.add(dataSourceAccessPolicyProvider);
		return this;
	}

	public ProxyDataSourceBuilder addAccessPolicyManaged(final AccessPolicyManaged dataSourceAccessPolicyManaged) {
		this.accessPolicyManageds.add(dataSourceAccessPolicyManaged);
		return this;
	}

	public ProxyDataSourceBuilder addValidationListener(final ValidationListener dataSourceValidationListener) {
		this.validationListeners.add(dataSourceValidationListener);
		return this;
	}

	public ProxyDataSourceBuilder addAccessPolicyChangeListener(final AccessPolicyChangeListener dataSourceAccessPolicyChangeListener) {
		this.accessPolicyChangeListeners.add(dataSourceAccessPolicyChangeListener);
		return this;
	}

	public ProxyDataSourceBuilder addValueChangeListener(final ValueChangeListener dataSourceValueChangeListener) {
		this.valueChangeListeners.add(dataSourceValueChangeListener);
		return this;
	}

	public ProxyDataSourceBuilder addValidator(final Validator validator) {
		validators.add(validator);
		return this;
	}

	public ProxyDataSourceBuilder setNavigationResultProxy(final boolean navigationResultProxy) {
		this.navigationResultProxy = navigationResultProxy;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

}