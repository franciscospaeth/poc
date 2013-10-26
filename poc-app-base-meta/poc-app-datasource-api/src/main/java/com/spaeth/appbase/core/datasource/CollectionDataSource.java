package com.spaeth.appbase.core.datasource;

import java.util.Collection;

/**
 * Collection dataSource are responsible to deliver collection objects. Area
 * sample of collections: lists and sets. The implementation needs to hold the
 * original instance of the given collection in order to preserve consistency.
 * All operations needs to be staged in a way that applying it (
 * {@link #commit()}) will just flush changes to the original collection.
 * 
 * Normally each element on the collection in wrapped with an own
 * {@link DataSource}, meaning when you add some element to the collection (
 * {@link #addElement(Object)}) or get some element ({@link #getElement(Object)}
 * ), you will receive an {@link DataSource} that is automatically synchronized
 * with the collection, and will be part of the future commit activities.
 * 
 * @see {@link SetDataSource}
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 */
public interface CollectionDataSource extends DataSource {

	/**
	 * Get an element from the collection using as key the own object.
	 * 
	 * @param value
	 *            value needed in a represented way
	 * @return the interfaced version of the <code>value</code> for this
	 *         dataSource
	 */
	DataSource getElement(Object value);

	/**
	 * Adds an element to the collection, taking account the staged state.
	 * 
	 * @param value
	 *            value to be added to the collection
	 * @return the interfaced version of the <code>value</code> for this
	 *         dataSource
	 */
	DataSource addElement(Object value);

	/**
	 * Removes a given element from the collection.
	 * 
	 * @param value
	 *            value to be added to the collection
	 */
	void removeElement(Object value);

	/**
	 * Returns a list of all datasource representing elements of this collection
	 * datasource.
	 * 
	 * @return
	 */
	Collection<DataSource> getElements();

	/**
	 * Number of elements present in the collection.
	 * 
	 * @return number of elements in the collection, in case of the dataSource
	 *         is representing a <code>null</code> value, <code>null</code> is
	 *         returned
	 */
	Integer size();

	/**
	 * Removes all elements from the dataSource. Similar to
	 * {@link Collection#clear()}.
	 */
	void clear();

	/**
	 * Used in order to retrieve the status of a specific element held by the
	 * collection datasource.
	 * 
	 * @param value
	 * @return
	 * @see CollectionElementStatus
	 */
	CollectionElementStatus getElementStatus(Object value);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spaeth.appbase.core.datasource.DataSource#getDiff()
	 */
	@Override
	public CollectionDataSourceDiff getDiff();

}
