package com.spaeth.appbase.adds.swing.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.spaeth.appbase.adds.swing.ComponentWrapperHelper;
import com.spaeth.appbase.component.api.IPanelTab;
import com.spaeth.appbase.component.api.ITabbedPanel;

public class TabbedPanel extends DetacheableComponent<JTabbedPane> implements ITabbedPanel {

	private static final long serialVersionUID = 1L;

	private final Map<JPanel, IPanelTab> tabs = new LinkedHashMap<JPanel, IPanelTab>();

	@Override
	protected JTabbedPane createDelegated() {
		JTabbedPane delegated = new JTabbedPane();
		return delegated;
	}

	@Override
	public void addTab(final IPanelTab tab) {
		PanelTab pt = ComponentWrapperHelper.unwrap(tab, PanelTab.class);

		JPanel contained = pt.getDelegated();

		pt.internalSetParent(this);

		tabs.put(contained, tab);
	}

	@Override
	public void removeTab(final IPanelTab panelTab) {
		PanelTab panelTabUnwrapped = ComponentWrapperHelper.unwrap(panelTab, PanelTab.class);

		JPanel delegated = panelTabUnwrapped.getDelegated();

		getDelegated().remove(delegated);

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
		getDelegated().setSelectedComponent(pt.getDelegated());
	}

	@Override
	public void addListener(final TabCloseListener listener) {
		throw new IllegalStateException("pedinging implementation");
	}

	@Override
	public void removeListener(final TabCloseListener listener) {
		throw new IllegalStateException("pedinging implementation");
	}

}
