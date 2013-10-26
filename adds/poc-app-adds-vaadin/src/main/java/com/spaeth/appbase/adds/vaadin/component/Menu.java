package com.spaeth.appbase.adds.vaadin.component;

import java.util.List;

import com.spaeth.appbase.adds.vaadin.ComponentWrapperHelper;
import com.spaeth.appbase.component.Component;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.api.IMenu;

public class Menu extends MenuItem implements com.spaeth.appbase.component.Component, IMenu {

	private static final long serialVersionUID = 1L;

	private String text;
	private StreamProvider icon;
	private boolean enabled = true;

	private List<com.spaeth.appbase.component.MenuItem> options;

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

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(final String text) {
		this.text = text;
		if (getDelegated() == null) {
			return;
		}
		getDelegated().setText(text);
	}

	@Override
	public StreamProvider getIcon() {
		return icon;
	}

	@Override
	public void setIcon(final StreamProvider icon) {
		this.icon = icon;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
		if (getDelegated() == null) {
			return;
		}
		this.enabled = enabled;
	}

	@Override
	protected void onInitialize() {
		setText(text);
		setEnabled(enabled);
	}

}
