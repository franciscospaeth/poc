package com.spaeth.appbase.adds.swing.component;

import javax.swing.JPasswordField;

import org.apache.commons.lang3.ObjectUtils;

import com.spaeth.appbase.adds.swing.component.customized.JFieldFrame;
import com.spaeth.appbase.component.api.IPasswordField;

public class PasswordField extends VisualFieldComponent<JFieldFrame<JPasswordField>> implements IPasswordField {

	private static final long serialVersionUID = 1L;

	@Override
	protected JFieldFrame<JPasswordField> createDelegated() {
		JPasswordField delegated = new JPasswordField();
		return new JFieldFrame<JPasswordField>(delegated);
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
