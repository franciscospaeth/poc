package com.spaeth.appbase.adds.swing.component;

import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.commons.lang3.ObjectUtils;

import com.spaeth.appbase.adds.swing.ComponentWrapperHelper;
import com.spaeth.appbase.adds.swing.component.customized.layout.OrderedLayoutConstraint;
import com.spaeth.appbase.adds.swing.component.customized.layout.OrderedLayoutManager;

public abstract class VisualComponent<ComponentClass extends JComponent> extends Component<ComponentClass> implements
		com.spaeth.appbase.component.VisualComponent {

	private static final long serialVersionUID = 1L;

	private Measure width = null;
	private Measure height = null;

	@Override
	public Measure getWidth() {
		return width;
	}

	@Override
	public void setWidth(final Measure width) {
		this.width = width;

		Container parent = getDelegated().getParent();
		if (parent != null) {
			OrderedLayoutManager<?> olm = (OrderedLayoutManager<?>) parent.getLayout();
			olm.setWidth(getDelegated(), ObjectUtils.toString(width, null));
		}
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
		this.height = height;

		Container parent = getDelegated().getParent();
		if (parent != null) {
			OrderedLayoutManager<?> olm = (OrderedLayoutManager<?>) parent.getLayout();
			olm.setHeight(getDelegated(), ObjectUtils.toString(height, null));
		}
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
		ComponentContainer<JPanel> cc = null;

		// unwrapp if there is any object informed
		if (componentContainer != null) {
			cc = ComponentWrapperHelper.unwrap(componentContainer, ComponentContainer.class);
		}

		// get old one to remove the current component
		if (this.getParent() != null) {
			cc.getDelegated().remove(this.getDelegated());
			((ComponentContainer<?>) this.getParent()).internalRemoveComponent(this);
		}

		// get new one to add it to the new container
		if (cc != null) {
			ComponentClass c = this.getDelegated();
			if (c == null) {
				throw new IllegalStateException("adding component (" + this + ") has no delegated component");
			}
			cc.getDelegated().add(
					c,
					new OrderedLayoutConstraint(ObjectUtils.toString(width, null), ObjectUtils.toString(height, null),
							0));
			cc.internalAddComponent(this);
		}

	}

}
