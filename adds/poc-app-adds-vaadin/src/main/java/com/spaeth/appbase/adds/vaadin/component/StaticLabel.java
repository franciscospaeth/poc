package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.component.SizeDefinition;
import com.spaeth.appbase.component.api.IStaticLabel;
import com.vaadin.ui.Label;

public class StaticLabel extends DetacheableComponent<Label> implements IStaticLabel {

	private static final long serialVersionUID = 1L;

	private final AbstractLabel abstractLabel;

	public StaticLabel() {
		abstractLabel = new AbstractLabel(this);
	}

	@Override
	protected Label createDelegated() {
		return new Label();
	}

	@Override
	public void setText(final String text) {
		getDelegated().setValue(text);
	}

	@Override
	public String getText() {
		return String.valueOf(getDelegated().getValue());
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
