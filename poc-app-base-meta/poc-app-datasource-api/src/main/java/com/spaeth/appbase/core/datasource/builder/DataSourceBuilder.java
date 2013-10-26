package com.spaeth.appbase.core.datasource.builder;

import com.spaeth.appbase.core.datasource.AccessPolicyManaged;
import com.spaeth.appbase.core.datasource.AccessPolicyProvider;
import com.spaeth.appbase.core.datasource.ValueConverter;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener;
import com.spaeth.appbase.core.datasource.event.CommitListener;
import com.spaeth.appbase.core.datasource.event.ValidationListener;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.spaeth.appbase.core.datasource.validation.Validator;
import com.spaeth.appbase.core.security.model.AccessPolicy;

public interface DataSourceBuilder<T extends DataSourceBuilder<?>> extends BaseDataSourceBuilder<T> {

	T setType(Class<?> type);

	Class<?> getType();

	T setInitialValue(Object initialValue);

	T setAccessPolicy(AccessPolicy accessPolicy);

	T setReadOnly();

	T setReadWrite();

	T setUnaccessible();

	T setValueConverter(ValueConverter converter);

	T addDataSource(BaseDataSourceBuilder<?> propertyBuilder);

	T addValidator(Validator validator);

	T addValueChangeListener(ValueChangeListener valueChangeListener);

	T addAccessPolicyChangeListener(AccessPolicyChangeListener accessPolicyChangeListener);

	T addAccessPolicyProvider(AccessPolicyProvider dataSourceAccessPolicyProvider);

	T addAccessPolicyManaged(AccessPolicyManaged dataSourceAccessPolicyManaged);

	T addValidationListener(ValidationListener dataSourceValidationListener);

	T addCommitListener(CommitListener dataSourceCommitListener);

	T addDecorator(DecoratorBuilder dataSorceDecoratorBuilder);

}
