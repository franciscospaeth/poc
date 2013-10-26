package com.spaeth.appbase.adds.swing.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.api.IMenuOption;
import com.spaeth.appbase.core.model.DefaultActionParameters;
import com.spaeth.appbase.event.ActionUpdateEvent;
import com.spaeth.appbase.event.ActionUpdateEventListener;
import com.spaeth.appbase.model.Action;

public class MenuOption extends MenuItem implements IMenuOption, ActionUpdateEventListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private StreamProvider icon;
	private Action action;

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
	public Action getAction() {
		return action;
	}

	@Override
	public void setAction(final Action action) {
		if (action != null) {
			action.removeUpdateListener(this);
		}
		this.action = action;
		if (action != null) {
			action.addUpdateListener(this);
		}
	}

	@Override
	protected JMenuItem createDelegated() {
		JMenuItem result = new JMenuItem();
		result.addActionListener(this);
		return result;
	}

	@Override
	public void actionUpdated(final ActionUpdateEvent actionUpdateEvent) {
		getDelegated().setEnabled(actionUpdateEvent.getAction().isEnabled());
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (action == null) {
			return;
		}
		action.execute(new DefaultActionParameters.Builder(this).build());
	}

}
