package com.spaeth.appbase.adds.vaadin.component.mediator;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;

import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.DataSource;
import com.vaadin.data.Container;

public class VaadinHierarchicalDataSource extends VaadinDataSourceContainerAdapter implements Container.Hierarchical {

	private static final long serialVersionUID = 1L;

	private final Transformer unwrapDataSourceTransformer = new Transformer() {
		@Override
		public Object transform(final Object input) {
			if (input instanceof DataSource) {
				return ((DataSource) input).get();
			}
			throw new IllegalArgumentException("a datasource is required to perform datasource unwrapping");
		}
	};

	private final CollectionDataSource collectionDataSource;
	private final String parentProperty;

	public VaadinHierarchicalDataSource(final CollectionDataSource collectionDataSource, final String parentProperty) {
		super(collectionDataSource);
		this.collectionDataSource = collectionDataSource;
		this.parentProperty = parentProperty;
	}

	@Override
	public Collection<?> getChildren(final Object itemId) {
		Collection<?> result = CollectionUtils.select( //
				collectionDataSource.getElements(), //
				new ChildrenSearchPredicate(parentProperty, itemId));
		return CollectionUtils.collect(result, unwrapDataSourceTransformer);
	}

	@Override
	public Object getParent(final Object itemId) {
		DataSource element = collectionDataSource.getElement(itemId);

		if (element == null) {
			return null;
		}

		DataSource ds = element.getDataSource(parentProperty);
		return collectionDataSource.getElement(ds.get());
	}

	@Override
	public Collection<?> rootItemIds() {
		Collection<?> result = CollectionUtils.select( //
				collectionDataSource.getElements(), //
				new ChildrenSearchPredicate(parentProperty, null));
		return CollectionUtils.collect(result, unwrapDataSourceTransformer);
	}

	@Override
	public boolean setParent(final Object itemId, final Object newParentId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean areChildrenAllowed(final Object itemId) {
		return getChildren(itemId).size() > 0;
	}

	@Override
	public boolean setChildrenAllowed(final Object itemId, final boolean areChildrenAllowed) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isRoot(final Object itemId) {
		DataSource element = collectionDataSource.getElement(itemId);

		if (element == null) {
			return false;
		}

		DataSource ds = element.getDataSource(parentProperty);
		return ds.get() == null;
	}

	@Override
	public boolean hasChildren(final Object itemId) {
		return getChildren(itemId).size() > 0;
	}

	private static class ChildrenSearchPredicate implements Predicate {

		private final String parentProperty;
		private final Object parent;

		public ChildrenSearchPredicate(final String parentProperty, final Object parent) {
			super();
			this.parentProperty = parentProperty;
			this.parent = parent;
		}

		@Override
		public boolean evaluate(final Object input) {
			if (!(input instanceof DataSource)) {
				return false;
			}

			DataSource ds = (DataSource) input;

			return ds.getDataSource(parentProperty).get() == parent;
		}

	}

}
