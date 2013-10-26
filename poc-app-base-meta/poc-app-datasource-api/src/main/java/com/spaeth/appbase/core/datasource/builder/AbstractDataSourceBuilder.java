package com.spaeth.appbase.core.datasource.builder;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.spaeth.appbase.core.datasource.AbstractDataSource;
import com.spaeth.appbase.core.datasource.AccessPolicyManaged;
import com.spaeth.appbase.core.datasource.AccessPolicyProvider;
import com.spaeth.appbase.core.datasource.CreateTemplate;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.ValueConverter;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener;
import com.spaeth.appbase.core.datasource.event.CommitListener;
import com.spaeth.appbase.core.datasource.event.ValidationListener;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.spaeth.appbase.core.datasource.validation.Validator;
import com.spaeth.appbase.core.security.model.AccessPolicy;

@SuppressWarnings("unchecked")
public abstract class AbstractDataSourceBuilder<T extends AbstractDataSourceBuilder<?>> implements DataSourceBuilder<T> {

	protected String name;
	protected Class<?> type = null;
	protected AccessPolicy accessPolicy = AccessPolicy.WRITE;
	protected boolean doNotResetAfterBuild = false;
	protected Object initialValue = null;

	protected List<BaseDataSourceBuilder<?>> dataSources = new ArrayList<BaseDataSourceBuilder<?>>();
	protected CreateTemplate<Object, DataSource> createTemplate;

	protected List<DecoratorBuilder> dataSourceDecorators = new ArrayList<DecoratorBuilder>();

	private final List<Validator> validators = new ArrayList<Validator>();
	private final List<ValueChangeListener> dataSourceValueChangeListeners = new ArrayList<ValueChangeListener>();
	private final List<CommitListener> dataSourceCommitListeners = new ArrayList<CommitListener>();
	private final List<AccessPolicyChangeListener> dataSourceAccessPolicyChangeListeners = new ArrayList<AccessPolicyChangeListener>();
	private final List<ValidationListener> dataSourceValidationListeners = new ArrayList<ValidationListener>();
	private final List<AccessPolicyProvider> dataSourceAccessPolicyProviders = new ArrayList<AccessPolicyProvider>();
	private final List<AccessPolicyManaged> dataSourceAccessPolicyManageds = new ArrayList<AccessPolicyManaged>();

	private ValueConverter converter = ValueConverter.DUMMY;

	protected Class<?> autoCalculateTypeIfNeeded(final DataSource owner) {
		Class<?> createType = type;
		if (createType == null && owner != null) {
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(owner.getType());
			for (PropertyDescriptor pd : pds) {
				if (pd.getName().equals(name)) {
					createType = pd.getPropertyType();
				}
			}
		} else if (createType == null && owner == null) {
			throw new IllegalStateException("auto-type calculation not possible with null owner on " + name);
		}
		return createType;
	}

	public AbstractDataSourceBuilder() {
	}

	public AbstractDataSourceBuilder(final String name) {
		super();
		this.name = name;
	}

	public AbstractDataSourceBuilder(final Class<?> type) {
		super();
		this.type = type;
	}

	public AbstractDataSourceBuilder(final String name, final Class<?> type) {
		super();
		this.name = name;
		this.type = type;
	}

	public T doNotResetAfterBuild() {
		this.doNotResetAfterBuild = true;
		return (T) this;
	}

	@Override
	public T addAccessPolicyProvider(final AccessPolicyProvider dataSourceAccessPolicyProvider) {
		this.dataSourceAccessPolicyProviders.add(dataSourceAccessPolicyProvider);
		return (T) this;
	}

	@Override
	public T setReadOnly() {
		setAccessPolicy(AccessPolicy.READ);
		return (T) this;
	}

	@Override
	public T setReadWrite() {
		setAccessPolicy(AccessPolicy.WRITE);
		return (T) this;
	}

	@Override
	public T setUnaccessible() {
		setAccessPolicy(AccessPolicy.NONE);
		return (T) this;
	}

	@Override
	public T addAccessPolicyManaged(final AccessPolicyManaged dataSourceAccessPolicyManaged) {
		this.dataSourceAccessPolicyManageds.add(dataSourceAccessPolicyManaged);
		return (T) this;
	}

	@Override
	public T addValidationListener(final ValidationListener dataSourceValidationListener) {
		this.dataSourceValidationListeners.add(dataSourceValidationListener);
		return (T) this;
	}

	@Override
	public T addAccessPolicyChangeListener(final AccessPolicyChangeListener dataSourceAccessPolicyChangeListener) {
		this.dataSourceAccessPolicyChangeListeners.add(dataSourceAccessPolicyChangeListener);
		return (T) this;
	}

	@Override
	public T addValueChangeListener(final ValueChangeListener dataSourceValueChangeListener) {
		this.dataSourceValueChangeListeners.add(dataSourceValueChangeListener);
		return (T) this;
	}

	@Override
	public T addCommitListener(final CommitListener dataSourceCommitListener) {
		this.dataSourceCommitListeners.add(dataSourceCommitListener);
		return (T) this;
	}

	@Override
	public T addDataSource(final BaseDataSourceBuilder<?> propertyBuilder) {
		this.dataSources.add(propertyBuilder);
		return (T) this;
	}

	@Override
	public T addValidator(final Validator validator) {
		validators.add(validator);
		return (T) this;
	}

	@Override
	public T setName(final String name) {
		this.name = name;
		return (T) this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public T setValueConverter(final ValueConverter converter) {
		this.converter = converter;
		return (T) this;
	}

	@Override
	public T setType(final Class<?> type) {
		this.type = type;
		return (T) this;
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public T setAccessPolicy(final AccessPolicy accessPolicy) {
		if (accessPolicy == null) {
			throw new NullPointerException();
		}
		this.accessPolicy = accessPolicy;
		return (T) this;
	}

	@Override
	public T addDecorator(final DecoratorBuilder dataSorceDecoratorBuilder) {
		dataSourceDecorators.add(dataSorceDecoratorBuilder);
		return (T) this;
	}

	@Override
	public DataSource build() {
		return build(null);
	}

	@Override
	public T setInitialValue(final Object initialValue) {
		this.initialValue = initialValue;
		return (T) this;
	}

	@Override
	public DataSource build(final DataSource owner) {

		preBuildCheck(owner);

		DataSource result = create(owner);

		result.setAccessPolicy(accessPolicy);

		if (createTemplate != null) {
			((AbstractDataSource<?>) result).setCreateTemplate(createTemplate);
		}

		configure(result);
		result = decorate(result);

		if (owner == null) {
			initialize(result);
		}

		return result;
	}

	private DataSource decorate(final DataSource dataSource) {
		DataSource result = dataSource;
		for (DecoratorBuilder dsdb : dataSourceDecorators) {
			result = dsdb.decorate(result);
		}
		return result;
	}

	protected void configure(final DataSource result) {
		result.setValueConverter(converter);

		for (BaseDataSourceBuilder<?> dsb : dataSources) {
			DataSource built = dsb.build(result);
			((AbstractDataSource<?>) result).addDataSource(built);
			result.getMeta().getChildrenMeta().add(built.getMeta());
		}

		for (AccessPolicyProvider dsapp : dataSourceAccessPolicyProviders) {
			result.addDataSourceAccessPolicyProvider(dsapp);
		}

		for (AccessPolicyManaged dsapm : dataSourceAccessPolicyManageds) {
			result.addDataSourceAccessPolicyManaged(dsapm);
		}

		for (ValueChangeListener dsvcl : dataSourceValueChangeListeners) {
			result.addDataSourceValueChangeListener(dsvcl);
		}

		for (CommitListener dsvcl : dataSourceCommitListeners) {
			result.addDataSourceCommitListener(dsvcl);
		}

		for (ValidationListener dsvl : dataSourceValidationListeners) {
			result.addDataSourceValidationListener(dsvl);
		}

		for (AccessPolicyChangeListener dsapcl : dataSourceAccessPolicyChangeListeners) {
			result.addDataSourceAccessPolicyChangeListener(dsapcl);
		}

		for (Validator validator : validators) {
			result.addValidator(validator);
		}
	}

	protected void initialize(final DataSource result) {
		if (!doNotResetAfterBuild) {
			result.reset();
		}
	}

	protected void preBuildCheck(final DataSource owner) {
	}

	protected abstract DataSource create(DataSource owner);

}
