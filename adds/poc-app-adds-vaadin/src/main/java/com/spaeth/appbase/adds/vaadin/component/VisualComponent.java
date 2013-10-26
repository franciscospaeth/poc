package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.adds.vaadin.ComponentWrapperHelper;
import com.vaadin.ui.AbstractComponentContainer;

public abstract class VisualComponent<ComponentClass extends com.vaadin.ui.Component> extends Component<ComponentClass>
		implements com.spaeth.appbase.component.VisualComponent {

	private static final long serialVersionUID = 1L;

	private Measure width;
	private Measure height;

	@Override
	public Measure getWidth() {
		return width;
	}

	@Override
	public void setWidth(final Measure width) {
		if (width == Measure.ZERO) {
			return;
		}
		getDelegated().setWidth(width.toString());
		this.width = width;
	}

	@Override
	public float getNormalizedWidth() {
		return Float.valueOf(getDelegated().getWidth()).floatValue();
	}

	@Override
	public Measure getHeight() {
		return height;
	}

	@Override
	public void setHeight(final Measure height) {
		if (height == Measure.ZERO) {
			return;
		}
		getDelegated().setHeight(height.toString());
		this.height = height;
	}

	@Override
	public float getNormalizedHeight() {
		return Float.valueOf(getDelegated().getHeight()).floatValue();
	}

	@Override
	public boolean isVisible() {
		return getDelegated().isVisible();
	}

	@Override
	public void setVisible(final boolean visible) {
		getDelegated().setVisible(visible);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setParent(final com.spaeth.appbase.component.ComponentContainer componentContainer) {
		ComponentContainer<AbstractComponentContainer> cc = null;

		// unwrapp if there is any object informed
		if (componentContainer != null) {
			cc = ComponentWrapperHelper.unwrap(componentContainer, ComponentContainer.class);
		}

		// get old one to remove the current component
		if (this.getParent() != null) {
			cc.getDelegated().removeComponent(this.getDelegated());
			((ComponentContainer<?>) this.getParent()).internalRemoveComponent(this);
		}

		// get new one to add it to the new container
		if (cc != null) {
			ComponentClass c = this.getDelegated();
			cc.getDelegated().addComponent(c);
			cc.internalAddComponent(this);
		}

	}

}
