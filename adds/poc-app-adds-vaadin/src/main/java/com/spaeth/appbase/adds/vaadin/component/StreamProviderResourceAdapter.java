package com.spaeth.appbase.adds.vaadin.component;

import java.io.BufferedInputStream;
import java.io.InputStream;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

import com.spaeth.appbase.adds.web.PocAppServletImpl;
import com.spaeth.appbase.component.StreamProvider;
import com.vaadin.terminal.StreamResource;

public class StreamProviderResourceAdapter extends StreamResource {

	private static final long serialVersionUID = 1L;

	public StreamProviderResourceAdapter(final StreamProvider streamProvider) {
		super(new StreamSourceAdapter(streamProvider), "file." + Math.random() + ".tmp", PocAppServletImpl
				.getApplication());
		setCacheTime(0);
	}

	@Override
	public String getMIMEType() {
		try {
			byte[] data = new byte[20];
			InputStream stream = ((StreamSourceAdapter) getStreamSource()).getStream();
			stream.mark(20);
			stream.read(data);
			stream.reset();
			MagicMatch magicMatch = Magic.getMagicMatch(data);
			return magicMatch.getMimeType();
		} catch (Exception e) {
			return "/";
		}
	}

	static class StreamSourceAdapter implements StreamSource {

		private static final long serialVersionUID = 1L;

		private final StreamProvider streamProvider;

		public StreamSourceAdapter(final StreamProvider streamProvider) {
			this.streamProvider = streamProvider;
		}

		@Override
		public InputStream getStream() {
			InputStream result = streamProvider.getStream();
			if (result instanceof BufferedInputStream) {
				return result;
			}
			return new BufferedInputStream(result, 20);
		}

	}

}
