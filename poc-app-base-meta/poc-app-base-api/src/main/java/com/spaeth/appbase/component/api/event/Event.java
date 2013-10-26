package com.spaeth.appbase.component.api.event;

import java.util.EventObject;

public class Event extends EventObject {

	private static final long serialVersionUID = 1L;

	public Event(final Object source) {
		super(source);
	}

}
