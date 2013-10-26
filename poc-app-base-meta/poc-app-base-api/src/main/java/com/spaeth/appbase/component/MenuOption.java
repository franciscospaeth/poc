package com.spaeth.appbase.component;

import com.spaeth.appbase.component.api.IMenuOption;
import com.spaeth.appbase.model.Action;

public class MenuOption extends AbstractComponent<IMenuOption> implements IMenuOption {

	private static final long serialVersionUID = 1L;

	public MenuOption() {
	}

	public MenuOption(final Action action) {
		setAction(action);
	}

	@Override
	public Action getAction() {
		return getDelegated().getAction();
	}

	@Override
	public void setAction(final Action action) {
		getDelegated().setAction(action);
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

}
