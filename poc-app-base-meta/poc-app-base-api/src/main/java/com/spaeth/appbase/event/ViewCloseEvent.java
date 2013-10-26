package com.spaeth.appbase.event;

import java.util.EventObject;

public class ViewCloseEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ViewCloseEvent(final Object source) {
		super(source);
	}

	public void proceed() {
	}

}
