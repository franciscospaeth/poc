package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.api.IImage;
import com.spaeth.appbase.event.StreamProviderUpdateEvent;
import com.spaeth.appbase.event.StreamProviderUpdateListener;
import com.vaadin.ui.Embedded;

public class Image extends DetacheableComponent<Embedded> implements IImage, StreamProviderUpdateListener {

	private static final long serialVersionUID = 1L;

	private StreamProvider streamProvider;

	public Image() {
	}

	public Image(final StreamProvider streamProvider) {
		setStreamProvider(streamProvider);
	}

	@Override
	protected Embedded createDelegated() {
		return new Embedded();
	}

	@Override
	public void setStreamProvider(final StreamProvider streamProvider) {
		streamProvider.addUpdateListener(this);
		getDelegated().setSource(new StreamProviderResourceAdapter(streamProvider));
		this.streamProvider = streamProvider;
	}

	@Override
	public StreamProvider getStreamProvider() {
		return streamProvider;
	}

	@Override
	public void onUpdate(final StreamProviderUpdateEvent event) {
		getDelegated().setSource(new StreamProviderResourceAdapter(streamProvider));
	}

}
