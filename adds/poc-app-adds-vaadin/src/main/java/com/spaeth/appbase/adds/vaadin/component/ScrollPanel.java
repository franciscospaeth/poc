package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.component.api.ILayout;
import com.spaeth.appbase.component.api.IScrollPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class ScrollPanel extends ComponentContainer<Panel> implements IScrollPanel {

	private static final long serialVersionUID = 1L;

	public ScrollPanel() {
	}

	@Override
	protected Panel createDelegated() {
		Panel delegated = new Panel();
		VerticalLayout content = new VerticalLayout();
		content.setMargin(false);
		delegated.setContent(content);
		delegated.setStyleName(Reindeer.PANEL_LIGHT);
		return delegated;
	}

	public ScrollPanel(final ILayout layout) {
		this();
		addComponent(layout);
	}

	@Override
	public void addComponent(final com.spaeth.appbase.component.DetacheableComponent component) {
		if (getComponents().size() == 0) {
			super.addComponent(component);
		} else {
			throw new IllegalStateException(IScrollPanel.class.getName()
					+ " shall hold just one, and maximum one component");
		}
	}

}
