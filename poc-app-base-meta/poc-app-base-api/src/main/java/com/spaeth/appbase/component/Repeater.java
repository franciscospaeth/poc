package com.spaeth.appbase.component;

import com.spaeth.appbase.component.api.IRepeater;
import com.spaeth.appbase.core.datasource.CollectionDataSource;

public class Repeater extends AbstractComponent<IRepeater> implements IRepeater {

	private static final long serialVersionUID = 1L;

	@Override
	public Measure getWidth() {
		return getDelegated().getWidth();
	}

	@Override
	public float getNormalizedWidth() {
		return getDelegated().getNormalizedWidth();
	}

	@Override
	public void setWidth(Measure width) {
		getDelegated().setWidth(width);
	}

	@Override
	public Measure getHeight() {
		return getDelegated().getHeight();
	}

	@Override
	public float getNormalizedHeight() {
		return getDelegated().getNormalizedHeight();
	}

	@Override
	public void setHeight(Measure height) {
		getDelegated().setHeight(height);
	}

	@Override
	public boolean isVisible() {
		return getDelegated().isVisible();
	}

	@Override
	public void setVisible(boolean visible) {
		getDelegated().setVisible(visible);
	}

	@Override
	public void setParent(ComponentContainer componentContainer) {
		getDelegated().setParent(componentContainer);
	}

	@Override
	public void setCollectionDataSource(
			CollectionDataSource collectionDataSource) {
		getDelegated().setCollectionDataSource(collectionDataSource);
	}

	@Override
	public CollectionDataSource getCollectionDataSource() {
		return getDelegated().getCollectionDataSource();
	}

	@Override
	public Repeated getRepeated() {
		return getDelegated().getRepeated();
	}

	@Override
	public void setRepeated(Repeated repeated) {
		getDelegated().setRepeated(repeated);
	}
	
	@Override
	public VisualComponent getComponentWhenEmpty() {
		return getDelegated().getComponentWhenEmpty();
	}
	
	@Override
	public void setComponentWhenEmpty(VisualComponent visualComponent) {
		getDelegated().setComponentWhenEmpty(visualComponent);
	}

}
