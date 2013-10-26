package com.spaeth.appbase.adds.datasource.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;

import com.spaeth.appbase.adds.datasource.bean.builder.BeanCollectionDataSourceBuilder;
import com.spaeth.appbase.adds.datasource.bean.builder.BeanDataSourceBuilder;
import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.CollectionDataSourceDiff;
import com.spaeth.appbase.core.datasource.CollectionElementStatus;
import com.spaeth.appbase.core.datasource.CreateTemplate;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.DataSourceMeta;
import com.spaeth.appbase.core.datasource.ServingDirective;
import com.spaeth.appbase.core.datasource.SimpleCreateTemplate;
import com.spaeth.appbase.core.datasource.builder.DataSourceBuilder;
import com.spaeth.appbase.core.datasource.builder.ValueDataSourceBuilder;
import com.spaeth.appbase.core.datasource.event.CommitListener.CommitEvent;
import com.spaeth.appbase.core.datasource.event.CommitListener.CommitException;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener.DataSourceEventCause;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener.DataSourceValueChangedEvent;
import com.spaeth.appbase.core.datasource.validation.ConstraintViolation;

public abstract class BeanCollectionDataSource<T extends BeanCollectionDataSourceBuilder<?>> extends AbstractBeanDataSource<T> implements
		CollectionDataSource {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected CreateTemplate<DataSourceBuilder<?>, Object> elementCreateTemplate = new SimpleCreateTemplate(BeanDataSourceBuilder.class);

	protected Map<Object, DataSource> dataSourceMap;

	public BeanCollectionDataSource(final String name, final Class<?> type) {
		super(new DataSourceMeta(name, type, ServingDirective.COLLECTION));
		// TODO: holding map should be possible to be defined by the user
		dataSourceMap = new LinkedHashMap<Object, DataSource>();
	}

	public BeanCollectionDataSource(final String name, final Class<?> type, final Object initialValue) {
		super(new DataSourceMeta(name, type, ServingDirective.COLLECTION), initialValue);
		// TODO: holding map should be possible to be definied by the user
		dataSourceMap = new LinkedHashMap<Object, DataSource>();
	}

	public BeanCollectionDataSource(final DataSource owner, final String name, final Class<?> type) {
		super(owner, new DataSourceMeta(name, type, ServingDirective.COLLECTION), null);
		// TODO: holding map should be possible to be definied by the user
		dataSourceMap = new LinkedHashMap<Object, DataSource>();
	}

	public BeanCollectionDataSource(final DataSource owner, final String name, final Class<?> type, final Object initialValue) {
		super(owner, new DataSourceMeta(name, type, ServingDirective.COLLECTION), initialValue);
		// TODO: holding map should be possible to be definied by the user
		dataSourceMap = new LinkedHashMap<Object, DataSource>();
	}

	public BeanCollectionDataSource(final DataSource owner, final DataSourceMeta meta, final Object initialValue) {
		super(owner, meta, initialValue);
	}

	public BeanCollectionDataSource(final DataSourceMeta meta, final Object initialValue) {
		super(meta, initialValue);
	}

	@Override
	public DataSource getElement(final Object value) {
		// check access
		checkReadPermission();

		if (value == null) {
			return null;
		}
		return dataSourceMap.get(value);
	}

	@Override
	public Collection<DataSource> getElements() {
		checkReadPermission();

		return dataSourceMap.values();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void executeSet(final DataSource eventGenerator, final Object value, final DataSourceEventCause cause) {
		Set<Object> keySet = dataSourceMap.keySet();
		if (value != null) {
			Set<Object> newSet = (Set<Object>) value;
			Collection<Object> remove = CollectionUtils.subtract(keySet, newSet);
			for (Object k : remove) {
				removeElementFromDataSourceMap(k);
			}
			for (Object o : CollectionUtils.intersection(newSet, keySet)) {
				dataSourceMap.get(o).reset(o);
			}
			createDataSourcesForCollection(CollectionUtils.subtract(newSet, keySet));
			resortMapBasedOn(newSet);
		} else {
			Object[] keySetArray = keySet.toArray();
			for (Object o : keySetArray) {
				removeElementFromDataSourceMap(o);
			}
		}

		super.executeSet(eventGenerator, value, cause);
	}

	private void resortMapBasedOn(final Set<Object> newSet) {
		Map<Object, DataSource> m = new HashMap<Object, DataSource>(dataSourceMap);
		Set<Object> tempSet = new LinkedHashSet<Object>(newSet);
		dataSourceMap.clear();
		for (Object o : tempSet) {
			dataSourceMap.put(o, m.get(o));
		}
	}

	protected void removeElementFromDataSourceMap(final Object k) {
		dataSourceMap.remove(k);
	}

	private void createDataSourcesForCollection(final Collection<Object> value) {
		for (Object o : value) {
			//if (o == null) {
			//	throw new NullPointerException();
			//}
			if (getType().isInstance(o)) {
				dataSourceMap.put(o, createElementDataSourceBuilder(o));
			}
		}
	}

	protected DataSource createElementDataSourceBuilder(final Object value) {
		DataSourceBuilder<?> builder = elementCreateTemplate.create(value);
		builder.setInitialValue(value);
		configureChildBuilder(builder, value);
		DataSource dataSource = builder.build(this);
		dataSource.reset();
		return dataSource;
	}

	public void setElementCreateTemplate(final CreateTemplate<DataSourceBuilder<?>, Object> dataSourceCreateTemplate) {
		this.elementCreateTemplate = dataSourceCreateTemplate;
	}

	@Override
	public Integer size() {
		return dataSourceMap.size();
	}

	@Override
	public void clear() {
		for (Object o : dataSourceMap.keySet()) {
			removeElementFromDataSourceMap(o);
		}
	}

	@Override
	public void commit() {
		if (isModified() && validate().isEmpty()) {
			CollectionDataSourceDiff diff = getDiff();

			CommitEvent event = new CommitEvent(this, diff);
			try {
				fireOnPreCommitListener(event);
			} catch (CommitException e) {
				return;
			}

			Collection<Object> applyTo = getCollection();
			try {
				fireOnPreCommitListener(event);
			} catch (CommitException e) {
				return;
			}

			for (Object o : diff.getAllElements()) {
				getElement(o).commit();
			}

			for (Object o : diff.getAddedElements()) {
				applyTo.add(o);
			}

			for (Object o : diff.getRemovedElements()) {
				applyTo.remove(o);
			}

			fireOnPostCommitListener(event);

			LinkedHashSet<Object> newValue = new LinkedHashSet<Object>();
			for (DataSource ds : dataSourceMap.values()) {
				newValue.add(ds.getModel());
			}

			executeSet(this, newValue, DataSourceEventCause.COMMIT);
		}
	}

	@Override
	public boolean isModified() {
		return super.isModified() || getDiff().getDiffCount() > 0;
	}

	@SuppressWarnings("unchecked")
	protected Collection<Object> getCollection() {
		Object value = super.getInternal();
		if (value == null) {
			return new HashSet<Object>();
		}
		return (Collection<Object>) value;
	}

	protected void configureChildBuilder(final DataSourceBuilder<?> builder, final Object value) {
		builder.setName(getName());
		builder.setType(getType());

		for (DataSource ds : getDataSources()) {
			DataSourceBuilder<?> propertyBuilder = ds.getBuilder();
			if (propertyBuilder instanceof ValueDataSourceBuilder) {
				try {
					if (value != null) {
						Object propertyValue = PropertyUtils.getProperty(value, ds.getName());
						propertyBuilder.setInitialValue(propertyValue);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			builder.addDataSource(propertyBuilder);
		}
	}

	@Override
	public final void setModel(final Object value) {

		if (value == null) {
			super.setModel(null);
			return;
		}

		if (!(value instanceof Collection)) {
			throw new IllegalStateException("value not compatible with expected collection");
		}

		super.setModel(processSetCollection(value));
	}

	@Override
	protected void setInternal(final DataSource eventGenerator, final Object value) {
		executeSet(eventGenerator, value, DataSourceEventCause.SET);
	}

	protected abstract Collection<?> processSetCollection(Object value);

	@Override
	public T getBuilder() {
		T builder = super.getBuilder();
		builder.setElementCreateTemplate(elementCreateTemplate);
		return builder;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void propagateChangedDown(final DataSourceValueChangedEvent dsce) {
		for (DataSource ds : dataSourceMap.values()) {

			// spring items that were already on old collection this due the
			// datasources that were already changed, needs to hold the edited
			// information
			// c != null - when not the initial set is
			// ((Collection)(dsce.getOldValue())).contains(ds.get()) - when this
			// was already in the collection
			// !isModified - when this comes from a reset
			Collection c = (Collection) dsce.getOldValue();
			if ((c != null && (c.contains(ds.getModel()))) && isModified() && dsce.getCause() != DataSourceEventCause.RESET) {
				continue;
			}

			Object newValue = null;
			Object oldValue = null;

			Object holdedValue = ds.getModel();

			Object oldNotifiedObject = dsce.getOldValue();
			Object newNotifiedObject = dsce.getNewValue();

			if (oldNotifiedObject != null && oldNotifiedObject instanceof Collection) {
				if (((Collection) oldNotifiedObject).contains(holdedValue)) {
					oldValue = holdedValue;
				}
			}

			if (newNotifiedObject != null && newNotifiedObject instanceof Collection) {
				if (((Collection) newNotifiedObject).contains(holdedValue)) {
					newValue = holdedValue;
				}
			}

			ds.process(new DataSourceValueChangedEvent(dsce.getCause(), dsce.getSource(), oldValue, newValue));
		}
	}

	@Override
	protected Collection<ConstraintViolation> executeValidators() {
		Collection<ConstraintViolation> result = super.executeValidators();
		for (DataSource ds : dataSourceMap.values()) {
			result.addAll(ds.validate());
		}
		return result;
	}

	@Override
	public CollectionDataSourceDiff getDiff() {
		Collection<Object> original = getCollection();
		Collection<Object> edited = dataSourceMap.keySet();

		Collection<?> added = CollectionUtils.subtract(edited, original);
		Collection<?> removed = CollectionUtils.subtract(original, edited);
		Collection<Object> modified = new HashSet<Object>();

		for (Object o : edited) {
			if (dataSourceMap.get(o).isModified()) {
				modified.add(dataSourceMap.get(o).getModel());
			}
		}

		return new CollectionDataSourceDiff(added, removed, modified);
	}

	@Override
	public CollectionElementStatus getElementStatus(final Object value) {

		Collection<Object> original = getCollection();
		Collection<Object> edited = dataSourceMap.keySet();

		if (CollectionUtils.subtract(edited, original).contains(value)) {
			return CollectionElementStatus.NEW;
		}

		if (CollectionUtils.subtract(original, edited).contains(value)) {
			return CollectionElementStatus.DELETED;
		}

		DataSource elementDataSource = dataSourceMap.get(value);
		if (elementDataSource != null) {
			if (elementDataSource.isModified()) {
				return CollectionElementStatus.MODIFIED;
			} else {
				return CollectionElementStatus.UNMODIFIED;
			}
		}

		return CollectionElementStatus.NOT_FOUND;

	}

}
