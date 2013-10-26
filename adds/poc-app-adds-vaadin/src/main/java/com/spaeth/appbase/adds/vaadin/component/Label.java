package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.component.SizeDefinition;
import com.spaeth.appbase.component.api.ILabel;

public class Label extends VisualFieldComponent<com.vaadin.ui.Label> implements ILabel {

	private static final long serialVersionUID = 1L;

	private final AbstractLabel abstractLabel;

	public Label() {
		abstractLabel = new AbstractLabel(this);
	}

	@Override
	protected com.vaadin.ui.Label createDelegated() {
		return new com.vaadin.ui.Label();
	}

	@Override
	public void setTextSizeDefinition(final SizeDefinition size) {
		abstractLabel.setTextSizeDefinition(size);
	}

	@Override
	public SizeDefinition getTextSizeDefinition() {
		return abstractLabel.getTextSizeDefinition();
	}

}
