package com.spaeth.appbase.adds.vaadin.component.mediator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.DataSourceMeta;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class VaadinDataSourceContainerAdapter implements Container, Container.PropertySetChangeNotifier, Container.ItemSetChangeNotifier,
		Container.Ordered, ValueChangeListener {

	private static final long serialVersionUID = 1L;

	public static interface VaadinDataSourceContainerAdapterListener {

		public static final VaadinDataSourceContainerAdapterListener DUMMY = new VaadinDataSourceContainerAdapterListener() {
			@Override
			public void propertySetChange(final CollectionDataSource dataSource) {
			}
		};

		void propertySetChange(CollectionDataSource dataSource);
	}

	private final CollectionDataSource dataSource;
	private final Map<DataSource, VaadinDataSourceItemAdapter> vaadinDataSourceItemAdapters = new HashMap<DataSource, VaadinDataSourceItemAdapter>();
	private VaadinDataSourceContainerAdapterListener listener = VaadinDataSourceContainerAdapterListener.DUMMY;

	public VaadinDataSourceContainerAdapter(final CollectionDataSource collectionDataSource) {
		this.dataSource = collectionDataSource;
	}

	public CollectionDataSource getDataSource() {
		return dataSource;
	}

	public void setListener(final VaadinDataSourceContainerAdapterListener listener) {
		this.listener = listener;
	}

	@Override
	public Item getItem(final Object itemId) {

		if (itemId == null || itemId == VaadinDataSourcePropertyAdapter.NULL_PROPERTY_VALUE) {
			return null;
		}
		
		DataSource element = dataSource.getElement(itemId);

		if (element == null) {
			return null;
		}

		if (!vaadinDataSourceItemAdapters.containsKey(element)) {
			vaadinDataSourceItemAdapters.put(element, new VaadinDataSourceItemAdapter(element));
		}

		return vaadinDataSourceItemAdapters.get(element);

	}

	@Override
	public Collection<?> getItemIds() {
		return Collections.unmodifiableCollection(getKeyList());
	}

	@Override
	public Property getContainerProperty(final Object itemId, final Object propertyId) {
		Item item = getItem(itemId);

		if (item == null) {
			return null;
		}

		return item.getItemProperty(propertyId);
	}

	@Override
	public Collection<?> getContainerPropertyIds() {
		Collection<Object> result = new ArrayList<Object>();
		if (dataSource != null) {
			addAllProperties("", result, dataSource.getMeta());
		}
		return result;
	}

	private void addAllProperties(final String prefix, final Collection<Object> result, final DataSourceMeta dataSourceMeta) {
		for (DataSourceMeta ds : dataSourceMeta.getChildrenMeta()) {
			result.add(prefix + ds.getName());
			addAllProperties(prefix + ds.getName() + ".", result, ds);
		}
	}

	@Override
	public Class<?> getType(final Object propertyId) {
		DataSource ds = dataSource.getDataSource(StringUtils.splitByWholeSeparatorPreserveAllTokens(String.valueOf(propertyId), "."));

		if (ds == null) {
			return null;
		}

		return ds.getType();
	}

	@Override
	public int size() {
		Integer result = dataSource.size();
		if (result == null) {
			return 0;
		}
		return result;
	}

	@Override
	public boolean containsId(final Object itemId) {
		return this.getKeyList().contains(itemId);
	}

	@Override
	public Item addItem(final Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object addItem() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItem(final Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addContainerProperty(final Object propertyId, final Class<?> type, final Object defaultValue)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeContainerProperty(final Object propertyId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void onChange(final DataSourceValueChangedEvent event) {
		if (event.getCause() != DataSourceEventCause.COMMIT) {
			fireContainerPropertySetChange();
			fireItemSetChange();
		} else if (event.getCause() != DataSourceEventCause.COMMIT) {
			vaadinDataSourceItemAdapters.clear();
		}
	}

	private List<Object> getKeyList() {
		Object c = dataSource.get();
		if (c == null) {
			return new ArrayList<Object>();
		} else {
			ArrayList<Object> interfacedNulls = new ArrayList<Object>((Collection<?>) c);
			Collections.replaceAll(interfacedNulls, null, VaadinDataSourcePropertyAdapter.NULL_PROPERTY_VALUE);
			return interfacedNulls;
		}
	}

	/**
	 * List of all Property set change event listeners.
	 */
	private Collection<Container.PropertySetChangeListener> propertySetChangeListeners = null;

	/**
	 * List of all container Item set change event listeners.
	 */
	private Collection<Container.ItemSetChangeListener> itemSetChangeListeners = null;

	/**
	 * An <code>event</code> object specifying the container whose Property set
	 * has changed.
	 * 
	 * This class does not provide information about which properties were
	 * concerned by the change, but subclasses can provide additional
	 * information about the changes.
	 */
	protected static class BasePropertySetChangeEvent extends EventObject implements Container.PropertySetChangeEvent, Serializable {

		private static final long serialVersionUID = 1L;

		protected BasePropertySetChangeEvent(final Container source) {
			super(source);
		}

		@Override
		public Container getContainer() {
			return (Container) getSource();
		}
	}

	/**
	 * An <code>event</code> object specifying the container whose Item set has
	 * changed.
	 * 
	 * This class does not provide information about the exact changes
	 * performed, but subclasses can add provide additional information about
	 * the changes.
	 */
	protected static class BaseItemSetChangeEvent extends EventObject implements Container.ItemSetChangeEvent, Serializable {

		private static final long serialVersionUID = 1L;

		protected BaseItemSetChangeEvent(final Container source) {
			super(source);
		}

		@Override
		public Container getContainer() {
			return (Container) getSource();
		}
	}

	// PropertySetChangeNotifier

	/**
	 * Implementation of the corresponding method in
	 * {@link PropertySetChangeNotifier}, override with the corresponding public
	 * method and implement the interface to use this.
	 * 
	 * @see PropertySetChangeNotifier#addListener(com.vaadin.data.Container.PropertySetChangeListener)
	 */
	@Override
	public void addListener(final Container.PropertySetChangeListener listener) {
		if (getPropertySetChangeListeners() == null) {
			setPropertySetChangeListeners(new LinkedList<Container.PropertySetChangeListener>());
		}
		getPropertySetChangeListeners().add(listener);
	}

	/**
	 * Implementation of the corresponding method in
	 * {@link PropertySetChangeNotifier}, override with the corresponding public
	 * method and implement the interface to use this.
	 * 
	 * @see PropertySetChangeNotifier#removeListener(com.vaadin.data.Container.
	 *      PropertySetChangeListener)
	 */
	@Override
	public void removeListener(final Container.PropertySetChangeListener listener) {
		if (getPropertySetChangeListeners() != null) {
			getPropertySetChangeListeners().remove(listener);
		}
	}

	// ItemSetChangeNotifier

	/**
	 * Implementation of the corresponding method in
	 * {@link ItemSetChangeNotifier}, override with the corresponding public
	 * method and implement the interface to use this.
	 * 
	 * @see ItemSetChangeNotifier#addListener(com.vaadin.data.Container.ItemSetChangeListener)
	 */
	@Override
	public void addListener(final Container.ItemSetChangeListener listener) {
		if (getItemSetChangeListeners() == null) {
			setItemSetChangeListeners(new LinkedList<Container.ItemSetChangeListener>());
		}
		getItemSetChangeListeners().add(listener);
	}

	/**
	 * Implementation of the corresponding method in
	 * {@link ItemSetChangeNotifier}, override with the corresponding public
	 * method and implement the interface to use this.
	 * 
	 * @see ItemSetChangeNotifier#removeListener(com.vaadin.data.Container.ItemSetChangeListener)
	 */
	@Override
	public void removeListener(final Container.ItemSetChangeListener listener) {
		if (getItemSetChangeListeners() != null) {
			getItemSetChangeListeners().remove(listener);
		}
	}

	/**
	 * Sends a simple Property set change event to all interested listeners.
	 */
	protected void fireContainerPropertySetChange() {
		fireContainerPropertySetChange(new BasePropertySetChangeEvent(this));
	}

	/**
	 * Sends a Property set change event to all interested listeners.
	 * 
	 * Use {@link #fireContainerPropertySetChange()} instead of this method
	 * unless additional information about the exact changes is available and
	 * should be included in the event.
	 * 
	 * @param event
	 *            the property change event to send, optionally with additional
	 *            information
	 */
	protected void fireContainerPropertySetChange(final Container.PropertySetChangeEvent event) {
		if (getPropertySetChangeListeners() != null) {
			final Object[] l = getPropertySetChangeListeners().toArray();
			for (final Object element : l) {
				((Container.PropertySetChangeListener) element).containerPropertySetChange(event);
			}
		}
		listener.propertySetChange(dataSource);
	}

	/**
	 * Sends a simple Item set change event to all interested listeners,
	 * indicating that anything in the contents may have changed (items added,
	 * removed etc.).
	 */
	protected void fireItemSetChange() {
		fireItemSetChange(new BaseItemSetChangeEvent(this));
	}

	/**
	 * Sends an Item set change event to all registered interested listeners.
	 * 
	 * @param event
	 *            the item set change event to send, optionally with additional
	 *            information
	 */
	protected void fireItemSetChange(final ItemSetChangeEvent event) {
		if (getItemSetChangeListeners() != null) {
			final Object[] l = getItemSetChangeListeners().toArray();
			for (final Object element : l) {
				((Container.ItemSetChangeListener) element).containerItemSetChange(event);
			}
		}
	}

	/**
	 * Sets the property set change listener collection. For internal use only.
	 * 
	 * @param propertySetChangeListeners
	 */
	protected void setPropertySetChangeListeners(final Collection<Container.PropertySetChangeListener> propertySetChangeListeners) {
		this.propertySetChangeListeners = propertySetChangeListeners;
	}

	/**
	 * Returns the property set change listener collection. For internal use
	 * only.
	 */
	protected Collection<Container.PropertySetChangeListener> getPropertySetChangeListeners() {
		return this.propertySetChangeListeners;
	}

	/**
	 * Sets the item set change listener collection. For internal use only.
	 * 
	 * @param itemSetChangeListeners
	 */
	protected void setItemSetChangeListeners(final Collection<Container.ItemSetChangeListener> itemSetChangeListeners) {
		this.itemSetChangeListeners = itemSetChangeListeners;
	}

	/**
	 * Returns the item set change listener collection. For internal use only.
	 */
	protected Collection<Container.ItemSetChangeListener> getItemSetChangeListeners() {
		return this.itemSetChangeListeners;
	}

	public Collection<?> getListeners(final Class<?> eventType) {
		if (Container.PropertySetChangeEvent.class.isAssignableFrom(eventType)) {
			if (this.propertySetChangeListeners == null) {
				return Collections.EMPTY_LIST;
			} else {
				return Collections.unmodifiableCollection(this.propertySetChangeListeners);
			}
		} else if (Container.ItemSetChangeEvent.class.isAssignableFrom(eventType)) {
			if (this.itemSetChangeListeners == null) {
				return Collections.EMPTY_LIST;
			} else {
				return Collections.unmodifiableCollection(this.itemSetChangeListeners);
			}
		}

		return Collections.EMPTY_LIST;
	}

	@Override
	public Object nextItemId(final Object itemId) {
		List<Object> keyList = getKeyList();
		int idx = keyList.indexOf(itemId);

		idx++;

		if (idx >= keyList.size()) {
			return null;
		}

		return keyList.get(idx);
	}

	@Override
	public Object prevItemId(final Object itemId) {
		List<Object> keyList = getKeyList();
		int idx = keyList.indexOf(itemId);

		idx++;

		if (idx < 0 || idx >= keyList.size()) {
			return null;
		}

		return keyList.get(idx);
	}

	@Override
	public Object firstItemId() {
		List<Object> keyList = getKeyList();
		if (keyList.isEmpty()) {
			return null;
		}
		return keyList.get(0);
	}

	@Override
	public Object lastItemId() {
		List<Object> keyList = getKeyList();
		if (keyList.isEmpty()) {
			return null;
		}
		return keyList.get(keyList.size() - 1);
	}

	@Override
	public boolean isFirstId(final Object itemId) {
		if (itemId == null) {
			return false;
		}
		return itemId.equals(firstItemId());
	}

	@Override
	public boolean isLastId(final Object itemId) {
		if (itemId == null) {
			return false;
		}
		return itemId.equals(lastItemId());
	}

	@Override
	public Object addItemAfter(final Object previousItemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Item addItemAfter(final Object previousItemId, final Object newItemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

}
