package com.spaeth.appbase.adds.vaadin.component.mediator;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.ServingDirective;
import com.spaeth.appbase.core.datasource.event.AccessPolicyChangeListener;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.spaeth.appbase.core.security.model.AccessPolicy;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

public class VaadinDataSourcePropertyAdapter implements Property, ValueChangeListener, AccessPolicyChangeListener,
		Property.ValueChangeNotifier, Property.ReadOnlyStatusChangeNotifier {

	private static final long serialVersionUID = 1L;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected DataSource dataSource = null;
	
	public static final Object NULL_PROPERTY_VALUE = new Object();

	private LinkedList<ReadOnlyStatusChangeListener> readOnlyStatusChangeListeners = null;
	private LinkedList<ValueChangeListener> valueChangeListeners = null;
	private Object nullValue = null;
	private Class<?> holdedType = Object.class;

	public VaadinDataSourcePropertyAdapter() {
	}

	public VaadinDataSourcePropertyAdapter(final Object nullValue) {
		this.nullValue = nullValue;
	}

	protected void initialize() {
	}

	public void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setHoldedType(final Class<?> holdedType) {
		this.holdedType = holdedType;
	}

	@Override
	public Object getValue() {
		if (this.dataSource == null || this.dataSource.getAccessPolicy() == AccessPolicy.NONE) {
			return nullValue;
		}
		Object result = this.dataSource.get();
		if (result == null) {
			result = nullValue;
		}
		return result;
	}

	@Override
	public void setValue(final Object newValue) {

		if (dataSource == null) {
			return;
		}

		// determines the value to be set
		Object value = newValue;

		String name = this.dataSource.getName();
		AccessPolicy accessPolicy = this.dataSource.getAccessPolicy();
		Class<?> type = this.dataSource.getType();

		// execute set
		if (!(accessPolicy == AccessPolicy.WRITE)) {
			throw new Property.ReadOnlyException("property " + name + " has its security state configured as " + accessPolicy);
		}

		if (value instanceof Collection && dataSource.getMeta().getServingDirective() == ServingDirective.COLLECTION) {
			this.dataSource.setModel(value);
			return;
		}

		if (value == null || type.isAssignableFrom(value.getClass())) {
			this.dataSource.set(value);
			return;
		}
		
		if (value == NULL_PROPERTY_VALUE) {
			this.dataSource.set(null);
			return;
		}

		if (type == String.class) {
			this.dataSource.setModel(String.valueOf(value));
			return;
		}

		if (StringUtils.isBlank(String.valueOf(value))) {
			this.dataSource.setModel(nullValue);
			return;
		}

		try {
			if (type == Integer.class) {
				this.dataSource.setModel(Integer.valueOf(String.valueOf(value)));
				return;
			}

			if (type == Float.class) {
				this.dataSource.setModel(Float.valueOf(String.valueOf(value)));
				return;
			}

			if (type == Double.class) {
				this.dataSource.setModel(Double.valueOf(String.valueOf(value)));
				return;
			}

			if (type == Long.class) {
				this.dataSource.setModel(Long.valueOf(String.valueOf(value)));
				return;
			}

			if (type == BigDecimal.class) {
				this.dataSource.setModel(new BigDecimal(String.valueOf(value)));
				return;
			}
		} catch (NumberFormatException nfe) {
			logger.warn("report error to interface: " + nfe.getMessage(), nfe);
			return;
		}

		throw new Property.ConversionException("new value for property " + name + " could not be represented as " + type);
	}

	@Override
	public Class<?> getType() {
		if (dataSource == null) {
			return holdedType;
		}
		return this.dataSource.getType();
	}

	@Override
	public boolean isReadOnly() {
		if (dataSource == null) {
			return false;
		}
		return this.dataSource.getAccessPolicy() != AccessPolicy.WRITE;
	}

	@Override
	public String toString() {
		return ObjectUtils.defaultIfNull(getValue(), "").toString();
	}

	@Override
	public void addListener(final Property.ReadOnlyStatusChangeListener listener) {
		if (this.readOnlyStatusChangeListeners == null) {
			this.readOnlyStatusChangeListeners = new LinkedList<ReadOnlyStatusChangeListener>();
		}
		this.readOnlyStatusChangeListeners.add(listener);
	}

	@Override
	public void removeListener(final Property.ReadOnlyStatusChangeListener listener) {
		if (this.readOnlyStatusChangeListeners != null) {
			this.readOnlyStatusChangeListeners.remove(listener);
		}
	}

	@Override
	public void addListener(final ValueChangeListener listener) {
		if (this.valueChangeListeners == null) {
			this.valueChangeListeners = new LinkedList<ValueChangeListener>();
		}
		this.valueChangeListeners.add(listener);
	}

	@Override
	public void removeListener(final ValueChangeListener listener) {
		if (this.valueChangeListeners != null) {
			this.valueChangeListeners.remove(listener);
		}

	}

	protected void fireSecurityAccessibilityStatusChange() {
		if (this.readOnlyStatusChangeListeners != null) {
			final Object[] l = this.readOnlyStatusChangeListeners.toArray();
			final Property.ReadOnlyStatusChangeEvent event = new ReadOnlyStatusChangeEvent(this);
			for (final Object element : l) {
				((Property.ReadOnlyStatusChangeListener) element).readOnlyStatusChange(event);
			}
		}
	}

	protected void fireValueChange() {
		if (this.valueChangeListeners != null) {
			final Object[] l = this.valueChangeListeners.toArray();
			final Property.ValueChangeEvent event = new ValueChangeEvent(this);
			for (final Object element : l) {
				this.logger.info("updating {} with {}", element, this.dataSource);
				if (element instanceof Table) {
					// TODO REMOVE CODE FROM THIS STEP
					if (!((Table) element).getItemIds().contains(dataSource.get())) {
						((Table) element).setCurrentPageFirstItemIndex(0);
					}
				}
				((Property.ValueChangeListener) element).valueChange(event);
			}
		}
	}

	private class ValueChangeEvent extends java.util.EventObject implements Property.ValueChangeEvent {

		private static final long serialVersionUID = 1L;

		protected ValueChangeEvent(final Property source) {
			super(source);
		}

		@Override
		public Property getProperty() {
			return (Property) getSource();
		}

	}

	protected class ReadOnlyStatusChangeEvent extends java.util.EventObject implements Property.ReadOnlyStatusChangeEvent {

		private static final long serialVersionUID = 1L;

		protected ReadOnlyStatusChangeEvent(final Property source) {
			super(source);
		}

		@Override
		public Property getProperty() {
			return (Property) getSource();
		}

	}

	@Override
	public void onAccessPolicyChanged(final AccessPolicyChangeEvent event) {
		fireSecurityAccessibilityStatusChange();
	}

	@Override
	public void onChange(final DataSourceValueChangedEvent event) {
		fireValueChange();
	}

	@Override
	public void setReadOnly(final boolean newStatus) {
		if (dataSource == null) {
			return;
		}

		AccessPolicy newAccessPolicy = newStatus ? AccessPolicy.READ : AccessPolicy.WRITE;
		this.dataSource.setAccessPolicy(newAccessPolicy);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

}