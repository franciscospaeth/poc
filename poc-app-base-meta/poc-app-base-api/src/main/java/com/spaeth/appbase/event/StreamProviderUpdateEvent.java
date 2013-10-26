package com.spaeth.appbase.event;

import java.util.EventObject;

import com.spaeth.appbase.component.StreamProvider;

public class StreamProviderUpdateEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public StreamProviderUpdateEvent(StreamProvider source) {
		super(source);
	}

	public StreamProvider getStreamProvider() {
		return (StreamProvider) getSource();
	}
	
}
