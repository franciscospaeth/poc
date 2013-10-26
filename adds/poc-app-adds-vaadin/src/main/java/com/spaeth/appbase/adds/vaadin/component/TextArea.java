package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.component.api.ITextArea;

public class TextArea extends VisualFieldComponent<com.vaadin.ui.TextArea> implements ITextArea {

	private static final long serialVersionUID = 1L;

	@Override
	protected com.vaadin.ui.TextArea createDelegated() {
		com.vaadin.ui.TextArea delegated = new com.vaadin.ui.TextArea();
		delegated.setImmediate(true);
		delegated.setNullRepresentation("");
		return delegated;
	}

}
