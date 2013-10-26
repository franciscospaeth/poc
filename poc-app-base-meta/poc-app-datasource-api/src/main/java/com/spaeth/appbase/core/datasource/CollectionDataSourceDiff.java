package com.spaeth.appbase.core.datasource;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Represents all modifications applied to a collection.
 * 
 * @author spaeth
 * 
 */
public class CollectionDataSourceDiff implements DataSourceDiff {

	private final Collection<?> addedElements;
	private final Collection<?> removedElements;
	private final Collection<?> modifiedElements;

	public static final CollectionDataSourceDiff NO_DIFF = new CollectionDataSourceDiff(Collections.emptySet(),
			Collections.emptySet(), Collections.emptySet());

	public CollectionDataSourceDiff(final Collection<?> addedElements, final Collection<?> removedElements,
			final Collection<?> modifiedElements) {
		super();
		this.addedElements = addedElements;
		this.removedElements = removedElements;
		this.modifiedElements = modifiedElements;
	}

	@Override
	public int getDiffCount() {
		return addedElements.size() + removedElements.size() + modifiedElements.size();
	}

	public Collection<?> getAddedElements() {
		return addedElements;
	}

	public Collection<?> getModifiedElements() {
		return modifiedElements;
	}

	public Collection<?> getRemovedElements() {
		return removedElements;
	}

	public Collection<?> getAllElements() {
		HashSet<Object> result = new HashSet<Object>();

		result.addAll(addedElements);
		result.addAll(modifiedElements);

		return result;
	}

	@Override
	public String toString() {
		return "CollectionDataSourceDiff [addedElements=" + addedElements + ", removedElements=" + removedElements
				+ ", modifiedElements=" + modifiedElements + "]";
	}

}
