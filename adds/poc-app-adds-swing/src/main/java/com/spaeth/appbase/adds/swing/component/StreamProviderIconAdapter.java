package com.spaeth.appbase.adds.swing.component;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Icon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.component.StreamProvider;

public class StreamProviderIconAdapter implements Icon {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final StreamProvider streamProvider;

	public StreamProviderIconAdapter(final StreamProvider streamProvider) {
		this.streamProvider = streamProvider;
	}

	@Override
	public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
		try {
			BufferedImage im = ImageIO.read(streamProvider.getStream());
			g.drawImage(im, x, y, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getIconWidth() {
		try {
			BufferedImage im = ImageIO.read(streamProvider.getStream());
			return im.getWidth();
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return 0;
		}
	}

	@Override
	public int getIconHeight() {
		try {
			BufferedImage im = ImageIO.read(streamProvider.getStream());
			return im.getHeight();
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return 0;
		}
	}

}
