package com.spaeth.appbase.adds.swing.component;

import java.awt.BorderLayout;
import java.awt.ScrollPane;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.spaeth.appbase.adds.swing.ComponentWrapperHelper;
import com.spaeth.appbase.component.api.ILayout;
import com.spaeth.appbase.component.api.IScrollPanel;

public class ScrollPanel extends ComponentContainer<JPanel> implements IScrollPanel {

	private static final long serialVersionUID = 1L;
	private ScrollPane container;

	public ScrollPanel() {
	}

	@Override
	protected JPanel createDelegated() {
		JPanel delegated = new JPanel(new BorderLayout());
		delegated.add(container = new ScrollPane());
		return delegated;
	}

	public ScrollPanel(final ILayout layout) {
		this();
		addComponent(layout);
	}

	@Override
	public void addComponent(final com.spaeth.appbase.component.DetacheableComponent component) {
		if (getComponents().size() == 0) {
			@SuppressWarnings("unchecked")
			DetacheableComponent<JComponent> unwrapped = ComponentWrapperHelper.unwrap(component,
					DetacheableComponent.class);
			container.add(unwrapped.getDelegated());
		} else {
			throw new IllegalStateException(IScrollPanel.class.getName()
					+ " shall hold just one, and maximum one component");
		}
	}

}
