package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.component.api.ICheckBox;

public class CheckBox extends VisualFieldComponent<com.vaadin.ui.CheckBox> implements ICheckBox {

	private static final long serialVersionUID = 1L;

	@Override
	protected com.vaadin.ui.CheckBox createDelegated() {
		com.vaadin.ui.CheckBox delegated = new com.vaadin.ui.CheckBox();
		delegated.setImmediate(true);
		return delegated;
	}
}
