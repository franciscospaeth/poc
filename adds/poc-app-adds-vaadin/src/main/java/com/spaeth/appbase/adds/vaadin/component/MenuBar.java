package com.spaeth.appbase.adds.vaadin.component;

import java.util.List;

import com.spaeth.appbase.adds.vaadin.ComponentWrapperHelper;
import com.spaeth.appbase.component.Component;
import com.spaeth.appbase.component.api.IMenuBar;

public class MenuBar extends DetacheableComponent<com.vaadin.ui.MenuBar> implements IMenuBar {

	private static final long serialVersionUID = 1L;

	private List<com.spaeth.appbase.component.MenuItem> options;

	@Override
	protected com.vaadin.ui.MenuBar createDelegated() {
		return new com.vaadin.ui.MenuBar();
	}

	@Override
	public List<com.spaeth.appbase.component.MenuItem> getOptions() {
		return options;
	}

	@Override
	public void setOptions(final List<com.spaeth.appbase.component.MenuItem> options) {
		this.options = options;
		if (getDelegated() == null) {
			return;
		}
		updateSubMenuParent();
	}

	@Override
	void internalSetParent(final Component parent) {
		super.internalSetParent(parent);
		updateSubMenuParent();
	}

	private void updateSubMenuParent() {
		for (com.spaeth.appbase.component.MenuItem menuItem : options) {
			ComponentWrapperHelper.unwrap(menuItem, MenuItem.class).internalSetParent(this);
		}
	}

}
