package com.spaeth.appbase.component.stream;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.event.StreamProviderUpdateEvent;
import com.spaeth.appbase.event.StreamProviderUpdateListener;

public abstract class AbstractStreamProvider implements StreamProvider {

	private final List<StreamProviderUpdateListener> updateListeners = new ArrayList<StreamProviderUpdateListener>();

	@Override
	public final InputStream getStream() {
		InputStream input = createInputStream();
		if (input == null) {
			return null;
		}
		return new BufferedInputStream(input);
	}

	@Override
	public void addUpdateListener(final StreamProviderUpdateListener listener) {
		updateListeners.add(listener);
	}

	@Override
	public void removeUpdateListener(final StreamProviderUpdateListener listener) {
		updateListeners.remove(listener);
	}

	protected void fireUpdateListeners() {
		for (StreamProviderUpdateListener ul : updateListeners) {
			ul.onUpdate(new StreamProviderUpdateEvent(this));
		}
	}

	protected abstract InputStream createInputStream();

}
