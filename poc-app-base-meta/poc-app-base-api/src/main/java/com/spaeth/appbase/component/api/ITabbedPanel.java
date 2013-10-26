package com.spaeth.appbase.component.api;

import java.util.List;

import com.spaeth.appbase.component.VisualComponent;
import com.spaeth.appbase.component.api.event.Event;
import com.spaeth.appbase.component.api.event.EventListener;

public interface ITabbedPanel extends VisualComponent {

	void addTab(IPanelTab tab);

	void removeTab(IPanelTab panelTab);

	void focusTab(IPanelTab panelTab);

	List<IPanelTab> getTabs();

	void addListener(TabCloseListener listener);

	void removeListener(TabCloseListener listener);

	public static class TabClosedEvent extends Event {

		private static final long serialVersionUID = 1L;
		private boolean abortClose = false;

		public TabClosedEvent(final IPanelTab source) {
			super(source);
		}

		@Override
		public IPanelTab getSource() {
			return (IPanelTab) super.getSource();
		}

		public void abort() {
			this.abortClose = true;
		}

		public boolean isAbort() {
			return abortClose;
		}

	}

	public static interface TabCloseListener extends EventListener {

		void onClose(TabClosedEvent event);

	}

}
