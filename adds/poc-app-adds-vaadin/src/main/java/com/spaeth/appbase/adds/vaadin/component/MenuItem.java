package com.spaeth.appbase.adds.vaadin.component;

import org.apache.commons.lang3.StringUtils;

import com.spaeth.appbase.adds.vaadin.ComponentWrapperHelper;
import com.vaadin.ui.MenuBar.Command;

public abstract class MenuItem extends Component<com.vaadin.ui.MenuBar.MenuItem> implements
		com.spaeth.appbase.component.MenuItem {

	private static final long serialVersionUID = 1L;

	@Override
	protected com.vaadin.ui.MenuBar.MenuItem createDelegated() {
		return null;
	}

	@Override
	void internalSetParent(final com.spaeth.appbase.component.Component parent) {
		super.internalSetParent(parent);
		Component<?> unwrapped = ComponentWrapperHelper.unwrap(parent, Component.class);
		Object parentDelegated = unwrapped.getDelegated();

		com.vaadin.ui.MenuBar.MenuItem menuItem = null;
		if (parentDelegated instanceof com.vaadin.ui.MenuBar) {
			menuItem = ((com.vaadin.ui.MenuBar) parentDelegated).addItem(StringUtils.defaultString(getText()),
					getCommand());
		} else if (parentDelegated instanceof com.vaadin.ui.MenuBar.MenuItem) {
			menuItem = ((com.vaadin.ui.MenuBar.MenuItem) parentDelegated).addItem(StringUtils.defaultString(getText()),
					getCommand());
		}

		if (menuItem == null) {
			throw new IllegalArgumentException("parent for a menu item should be a menu bar or other menu item");
		}

		if (getDelegated() != null) {
			throw new IllegalStateException("docking of menu item is not yet supported");
		}

		menuItem.setEnabled(isEnabled());

		setDelegated(menuItem);
	}

	protected Command getCommand() {
		return null;
	}

}
