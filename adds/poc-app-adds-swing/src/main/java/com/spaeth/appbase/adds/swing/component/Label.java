package com.spaeth.appbase.adds.swing.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

import org.apache.commons.lang3.ObjectUtils;

import com.spaeth.appbase.adds.swing.component.customized.JFieldFrame;
import com.spaeth.appbase.component.SizeDefinition;
import com.spaeth.appbase.component.api.ILabel;

public class Label extends VisualFieldComponent<JFieldFrame<JTextArea>> implements ILabel {

	private static final long serialVersionUID = 1L;
	private SizeDefinition size = SizeDefinition.NORMAL;

	public Label() {
		setTextSizeDefinition(size);
	}

	@Override
	protected JFieldFrame<JTextArea> createDelegated() {
		JTextArea textArea = new JTextArea();
		textArea.setOpaque(false);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(new Color(0, 0, 0, 0));
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		return new JFieldFrame<JTextArea>(textArea);
	}

	@Override
	public void setTextSizeDefinition(final SizeDefinition size) {
		Font f = getDelegated().getFont();
		float pointsSize = 10;
		switch (this.size = size) {
		case HUGE:
			pointsSize = 20;
			break;
		case BIG:
			pointsSize = 16;
			break;
		case NORMAL:
			pointsSize = 12;
			break;
		case SMALL:
			pointsSize = 10;
		}
		f.deriveFont(pointsSize);
		getDelegated().setFont(f);
	}

	@Override
	public SizeDefinition getTextSizeDefinition() {
		return size;
	}

	@Override
	public void setCaption(final String caption) {
		getDelegated().setCaption(caption);
	}

	@Override
	public String getCaption() {
		return getDelegated().getCaption();
	}

	@Override
	protected void updateView(final Object value) {
		getDelegated().getHoldedComponent().setText(ObjectUtils.toString(value));
	}

}
