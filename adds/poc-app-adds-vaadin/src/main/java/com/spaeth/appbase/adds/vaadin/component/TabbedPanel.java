package com.spaeth.appbase.adds.vaadin.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.adds.vaadin.ComponentWrapperHelper;
import com.spaeth.appbase.adds.vaadin.component.PanelTab.LazyTab;
import com.spaeth.appbase.component.api.IPanelTab;
import com.spaeth.appbase.component.api.ITabbedPanel;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.themes.Reindeer;

public class TabbedPanel extends DetacheableComponent<com.vaadin.ui.TabSheet> implements ITabbedPanel, CloseHandler {

	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<Tab, IPanelTab> tabs = new LinkedHashMap<Tab, IPanelTab>();

	private final List<TabCloseListener> tabCloseListeners = new ArrayList<ITabbedPanel.TabCloseListener>();

	@Override
	protected TabSheet createDelegated() {
		TabSheet delegated = new TabSheet();
		delegated.setStyleName(Reindeer.TABSHEET_SMALL);
		delegated.setCloseHandler(this);
		return delegated;
	}

	@Override
	public void addTab(final IPanelTab tab) {
		PanelTab pt = ComponentWrapperHelper.unwrap(tab, PanelTab.class);

		AbstractOrderedLayout contained = pt.getContent().getDelegated();
		Tab addedTab = getDelegated().addTab(contained);
		ComponentWrapperHelper.unwrap(tab, PanelTab.class).initialize(addedTab);

		tabs.put(addedTab, tab);
	}

	@Override
	public void removeTab(final IPanelTab panelTab) {
		PanelTab panelTabUnwrapped = ComponentWrapperHelper.unwrap(panelTab, PanelTab.class);

		Tab delegated = panelTabUnwrapped.getDelegated();

		if (delegated instanceof LazyTab) {
			throw new IllegalArgumentException("tab wasn't initializated yet, due this reason you can't remove it from this tabbed panel");
		}

		getDelegated().removeTab(delegated);

		tabs.remove(delegated);
	}

	@Override
	public List<IPanelTab> getTabs() {
		return Collections.unmodifiableList(new ArrayList<IPanelTab>(tabs.values()));
	}

	@Override
	public void focusTab(final IPanelTab panelTab) {
		if (!tabs.values().contains(panelTab)) {
			throw new IllegalStateException("just tabs added to the tabbed panel could be focused");
		}

		PanelTab pt = ComponentWrapperHelper.unwrap(panelTab, PanelTab.class);
		getDelegated().setSelectedTab(pt.getContent().getDelegated());
	}

	@Override
	public void onTabClose(final TabSheet tabsheet, final Component tabContent) {
		Tab tab = tabsheet.getTab(tabContent);

		// created event in order to proceed with notifications
		TabClosedEvent event = new TabClosedEvent(tabs.get(tab));

		// notifying
		for (TabCloseListener tcl : tabCloseListeners) {
			try {
				tcl.onClose(event);
			} catch (Exception e) {
				logger.warn("exception while notifying listeners about tab closing: " + e.getMessage(), e);
			}
			if (event.isAbort()) {
				break;
			}
		}

		// removing when no abort was required on notifications
		if (!event.isAbort()) {
			removeTab(tabs.get(tab));
		}
	}

	@Override
	public void addListener(final TabCloseListener listener) {
		tabCloseListeners.add(listener);
	}

	@Override
	public void removeListener(final TabCloseListener listener) {
		tabCloseListeners.remove(listener);
	}

}
