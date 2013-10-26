package com.spaeth.appbase.adds.vaadin;

import com.spaeth.appbase.Application;
import com.spaeth.appbase.ViewPort;
import com.spaeth.appbase.adds.vaadin.component.StreamProviderResourceAdapter;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.event.ViewShowEvent;
import com.spaeth.appbase.model.IconedView;
import com.spaeth.appbase.model.MainView;
import com.spaeth.appbase.model.TitledView;
import com.spaeth.appbase.model.View;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Window;

public class MainViewPort implements ViewPort {

	private final Application<? extends com.vaadin.Application> application;

	public MainViewPort(final Application<VaadinApplication> application) {
		super();
		this.application = application;
	}

	@Override
	public boolean showView(final View<?> view) {
		if (!(view instanceof MainView)) {
			return false;
		}

		handle(view);

		onShowView(view, this.application);

		return true;
	}

	private void handle(final View<?> view) {

		com.spaeth.appbase.component.ComponentContainer cc = (com.spaeth.appbase.component.ComponentContainer) view.getContent();

		@SuppressWarnings("unchecked")
		com.spaeth.appbase.adds.vaadin.component.ComponentContainer<AbstractComponentContainer> unwrapped = ComponentWrapperHelper.unwrap(
				cc, com.spaeth.appbase.adds.vaadin.component.ComponentContainer.class);
		final ComponentContainer content = unwrapped.getDelegated();

		Window window = new Window();
		window.setContent(content);

		if (view instanceof TitledView) {
			window.setCaption(((TitledView) view).getTitle());
		}

		if (view instanceof IconedView) {
			StreamProvider icon = ((IconedView) view).getIcon();
			if (icon != null) {
				window.setIcon(new StreamProviderResourceAdapter(icon));
			}
		}

		final Window oldWindow = this.application.getInstance().getMainWindow();

		if (oldWindow != null) {
			this.application.getInstance().removeWindow(oldWindow);
		}

		this.application.getInstance().setMainWindow(window);
	}

	protected void onShowView(final View<?> view, final Application<? extends com.vaadin.Application> application) {
		view.onShown(new ViewShowEvent(this));
	}

	@Override
	public void viewClosed(final View<?> view) {
	}

}
