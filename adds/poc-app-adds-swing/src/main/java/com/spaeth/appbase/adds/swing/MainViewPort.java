package com.spaeth.appbase.adds.swing;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.spaeth.appbase.Application;
import com.spaeth.appbase.ViewPort;
import com.spaeth.appbase.adds.swing.component.ComponentContainer;
import com.spaeth.appbase.adds.swing.component.StreamProviderIconAdapter;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.event.ViewShowEvent;
import com.spaeth.appbase.model.IconedView;
import com.spaeth.appbase.model.MainView;
import com.spaeth.appbase.model.TitledView;
import com.spaeth.appbase.model.View;
import com.spaeth.appbase.model.ViewCloseHandler;

public class MainViewPort implements ViewPort {

	private final Application<SwingApplication> application;

	public MainViewPort(final Application<SwingApplication> application) {
		this.application = application;
	}

	@Override
	public boolean showView(final View<?> view) {
		if (!(view instanceof MainView)) {
			return false;
		}

		handle(view);

		onShowView(view, this.application);

		return true;
	}

	@SuppressWarnings("unchecked")
	private void handle(final View<?> view) {
		com.spaeth.appbase.component.ComponentContainer cc = (com.spaeth.appbase.component.ComponentContainer) view.getContent();
		ComponentContainer<JPanel> unwrapped = ComponentWrapperHelper.unwrap(cc, ComponentContainer.class);
		application.getInstance().showOnMainFrame(unwrapped);

		JFrame mainFrame = application.getInstance().getMainFrame();

		if (view instanceof TitledView) {
			mainFrame.setTitle(((TitledView) view).getTitle());
		}

		if (view instanceof IconedView) {
			StreamProvider icon = ((IconedView) view).getIcon();
			if (icon != null) {
				StreamProviderIconAdapter sicon = new StreamProviderIconAdapter(icon);
				BufferedImage image = createImageFromIcon(sicon);
				mainFrame.setIconImage(image);
			}
		}

		mainFrame.setVisible(true);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}

	protected BufferedImage createImageFromIcon(final StreamProviderIconAdapter sicon) {
		int w = sicon.getIconWidth();
		int h = sicon.getIconHeight();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(w, h);
		Graphics2D g = image.createGraphics();
		sicon.paintIcon(null, g, 0, 0);
		g.dispose();
		return image;
	}

	private void onShowView(final View<?> view, final Application<SwingApplication> application2) {
		view.onShown(new ViewShowEvent(this, new ViewCloseHandler() {
			@Override
			public void execute() {
				application.getInstance().getMainFrame().dispose();
			}
		}));
	}

	@Override
	public void viewClosed(final View<?> view) {
		// TODO check sanity
	}
}
