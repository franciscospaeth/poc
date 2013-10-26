package com.spaeth.appbase.core.model.action;

import java.util.ArrayList;
import java.util.List;

import com.spaeth.appbase.event.ActionUpdateEvent;
import com.spaeth.appbase.event.ActionUpdateEventListener;
import com.spaeth.appbase.model.Action;
import com.spaeth.appbase.model.ActionParameters;
import com.spaeth.appbase.model.ActionSecurityConstraint;

public abstract class AbstractAction implements Action {

	private static final long serialVersionUID = 1L;

	/**
	 * If action could even be accessed by this user. The inverse of enable,
	 * that is a property that holds if the action can be invoked in a certain
	 * point at time, the accessible field defines that this action will never
	 * be accessible (in case of false) or it will depend on the time the user
	 * will try to access if (relies it to enable property).
	 */
	private boolean accessible = false;
	/**
	 * Enable represent if the action can be invoked at the current time the
	 * user see it.
	 */
	private boolean enable = true;
	// private String icon = "";
	// private String caption = "";
	private final List<ActionUpdateEventListener> actionUpdateListeners = new ArrayList<ActionUpdateEventListener>();
	private final List<ActionSecurityConstraint> securityConstraints = new ArrayList<ActionSecurityConstraint>();

	public AbstractAction() {
		super();
		refreshSecurityEvaluations();
	}

	/*
	 * public AbstractAction(final String caption) { super(); this.caption =
	 * caption; refreshSecurityEvaluations(); }
	 */

	/*
	 * public AbstractAction(final String caption, final String icon) { super();
	 * //this.icon = icon; //this.caption = caption;
	 * refreshSecurityEvaluations(); }
	 */

	@Override
	public void addSecurityConstraint(final ActionSecurityConstraint securityConstraint) {
		this.securityConstraints.add(securityConstraint);
		refreshSecurityEvaluations();
	}

	@Override
	public void removeSecurityConstraint(final ActionSecurityConstraint securityConstraint) {
		this.securityConstraints.remove(securityConstraint);
		refreshSecurityEvaluations();
	}

	@Override
	public boolean isEnabled() {
		return this.enable && this.accessible;
	}

	@Override
	public void setEnabled(final boolean enable) {
		final boolean isEnabled = isEnabled();
		this.enable = enable;
		if (isEnabled != isEnabled()) {
			fireActionChangeEvent();
		}
	}

	/*
	 * @Override public String getCaption() { return this.caption; }
	 * 
	 * @Override public void setCaption(final String caption) { if
	 * (this.caption.equals(caption)) { return; } this.caption = caption; }
	 */

	/*
	 * @Override public String getIcon() { return this.icon; }
	 * 
	 * @Override public void setIcon(final String icon) { if
	 * (this.icon.equals(this.icon)) { return; } this.icon = icon; }
	 */

	@Override
	public void addUpdateListener(final ActionUpdateEventListener listener) {
		this.actionUpdateListeners.add(listener);
	}

	@Override
	public void removeUpdateListener(final ActionUpdateEventListener listener) {
		this.actionUpdateListeners.remove(listener);
	}

	boolean isAccessible() {
		return this.accessible;
	}

	private void setAccessible(final boolean accessible) {
		final boolean enabled = isEnabled();

		this.accessible = accessible;
		if (enabled != isEnabled()) {
			fireActionChangeEvent();
		}
	}

	protected void fireActionChangeEvent() {
		for (final ActionUpdateEventListener listener : this.actionUpdateListeners) {
			listener.actionUpdated(createActionEvent());
		}
	}

	@Override
	public void refreshSecurityEvaluations() {
		boolean accessible = true;
		for (final ActionSecurityConstraint asc : this.securityConstraints) {
			accessible &= asc.isAccessible(this);
		}
		setAccessible(accessible);
	}

	@Override
	public final void execute(final ActionParameters parameters) {
		if (isEnabled()) {
			internalExecute(parameters);
		}
	}

	protected abstract void internalExecute(ActionParameters parameters);

	protected abstract ActionUpdateEvent createActionEvent();

}