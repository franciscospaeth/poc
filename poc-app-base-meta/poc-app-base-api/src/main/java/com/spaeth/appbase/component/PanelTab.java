package com.spaeth.appbase.component;

import java.util.List;

import com.spaeth.appbase.component.api.ILayout;
import com.spaeth.appbase.component.api.IPanelTab;

public class PanelTab extends AbstractComponent<IPanelTab> implements IPanelTab {

	private static final long serialVersionUID = 1L;

	public PanelTab() {
	}

	public PanelTab(final ILayout layout) {
		super(layout);
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
	public boolean isVisible() {
		return getDelegated().isVisible();
	}

	@Override
	public void setClosable(final boolean closable) {
		getDelegated().setClosable(closable);
	}

	@Override
	public void setVisible(final boolean visible) {
		getDelegated().setVisible(visible);
	}

	@Override
	public boolean isClosable() {
		return getDelegated().isClosable();
	}

	@Override
	public void addComponent(final DetacheableComponent component) {
		getDelegated().addComponent(component);
	}

	@Override
	public void removeComponent(final DetacheableComponent component) {
		getDelegated().removeComponent(component);
	}

	@Override
	public List<DetacheableComponent> getComponents() {
		return getDelegated().getComponents();
	}

	@Override
	public StreamProvider getIcon() {
		return getDelegated().getIcon();
	}

	@Override
	public void setIcon(final StreamProvider streamProvider) {
		getDelegated().setIcon(streamProvider);
	}

}
