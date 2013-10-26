package com.spaeth.appbase.adds.swing;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.spaeth.appbase.Application;
import com.spaeth.appbase.ViewPort;
import com.spaeth.appbase.adds.swing.component.ComponentContainer;
import com.spaeth.appbase.model.ModalView;
import com.spaeth.appbase.model.TitledView;
import com.spaeth.appbase.model.View;

public class ModalWindowViewPort implements ViewPort {

	public ModalWindowViewPort(final Application<SwingApplication> application) {
	}

	@Override
	public boolean showView(final View<?> view) {
		if (!(view instanceof ModalView)) {
			return false;
		}
		JDialog dialog = new JDialog();
		dialog.setVisible(true);
		if (view instanceof TitledView) {
			dialog.setTitle(((TitledView) view).getTitle());
		}

		com.spaeth.appbase.component.ComponentContainer cc = (com.spaeth.appbase.component.ComponentContainer) view.getContent();
		@SuppressWarnings("unchecked")
		ComponentContainer<JPanel> unwrapped = ComponentWrapperHelper.unwrap(cc, ComponentContainer.class);

		dialog.setLayout(new BorderLayout());
		dialog.add(unwrapped.getDelegated(), BorderLayout.CENTER);

		dialog.setSize(unwrapped.getWidth().getValue(), unwrapped.getHeight().getValue());
		dialog.pack();

		dialog.setResizable(false);

		return true;
	}

	@Override
	public void viewClosed(final View<?> view) {
		// TODO check sanity
	}
}
