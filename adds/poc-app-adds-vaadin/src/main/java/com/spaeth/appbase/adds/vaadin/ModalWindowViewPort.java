package com.spaeth.appbase.adds.vaadin;

import com.spaeth.appbase.Application;
import com.spaeth.appbase.ViewPort;
import com.spaeth.appbase.adds.vaadin.component.StreamProviderResourceAdapter;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.event.ViewShowEvent;
import com.spaeth.appbase.model.ModalView;
import com.spaeth.appbase.model.View;
import com.spaeth.appbase.model.ViewCloseHandler;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Window;

public class ModalWindowViewPort implements ViewPort {

	private final Application<? extends com.vaadin.Application> application;

	public ModalWindowViewPort(final Application<? extends com.vaadin.Application> application) {
		this.application = application;
	}

	@Override
	public boolean showView(final View<?> view) {
		if (!(view instanceof ModalView)) {
			return false;
		}

		ModalView modalView = (ModalView) view;

		final ComponentContainer content = unwrap(view);

		final Window window = getViewPortComponentContainer(view);
		window.setContent(content);
		window.setWidth(content.getWidth(), content.getWidthUnits());
		window.setHeight(content.getHeight(), content.getHeightUnits());
		window.setCaption(modalView.getTitle());
		window.setClosable(modalView.isClosable());
		content.setSizeFull();

		StreamProvider icon = modalView.getIcon();
		if (icon != null) {
			window.setIcon(new StreamProviderResourceAdapter(icon));
		} else {
			window.setIcon(null);
		}

		view.onShown(new ViewShowEvent(this, new ViewCloseHandler() {
			@Override
			public void execute() {
				window.getParent().removeWindow(window);
			}
		}));

		return true;
	}

	protected ComponentContainer unwrap(final View<?> view) {
		com.spaeth.appbase.component.ComponentContainer cc = (com.spaeth.appbase.component.ComponentContainer) view.getContent();

		@SuppressWarnings("unchecked")
		com.spaeth.appbase.adds.vaadin.component.ComponentContainer<AbstractComponentContainer> unwrapped = ComponentWrapperHelper.unwrap(
				cc, com.spaeth.appbase.adds.vaadin.component.ComponentContainer.class);
		final ComponentContainer content = unwrapped.getDelegated();
		return content;
	}

	protected Window getViewPortComponentContainer(final View<?> view) {
		final Window window = new Window();
		window.setModal(true);
		window.setResizable(false);
		this.application.getInstance().getMainWindow().addWindow(window);
		return window;
	}

	@Override
	public void viewClosed(final View<?> view) {
	}

}
