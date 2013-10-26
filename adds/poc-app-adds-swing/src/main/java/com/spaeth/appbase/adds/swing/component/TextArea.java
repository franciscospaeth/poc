package com.spaeth.appbase.adds.swing.component;

import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.lang3.ObjectUtils;

import com.spaeth.appbase.adds.swing.component.customized.JFieldFrame;
import com.spaeth.appbase.component.api.ITextArea;

public class TextArea extends VisualFieldComponent<JFieldFrame<JScrollPane>> implements ITextArea {

	private static final long serialVersionUID = 1L;

	private JTextArea textArea;

	@Override
	protected JFieldFrame<JScrollPane> createDelegated() {
		textArea = new JTextArea();
		textArea.setMargin(new Insets(3, 3, 3, 3));
		return new JFieldFrame<JScrollPane>(new JScrollPane(textArea));
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
		textArea.setText(ObjectUtils.toString(value));
	}

}
