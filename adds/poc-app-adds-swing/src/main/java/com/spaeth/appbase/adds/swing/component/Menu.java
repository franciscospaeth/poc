package com.spaeth.appbase.adds.swing.component;

import java.util.Collections;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.apache.commons.collections.CollectionUtils;

import com.spaeth.appbase.adds.swing.ComponentWrapperHelper;
import com.spaeth.appbase.component.MenuItem;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.api.IMenu;

public class Menu extends com.spaeth.appbase.adds.swing.component.MenuItem implements
		com.spaeth.appbase.component.Component, IMenu {

	private static final long serialVersionUID = 1L;
	private StreamProvider icon;
	private List<MenuItem> options = Collections.emptyList();

	@Override
	public List<MenuItem> getOptions() {
		return options;
	}

	@Override
	public void setOptions(final List<MenuItem> options) {
		for (Object mi : CollectionUtils.subtract(options, this.options)) {
			ComponentWrapperHelper.unwrap((com.spaeth.appbase.component.Component) mi,
					com.spaeth.appbase.adds.swing.component.MenuItem.class).initialize(getDelegated());
		}
		for (Object mi : CollectionUtils.subtract(this.options, options)) {
			getDelegated().remove(
					ComponentWrapperHelper.unwrap((com.spaeth.appbase.component.Component) mi,
							com.spaeth.appbase.adds.swing.component.MenuItem.class).getDelegated());
		}
		this.options = options;
	}

	@Override
	public String getText() {
		return getDelegated().getText();
	}

	@Override
	public void setText(final String text) {
		getDelegated().setText(text);
	}

	@Override
	public StreamProvider getIcon() {
		return icon;
	}

	@Override
	public void setIcon(final StreamProvider icon) {
		if (icon == null) {
			getDelegated().setIcon(null);
		} else {
			getDelegated().setIcon(new StreamProviderIconAdapter(icon));
		}
		this.icon = icon;
	}

	@Override
	public boolean isEnabled() {
		return getDelegated().isEnabled();
	}

	@Override
	public void setEnabled(final boolean enabled) {
		getDelegated().setEnabled(enabled);
	}

	@Override
	protected JMenuItem createDelegated() {
		return new JMenu();
	}

}
