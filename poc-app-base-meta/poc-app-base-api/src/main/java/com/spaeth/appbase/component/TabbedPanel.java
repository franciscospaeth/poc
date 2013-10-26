package com.spaeth.appbase.component;

import java.util.List;

import com.spaeth.appbase.component.api.IPanelTab;
import com.spaeth.appbase.component.api.ITabbedPanel;

public class TabbedPanel extends AbstractComponent<ITabbedPanel> implements ITabbedPanel {

	private static final long serialVersionUID = 1L;

	@Override
	public void setParent(final ComponentContainer componentContainer) {
		getDelegated().setParent(componentContainer);
	}

	@Override
	public Measure getWidth() {
		return getDelegated().getWidth();
	}

	@Override
	public void setWidth(final Measure width) {
		getDelegated().setWidth(width);
	}

	@Override
	public float getNormalizedWidth() {
		return getDelegated().getNormalizedWidth();
	}

	@Override
	public Measure getHeight() {
		return getDelegated().getHeight();
	}

	@Override
	public float getNormalizedHeight() {
		return getDelegated().getNormalizedHeight();
	}

	@Override
	public void setHeight(final Measure height) {
		getDelegated().setHeight(height);
	}

	@Override
	public boolean isVisible() {
		return getDelegated().isVisible();
	}

	@Override
	public void setVisible(final boolean visible) {
		getDelegated().setVisible(visible);
	}

	@Override
	public void addTab(final IPanelTab tab) {
		getDelegated().addTab(tab);
	}

	@Override
	public void removeTab(final IPanelTab panelTab) {
		getDelegated().removeTab(panelTab);
	}

	@Override
	public List<IPanelTab> getTabs() {
		return getDelegated().getTabs();
	}

	@Override
	public void focusTab(final IPanelTab panelTab) {
		getDelegated().focusTab(panelTab);
	}

	@Override
	public void addListener(final TabCloseListener listener) {
		getDelegated().addListener(listener);
	}

	@Override
	public void removeListener(final TabCloseListener listener) {
		getDelegated().removeListener(listener);
	}

}
