package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.adds.vaadin.component.mediator.VaadinDataSourceContainerAdapter;
import com.spaeth.appbase.adds.vaadin.component.mediator.VaadinDataSourcePropertyAdapter;
import com.spaeth.appbase.adds.vaadin.component.mediator.VaadinDataSourceContainerAdapter.VaadinDataSourceContainerAdapterListener;
import com.spaeth.appbase.component.CollectionViewerComponent;
import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Viewer;
import com.vaadin.ui.Field;

abstract class VisualCollectionFieldComponent<ComponentClass extends Field> extends VisualFieldComponent<ComponentClass> implements
		CollectionViewerComponent, ValueChangeListener {

	private static final long serialVersionUID = 1L;

	public Container.Viewer getDelegatedContainerView() {
		return (Viewer) super.getDelegated();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		removeListenersFromCurrentSetDataSource();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		if (!(getDelegated() instanceof Container.Viewer)) {
			throw new IllegalStateException("a visual collection field component needs to become as delegated an " + Container.Viewer.class
					+ " but " + getDelegated().getClass() + " received instead");
		}
	}

	@Override
	public void setCollectionDataSource(final CollectionDataSource colDS) {
		removeListenersFromCurrentSetDataSource();
		if (colDS != null) {
			VaadinDataSourceContainerAdapter containerDataSource = createCollectionDataSourceAdapter(colDS);
			getDelegatedContainerView().setContainerDataSource(containerDataSource);
			colDS.addDataSourceValueChangeListener(this);
			colDS.addDataSourceValueChangeListener(containerDataSource);
			containerDataSource.setListener(new VaadinDataSourceContainerAdapterListener() {
				@Override
				public void propertySetChange(final CollectionDataSource dataSource) {
					onContainerPropertiesUpdated(dataSource);
				}
			});
		}
	}

	protected VaadinDataSourceContainerAdapter createCollectionDataSourceAdapter(final CollectionDataSource colDS) {
		return new VaadinDataSourceContainerAdapter(colDS);
	}

	@Override
	public CollectionDataSource getCollectionDataSource() {
		Container ds = getDelegatedContainerView().getContainerDataSource();

		if (ds == null) {
			return null;
		}

		if (ds instanceof VaadinDataSourceContainerAdapter) {
			return ((VaadinDataSourceContainerAdapter) ds).getDataSource();
		}

		return null;

	}

	@Override
	public void onChange(final DataSourceValueChangedEvent event) {
	}

	protected void onContainerPropertiesUpdated(final CollectionDataSource dataSource) {
	}

	private void removeListenersFromCurrentSetDataSource() {
		CollectionDataSource setDS = getCollectionDataSource();
		if (setDS != null) {
			setDS.removeDataSourceValueChangeListener(this);
			setDS.removeDataSourceValueChangeListener((ValueChangeListener) getDelegatedContainerView().getContainerDataSource());
		}
	}

	@Override
	protected VaadinDataSourcePropertyAdapter createDataSourcePropertyAdapter() {
		return new VaadinDataSourcePropertyAdapter(VaadinDataSourcePropertyAdapter.NULL_PROPERTY_VALUE);
	}
	
}
