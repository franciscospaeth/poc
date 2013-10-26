package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.component.Component;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.api.IMenuOption;
import com.spaeth.appbase.core.model.DefaultActionParameters;
import com.spaeth.appbase.event.ActionUpdateEvent;
import com.spaeth.appbase.event.ActionUpdateEventListener;
import com.spaeth.appbase.model.Action;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class MenuOption extends com.spaeth.appbase.adds.vaadin.component.MenuItem implements IMenuOption,
ActionUpdateEventListener {

	private static final long serialVersionUID = 1L;

	private String text;
	private StreamProvider icon;
	private Action action;

	@Override
	public Action getAction() {
		return action;
	}

	@Override
	public void setAction(final Action action) {
		this.action = action;
	}

	@Override
	protected Command getCommand() {
		return new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (action != null) {
					action.execute(new DefaultActionParameters.Builder(MenuOption.this).build());
				}
			}
		};
	}

	@Override
	void internalSetParent(final Component parent) {
		super.internalSetParent(parent);
		synchronize();
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
		if (getDelegated() == null) {
			return;
		}
		setIconInternal(icon);
	}

	private void setIconInternal(final StreamProvider icon) {
		if (icon == null) {
			getDelegated().setIcon(null);
		} else {
			getDelegated().setIcon(new StreamProviderResourceAdapter(icon));
		}
	}

	@Override
	public boolean isEnabled() {
		if (action == null) {
			return true;
		}
		return action.isEnabled();
	}

	@Override
	public void actionUpdated(final ActionUpdateEvent actionUpdateEvent) {
		synchronize();
	}

	private void synchronize() {
		getDelegated().setEnabled(isEnabled());
		getDelegated().setText(getText());
		setIconInternal(icon);
	}

}
