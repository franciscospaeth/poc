package com.spaeth.appbase.event;

import java.util.EventObject;

import com.spaeth.appbase.ViewPort;
import com.spaeth.appbase.model.ViewCloseHandler;

public class ViewShowEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	private ViewCloseHandler viewCloseHandler = ViewCloseHandler.NOT_CLOSABLE;

	public ViewShowEvent(final ViewPort viewPort) {
		super(viewPort);
	}

	public ViewShowEvent(final ViewPort viewPort, final ViewCloseHandler viewCloseHandler) {
		super(viewPort);
		this.viewCloseHandler = viewCloseHandler;
	}

	@Override
	public ViewPort getSource() {
		return (ViewPort) super.getSource();
	}

	public ViewCloseHandler getCloseHandler() {
		return viewCloseHandler;
	}

}
