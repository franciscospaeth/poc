package com.spaeth.appbase.core.viewport;

import java.util.HashMap;
import java.util.Map;

import com.spaeth.appbase.ViewPort;
import com.spaeth.appbase.component.DetacheableComponent;
import com.spaeth.appbase.component.PanelTab;
import com.spaeth.appbase.component.TabbedPanel;
import com.spaeth.appbase.component.api.ITabbedPanel.TabCloseListener;
import com.spaeth.appbase.component.api.ITabbedPanel.TabClosedEvent;
import com.spaeth.appbase.event.ViewShowEvent;
import com.spaeth.appbase.model.FramedView;
import com.spaeth.appbase.model.View;
import com.spaeth.appbase.model.ViewCloseHandler;

public abstract class TabbedPanelViewPort implements ViewPort {

	private final Map<View<?>, PanelTab> tabs = new HashMap<View<?>, PanelTab>();

	@Override
	public boolean showView(final View<?> view) {
		if (!(view instanceof FramedView)) {
			return false;
		}

		TabbedPanel tabbedPanel = getViewPort(view);

		if (tabbedPanel == null) {
			return false;
		}

		final PanelTab tab = createTab(view);
		tab.addComponent((DetacheableComponent) view.getContent());

		configureSuitableProperties(tab, view);

		tabbedPanel.addTab(tab);

		tabbedPanel.addListener(new TabCloseListener() {
			@Override
			public void onClose(final TabClosedEvent event) {
				if (event.getSource() == tab) {
					view.close();
					event.abort();
				}
			}
		});

		tabbedPanel.focusTab(tab);

		tabs.put(view, tab);

		view.onShown(new ViewShowEvent(this, ViewCloseHandler.DUMMY));

		return true;
	}

	@Override
	public void viewClosed(final View<?> view) {
		TabbedPanel viewPort = getViewPort(view);
		viewPort.removeTab(tabs.get(view));
	}

	protected PanelTab createTab(final View<?> view) {
		return new PanelTab();
	}

	protected void configureSuitableProperties(final PanelTab tab, final View<?> view) {
		if (view instanceof FramedView) {
			final FramedView framedView = (FramedView) view;
			tab.setCaption(framedView.getTitle());
			tab.setClosable(framedView.isClosable());
			tab.setIcon(((FramedView) view).getIcon());
		}
	}

	protected abstract TabbedPanel getViewPort(View<?> view);

}
