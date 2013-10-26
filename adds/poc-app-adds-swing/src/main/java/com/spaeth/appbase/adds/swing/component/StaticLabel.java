package com.spaeth.appbase.adds.swing.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

import com.spaeth.appbase.component.SizeDefinition;
import com.spaeth.appbase.component.api.IStaticLabel;

public class StaticLabel extends DetacheableComponent<JTextArea> implements IStaticLabel {

	private static final long serialVersionUID = 1L;

	private SizeDefinition size = SizeDefinition.NORMAL;

	public StaticLabel() {
		setTextSizeDefinition(size);
	}

	@Override
	protected JTextArea createDelegated() {
		JTextArea textArea = new JTextArea();
		textArea.setOpaque(false);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(new Color(0, 0, 0, 0));
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		return textArea;
	}

	@Override
	public void setText(final String text) {
		getDelegated().setText(text);
	}

	@Override
	public String getText() {
		return String.valueOf(getDelegated().getText());
	}

	@Override
	public void setTextSizeDefinition(final SizeDefinition size) {
		Font f = getDelegated().getFont();
		switch (this.size = size) {
		case HUGE:
			f = f.deriveFont(22f).deriveFont(Font.BOLD);
			break;
		case BIG:
			f = f.deriveFont(18f).deriveFont(Font.BOLD);
			break;
		case NORMAL:
			f = f.deriveFont(16f);
			break;
		case SMALL:
			f = f.deriveFont(12f);
		}
		getDelegated().setFont(f);
	}

	@Override
	public SizeDefinition getTextSizeDefinition() {
		return size;
	}

}
