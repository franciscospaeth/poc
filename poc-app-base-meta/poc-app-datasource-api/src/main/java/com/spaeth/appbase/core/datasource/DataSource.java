package com.spaeth.appbase.core.datasource;

import java.util.Collection;

import com.spaeth.appbase.core.datasource.builder.DataSourceBuilder;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener;
import com.spaeth.appbase.core.datasource.event.CommitListener;
import com.spaeth.appbase.core.datasource.event.DataSourceEvent;
import com.spaeth.appbase.core.datasource.event.ValidationListener;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.spaeth.appbase.core.datasource.validation.ConstraintViolation;
import com.spaeth.appbase.core.datasource.validation.Validator;
import com.spaeth.appbase.core.security.model.AccessPolicy;

/**
 * Represents a source for a data. The composition of a dataSource can be seen
 * as a tree, that has a root (represented by an instance with no owner) and
 * some children. Each children has to be an instance of {@link DataSource}, and
 * it is not mandatory that a dataSource needs to hold a specific implementation
 * of {@link DataSource} as children.
 * 
 * The implementation of dataSources is based on the principle that every
 * operation that needs to be done between dataSources to communicate state
 * changes shall be done over process method. This allows the programmer mix
 * different implementation of {@link DataSource}.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 */
public interface DataSource {

	/**
	 * Retrieves the value currently configured for the dataSource. This value
	 * is not yet applied to the original source.
	 * 
	 * @return value configured to the current property
	 * @see {@link DataSource#commit()}
	 */
	Object getModel();

	Object get();

	/**
	 * Configures a specific value for the dataSource. This value is not
	 * directly applied to original source.
	 * 
	 * @param value
	 *            to be applied to this dataSource
	 * @see {@link DataSource#commit()} {@link DataSource#reset()}
	 */
	void setModel(Object value);

	void set(Object value);

	/**
	 * Creates a new instance of the current property and set it. The logic
	 * behind this method is generally related to an implementation of
	 * {@link CreateTemplate}.
	 * 
	 * @see {@link CreateTemplate}
	 */
	void createNew();

	/**
	 * Applies values of this dataSource's dependents and it self to its value,
	 * and configures the initialValue as the current one, meaning
	 * {@link #reset()} will rollBack changes to this version if changes are
	 * performed and {@link #reset()} invoked.
	 */
	void commit();

	/**
	 * Reset the current dataSource to its initial state.
	 */
	void reset();

	/**
	 * Reset the initial state of the dataSource and set the value to it.
	 */
	void reset(Object value);

	/**
	 * Operation used in order to retrieve the owner dataSource, commonly the
	 * one that depends on this dataSource instance.
	 * 
	 * @return dataSource that owns this one in his composition
	 */
	DataSource getOwner();

	/**
	 * Basically a delegation method in order to get dataSource name.
	 * 
	 * Delegation for: <code>dataSource.getMeta().getName()</code>
	 * 
	 * @return dataSources name
	 */
	String getName();

	/**
	 * Holds the type of this dataSource.
	 * 
	 * @return type holded by this dataSource
	 */
	Class<?> getType();

	/**
	 * Used in order to see if this dataSource has commitments pending.
	 * 
	 * The check of its modified status is done normally investigating itself
	 * and its dependents. This value is changed with {@link #commit()} method
	 * invocation.
	 * 
	 * @return if this dataSource has some staged data
	 */
	boolean isModified();

	/**
	 * Returns the dataSource names that this depends on. The same as:
	 * 
	 * <pre>
	 * Collection&lt;String&gt; dsNames = new Set&lt;String&gt;();
	 * List&lt;DataSourceMeta&gt; children = getMeta().getChildrenMeta();
	 * for (DataSourceMeta dsm : children) {
	 * 	dsNames.add(dsm.getName());
	 * }
	 * </pre>
	 * 
	 * @return collection of children dataSource's name
	 */
	Collection<String> getDataSourceNames();

	/**
	 * Get a child dataSource. This method could be used to acquire a nested
	 * child.<br>
	 * <br>
	 * Example: <br>
	 * <code>getDataSource("company")</code>: returns the child named
	 * <code>company</code> of this dataSource;
	 * <code>getDataSource("company","name")</code>: returns the child named
	 * <code>name</code> from child <code>company</code> of this dataSource;
	 * 
	 * @param name
	 * @return
	 */
	DataSource getDataSource(String... name);

	/**
	 * Get all dataSources that this one depends on (children dataSources).
	 * 
	 * @return collection of children dataSources
	 */
	Collection<DataSource> getDataSources();

	/**
	 * Validates this dataSource based on validators configured. The validation
	 * is applied over staged value.
	 * 
	 * @return collection of {@link ConstraintViolation} for the current
	 *         dataSource
	 */
	Collection<ConstraintViolation> validate();

	/**
	 * Configures the direct accessPolicy for this dataSource. The result
	 * {@link AccessPolicy} depends on this value added to
	 * {@link AccessPolicyProvider}.
	 * 
	 * @param accessPolicy
	 *            the one applied for this dataSource directly
	 */
	void setAccessPolicy(AccessPolicy accessPolicy);

	/**
	 * Returns the {@link AccessPolicy} configured to this dataSource using
	 * {@link #setAccessPolicy(AccessPolicy)}.
	 * 
	 * @return {@link AccessPolicy} specified for this dataSource
	 */
	AccessPolicy getAccessPolicy();

	/**
	 * Entry point for visitor to the dataSource.
	 * 
	 * @param dataSourceVisitor
	 *            visitor for this dataSource
	 * 
	 * @see {@link DataSourceVisitor}
	 */
	void accept(DataSourceVisitor dataSourceVisitor);

	/**
	 * Adds a listener for value change events fired by this dataSource.
	 * 
	 * @param dataSourceValueChangeListener
	 *            listener to be added
	 * 
	 * @see {@link ValueChangeListener}
	 */
	void addDataSourceValueChangeListener(ValueChangeListener dataSourceValueChangeListener);

	/**
	 * Removes a listener for value change events fired by this dataSource.
	 * 
	 * @param dataSourceValueChangeListener
	 *            listener to be removed
	 * 
	 * @see {@link ValueChangeListener}
	 */
	void removeDataSourceValueChangeListener(ValueChangeListener dataSourceValueChangeListener);

	/**
	 * Adds a listener for access policy change events fired by this dataSource.
	 * 
	 * @param dataSourceAccessPolicyChangeListener
	 *            listener to be added
	 * 
	 * @see {@link AccessPolicyChangeListener}
	 */
	void addDataSourceAccessPolicyChangeListener(AccessPolicyChangeListener dataSourceAccessPolicyChangeListener);

	/**
	 * Removes a listener for access policy change events fired by this
	 * dataSource.
	 * 
	 * @param dataSourceAccessPolicyChangeListener
	 *            listener to be removed
	 * 
	 * @see {@link AccessPolicyChangeListener}
	 */
	void removeDataSourceAccessPolicyChangeListener(AccessPolicyChangeListener dataSourceAccessPolicyChangeListener);

	/**
	 * Adds a access policy provider for this dataSource, which will be computed
	 * on access policy updates.
	 * 
	 * @param dataSourceAccessPolicyProvider
	 *            provider to be added
	 * 
	 * @see {@link AccessPolicyProvider}
	 */
	void addDataSourceAccessPolicyProvider(AccessPolicyProvider dataSourceAccessPolicyProvider);

	/**
	 * Removes a access policy provider for this dataSource.
	 * 
	 * @param dataSourceAccessPolicyProvider
	 *            provider to be removed
	 * 
	 * @see {@link AccessPolicyProvider}
	 */
	void removeDataSourceAccessPolicyProvider(AccessPolicyProvider dataSourceAccessPolicyProvider);

	/**
	 * Listener to be added in order to be trigger when {@link AccessPolicy}
	 * from this dataSource changes directly (by this dataSource or one of its
	 * parent) or indirectly (by {@link AccessPolicyProvider}).
	 * 
	 * @param dataSourceAccessPolicyManaged
	 *            managed to be added
	 */
	void addDataSourceAccessPolicyManaged(AccessPolicyManaged dataSourceAccessPolicyManaged);

	/**
	 * Listener to be removed from this dataSource.
	 * 
	 * @param dataSourceAccessPolicyManaged
	 *            managed to be removed
	 */
	void removeDataSourceAccessPolicyManaged(AccessPolicyManaged dataSourceAccessPolicyManaged);

	/**
	 * Adds a listener for validation events fired by this dataSource.
	 * 
	 * @param dataSourceValidationListener
	 *            listener to be added
	 * 
	 * @see {@link ValidationListener}
	 */
	void addDataSourceValidationListener(ValidationListener dataSourceValidationListener);

	/**
	 * Removes a listener for validation events fired by this dataSource.
	 * 
	 * @param dataSourceValidationListener
	 *            listener to be removed
	 * 
	 * @see {@link ValidationListener}
	 */
	void removeDataSourceValidationListener(ValidationListener dataSourceValidationListener);

	/**
	 * Adds a validator to this dataSource.
	 * 
	 * @param validator
	 *            validator to be inserted
	 * 
	 * @see {@link Validator}
	 */
	void addValidator(Validator validator);

	/**
	 * Removes a validator from this dataSource.
	 * 
	 * @param validator
	 *            validator to be removed
	 * 
	 * @see {@link Validator}
	 */
	void removeValidator(Validator validator);

	/**
	 * Processes an event generated by other dataSource and aims to hold a
	 * strict interface in order to maintain the compatibility of different
	 * implementations of dataSources. In a normal application this method
	 * shouldn't be used.
	 * 
	 * @param event
	 *            event to be processed by this dataSource
	 */
	void process(DataSourceEvent event);

	/**
	 * Generates a builder for this dataSource.
	 * 
	 * @return a builder in order to create a similar dataSource
	 */
	DataSourceBuilder<?> getBuilder();

	/**
	 * Gives out meta-data for this dataSource
	 * 
	 * @return {@link DataSourceMeta} of this dataSource
	 */
	DataSourceMeta getMeta();

	/**
	 * Adds a listener for commit events fired by this dataSource.
	 * 
	 * @param dataSourceCommitListener
	 *            listener to be added
	 * 
	 * @see {@link CommitListener}
	 */
	void addDataSourceCommitListener(CommitListener dataSourceCommitListener);

	/**
	 * Removes a listener for commit events fired by this dataSource.
	 * 
	 * @param dataSourceCommitListener
	 *            listener to be removed
	 * 
	 * @see {@link CommitListener}
	 */
	void removeDataSourceCommitListener(CommitListener dataSourceCommitListener);

	/**
	 * Provides an overview of changes performed on the dataSource.
	 * 
	 * @return difference object containing all modified data
	 */
	DataSourceDiff getDiff();

	/**
	 * Returns the datasource core. For decorated dataSources the decorated will
	 * be returned, for non-decorated it will return itself.
	 * 
	 * TODO review this concept
	 * 
	 * @return
	 */
	DataSource getCoreDataSource();

	/**
	 * Retrieves the converter associated to this datasource.
	 * 
	 * @return
	 */
	ValueConverter getValueConverter();

	/**
	 * Configures the given converter to this datasource.
	 * 
	 * @param valueConverter
	 */
	void setValueConverter(ValueConverter valueConverter);

}
