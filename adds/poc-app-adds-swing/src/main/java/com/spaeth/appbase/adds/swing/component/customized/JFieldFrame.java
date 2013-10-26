package com.spaeth.appbase.adds.swing.component.customized;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JFieldFrame<ComponentType extends JComponent> extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JLabel caption = new JLabel();
	private final ComponentType component;

	public JFieldFrame(final ComponentType framedComponent) {
		super(new BorderLayout());
		add(caption, BorderLayout.NORTH);
		add(component = framedComponent, BorderLayout.CENTER);
	}

	public void setCaption(final String caption) {
		this.caption.setText(caption);
	}

	public String getCaption() {
		return this.caption.getText();
	}

	public ComponentType getHoldedComponent() {
		return component;
	}

}
