package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.component.api.IPasswordField;

public class PasswordField extends VisualFieldComponent<com.vaadin.ui.PasswordField> implements IPasswordField {

	private static final long serialVersionUID = 1L;

	@Override
	protected com.vaadin.ui.PasswordField createDelegated() {
		com.vaadin.ui.PasswordField delegated = new com.vaadin.ui.PasswordField();
		delegated.setImmediate(true);
		delegated.setNullRepresentation("");
		return delegated;
	}
}
