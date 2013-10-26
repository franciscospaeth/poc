package com.spaeth.appbase.adds.swing.component;

import javax.swing.JLabel;

import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.api.IImage;
import com.spaeth.appbase.event.StreamProviderUpdateEvent;
import com.spaeth.appbase.event.StreamProviderUpdateListener;

public class Image extends DetacheableComponent<JLabel> implements IImage, StreamProviderUpdateListener {

	private static final long serialVersionUID = 1L;

	private StreamProvider streamProvider = null;

	public Image() {
	}

	public Image(final StreamProvider streamProvider) {
		setStreamProvider(streamProvider);
	}

	@Override
	protected JLabel createDelegated() {
		return new JLabel();
	}

	@Override
	public void setStreamProvider(final StreamProvider streamProvider) {
		StreamProviderIconAdapter icon = new StreamProviderIconAdapter(this.streamProvider = streamProvider);
		getDelegated().setIcon(icon);
		setWidth(new Measure(MeasureUnit.PIXEL, icon.getIconWidth()));
		setHeight(new Measure(MeasureUnit.PIXEL, icon.getIconHeight()));
	}

	@Override
	public StreamProvider getStreamProvider() {
		return streamProvider;
	}

	@Override
	public void onUpdate(final StreamProviderUpdateEvent event) {
		getDelegated().repaint();
	}

}
