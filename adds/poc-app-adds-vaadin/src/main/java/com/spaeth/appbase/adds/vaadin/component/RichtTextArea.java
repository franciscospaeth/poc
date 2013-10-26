package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.component.api.IRichTextArea;
import com.vaadin.ui.RichTextArea;

public class RichtTextArea extends VisualFieldComponent<com.vaadin.ui.RichTextArea> implements IRichTextArea {

	private static final long serialVersionUID = 1L;

	@Override
	protected com.vaadin.ui.RichTextArea createDelegated() {
		RichTextArea delegated = new com.vaadin.ui.RichTextArea();
		delegated.setImmediate(true);
		delegated.setNullRepresentation("");
		return delegated;
	}

}
