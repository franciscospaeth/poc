package com.spaeth.appbase.component;

import java.util.List;

import com.spaeth.appbase.component.api.IMenu;

public class Menu extends AbstractComponent<IMenu> implements IMenu {

	private static final long serialVersionUID = 1L;

	@Override
	public List<MenuItem> getOptions() {
		return getDelegated().getOptions();
	}

	@Override
	public void setOptions(final List<MenuItem> options) {
		getDelegated().setOptions(options);
	}

	@Override
	public String getText() {
		return getDelegated().getText();
	}

	@Override
	public void setText(final String caption) {
		getDelegated().setText(caption);
	}

	@Override
	public StreamProvider getIcon() {
		return getDelegated().getIcon();
	}

	@Override
	public void setIcon(final StreamProvider icon) {
		getDelegated().setIcon(icon);
	}

	@Override
	public boolean isEnabled() {
		return getDelegated().isEnabled();
	}

	@Override
	public void setEnabled(final boolean enabled) {
		getDelegated().setEnabled(enabled);
	}

}
