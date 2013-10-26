package com.spaeth.appbase.adds.vaadin.component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.spaeth.appbase.adds.vaadin.ComponentWrapperHelper;
import com.spaeth.appbase.component.CollectionViewerComponent;
import com.spaeth.appbase.component.FieldComponent;
import com.spaeth.appbase.component.api.IRepeater;
import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class Repeater extends DetacheableComponent<com.vaadin.ui.VerticalLayout> implements IRepeater, ValueChangeListener {

	private static final long serialVersionUID = 1L;

	private Repeated repeated;
	private CollectionDataSource collectionDataSource;
	private Set<com.spaeth.appbase.component.DetacheableComponent> components = new HashSet<com.spaeth.appbase.component.DetacheableComponent>();
	private com.spaeth.appbase.component.VisualComponent whenEmpty;
	private VerticalLayout whenEmptyContainer;
	
	@Override
	public void setCollectionDataSource(CollectionDataSource colDS) {
		removeListenersFromCurrentSetDataSource();
		if (colDS != null) {
			this.collectionDataSource = colDS;
			colDS.addDataSourceValueChangeListener(this);
			updateComponentVisuals();
		}
		
	}

	private void removeListenersFromCurrentSetDataSource() {
		CollectionDataSource setDS = getCollectionDataSource();
		if (setDS != null) {
			setDS.removeDataSourceValueChangeListener(this);
		}
	}

	@Override
	public CollectionDataSource getCollectionDataSource() {
		return collectionDataSource;
	}

	@Override
	public Repeated getRepeated() {
		return repeated;
	}

	@Override
	public void setRepeated(Repeated repeated) {
		this.repeated = repeated;
	}

	@Override
	protected com.vaadin.ui.VerticalLayout createDelegated() {
		return new com.vaadin.ui.VerticalLayout();
	}

	@Override
	public void onChange(final DataSourceValueChangedEvent event) {
		updateComponentVisuals();
	}

	protected void updateComponentVisuals() {
		removeAllComponents();
		createAllComponents();
	}

	private void createAllComponents() {
		Collection<DataSource> elements = getCollectionDataSource().getElements();
		
		if ((elements == null || elements.isEmpty()) && whenEmptyContainer != null) {
			whenEmptyContainer.setVisible(true);
			return;
		} else if (whenEmptyContainer != null) {
			whenEmptyContainer.setVisible(false);
		}
		
		for (DataSource ds : elements) {
			long start = System.currentTimeMillis();
			com.spaeth.appbase.component.VisualComponent created = repeated.create(ds);
			try {
				new FileWriter("/tmp/vezes",true).append("cc - " + (System.currentTimeMillis() - start) + "ms\n").close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// create components
			components.add(created);
			
			VisualComponent<?> createdNative = ComponentWrapperHelper.unwrap(created, VisualComponent.class);
			Component createdVaadin = createdNative.getDelegated();
			getDelegated().addComponent(createdVaadin);
		}
	}

	private void removeAllComponents() {
		for (com.spaeth.appbase.component.DetacheableComponent dc : components) {
			// clear datasources first
			clearDataSources(dc);
			
		}
		
		getDelegated().removeAllComponents();
		
		if (whenEmptyContainer != null) {
			getDelegated().addComponent(whenEmptyContainer);
		}
	}

	private void clearDataSources(com.spaeth.appbase.component.DetacheableComponent vc) {
		
		if (vc instanceof FieldComponent) {
		
			((FieldComponent) vc).setDataSource(null);
		
		} else if (vc instanceof CollectionViewerComponent) {
			
			((CollectionViewerComponent) vc).setCollectionDataSource(null);
		
		} else if (vc instanceof com.spaeth.appbase.component.ComponentContainer) {

			List<com.spaeth.appbase.component.DetacheableComponent> cmps = ((com.spaeth.appbase.component.ComponentContainer) vc).getComponents();
		
			for (com.spaeth.appbase.component.DetacheableComponent c : cmps) {
				clearDataSources(c);
			}
		
		}
		
	}

	@Override
	public void setComponentWhenEmpty(com.spaeth.appbase.component.VisualComponent visualComponent) {
		this.whenEmpty = visualComponent;
		if (whenEmpty != null) {
			whenEmptyContainer = new VerticalLayout();
			whenEmptyContainer.setWidth("100%");
			VisualComponent<?> whenEmptyVaadin = ComponentWrapperHelper.unwrap(whenEmpty, VisualComponent.class);
			whenEmptyContainer.addComponent(whenEmptyVaadin.getDelegated());
			getDelegated().addComponent(whenEmptyContainer);
		}
	}

	@Override
	public com.spaeth.appbase.component.VisualComponent getComponentWhenEmpty() {
		return whenEmpty;
	}

}
