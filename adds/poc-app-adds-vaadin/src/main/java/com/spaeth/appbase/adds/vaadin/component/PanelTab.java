package com.spaeth.appbase.adds.vaadin.component;

import java.util.List;

import com.spaeth.appbase.adds.vaadin.ComponentWrapperHelper;
import com.spaeth.appbase.component.DetacheableComponent;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.VisualComponent.Measure;
import com.spaeth.appbase.component.VisualComponent.MeasureUnit;
import com.spaeth.appbase.component.api.IOrderedLayout.VerticalLayoutFlow;
import com.spaeth.appbase.component.api.IPanelTab;
import com.vaadin.terminal.ErrorMessage;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;

public class PanelTab extends Component<Tab> implements IPanelTab {

	private static final long serialVersionUID = 1L;

	private final OrderedLayout componentContainer;

	private StreamProvider icon;

	public PanelTab() {
		this.componentContainer = new VerticalLayout(VerticalLayoutFlow.TOP_DOWN);
		this.componentContainer.setWidth(new Measure(MeasureUnit.PERCENTAGE, 100));
		this.componentContainer.setHeight(new Measure(MeasureUnit.PERCENTAGE, 100));
	}

	public PanelTab(final OrderedLayout componentContainer) {
		this.componentContainer = componentContainer;
	}

	@Override
	protected Tab createDelegated() {
		return new LazyTab();
	}

	OrderedLayout getContent() {
		return componentContainer;
	}

	@Override
	void internalSetParent(final com.spaeth.appbase.component.Component parent) {
		if (!(parent instanceof TabbedPanel)) {
			throw new IllegalArgumentException(String.format("a tab could be added just on %s components",
					TabbedPanel.class.getName()));
		}

		TabSheet tabbedSheet = ComponentWrapperHelper.unwrap(parent, TabbedPanel.class).getDelegated();

		initialize(tabbedSheet.addTab(new com.vaadin.ui.VerticalLayout()));
		// adicionar todos components dentro do componente da tab

		super.internalSetParent(parent);
	}

	void initialize(final Tab tab) {
		Tab delegated = getDelegated();
		if (delegated instanceof LazyTab) {
			((LazyTab) delegated).replicateToTab(tab);
		} else {
			throw new IllegalStateException("tabs doesn't support redocking with vaadin implementation yet");
		}
		setDelegated(tab);
	}

	@Override
	public void setCaption(final String caption) {
		getDelegated().setCaption(caption);
	}

	@Override
	public String getCaption() {
		return getDelegated().getCaption();
	}

	@Override
	public void setClosable(final boolean closable) {
		getDelegated().setClosable(closable);
	}

	@Override
	public boolean isClosable() {
		return getDelegated().isClosable();
	}

	@Override
	public void setVisible(final boolean visible) {
		getDelegated().setVisible(visible);
		componentContainer.setVisible(visible);
	}

	@Override
	public boolean isVisible() {
		return getDelegated().isVisible();
	}

	@Override
	public void addComponent(final DetacheableComponent component) {
		componentContainer.addComponent(component);
	}

	@Override
	public void removeComponent(final com.spaeth.appbase.component.DetacheableComponent component) {
		componentContainer.removeComponent(component);
	}

	@Override
	public List<DetacheableComponent> getComponents() {
		return componentContainer.getComponents();
	}

	@Override
	public void setIcon(final StreamProvider iconStreamProvider) {
		this.icon = iconStreamProvider;
		if (iconStreamProvider != null) {
			getDelegated().setIcon(new StreamProviderResourceAdapter(iconStreamProvider));
		} else {
			getDelegated().setIcon(null);
		}
	}

	@Override
	public StreamProvider getIcon() {
		return icon;
	}

	static class LazyTab implements Tab {

		private static final long serialVersionUID = 1L;

		private boolean visible = true;
		private String caption = null;
		private boolean closable = true;
		private Resource icon = null;

		@Override
		public boolean isVisible() {
			return visible;
		}

		@Override
		public void setVisible(final boolean visible) {
			this.visible = visible;
		}

		@Override
		public boolean isClosable() {
			return closable;
		}

		@Override
		public void setClosable(final boolean closable) {
			this.closable = closable;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public void setEnabled(final boolean enabled) {
		}

		@Override
		public void setCaption(final String caption) {
			this.caption = caption;
		}

		@Override
		public String getCaption() {
			return caption;
		}

		@Override
		public Resource getIcon() {
			return icon;
		}

		@Override
		public void setIcon(final Resource icon) {
			this.icon = icon;
		}

		@Override
		public String getDescription() {
			return null;
		}

		@Override
		public void setDescription(final String description) {
		}

		@Override
		public void setComponentError(final ErrorMessage componentError) {
		}

		@Override
		public ErrorMessage getComponentError() {
			return null;
		}

		@Override
		public com.vaadin.ui.Component getComponent() {
			return null;
		}

		public void replicateToTab(final Tab tab) {
			tab.setCaption(caption);
			tab.setClosable(closable);
			tab.setVisible(visible);
			tab.setIcon(icon);
		}

		@Override
		public void setStyleName(final String styleName) {
		}

		@Override
		public String getStyleName() {
			return null;
		}

	}
}
