package com.spaeth.appbase.adds.vaadin;

import java.io.InputStream;

import com.spaeth.appbase.Application;
import com.spaeth.appbase.ViewPort;
import com.spaeth.appbase.event.ViewShowEvent;
import com.spaeth.appbase.model.StreamView;
import com.spaeth.appbase.model.View;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;

public class VaadinStreamViewPort implements ViewPort {

	private final Application<VaadinApplication> application;

	public VaadinStreamViewPort(final Application<VaadinApplication> application) {
		super();
		this.application = application;
	}

	@Override
	public boolean showView(final View<?> view) {
		if (!(view instanceof StreamView)) {
			return false;
		}
		return handle((StreamView) view);
	}

	private boolean handle(final StreamView streamView) {

		com.vaadin.Application appInstance = application.getInstance();

		appInstance.getMainWindow().open(new StreamResource(new StreamSource() {

			private static final long serialVersionUID = 1L;

			@Override
			public InputStream getStream() {
				return streamView.getContent();
			}

		}, streamView.getName(), appInstance));

		streamView.onShown(new ViewShowEvent(this));

		return true;
	}

	@Override
	public void viewClosed(final View<?> view) {
	}

}
