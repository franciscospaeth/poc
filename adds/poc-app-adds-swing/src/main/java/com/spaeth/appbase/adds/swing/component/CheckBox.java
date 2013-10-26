package com.spaeth.appbase.adds.swing.component;

import javax.swing.JCheckBox;

import com.spaeth.appbase.component.api.ICheckBox;

public class CheckBox extends VisualFieldComponent<JCheckBox> implements ICheckBox {

	private static final long serialVersionUID = 1L;

	@Override
	protected JCheckBox createDelegated() {
		JCheckBox delegated = new JCheckBox();
		return delegated;
	}

	@Override
	public void setCaption(final String caption) {
		getDelegated().setText(caption);
	}

	@Override
	public String getCaption() {
		return getDelegated().getText();
	}

	@Override
	protected void updateView(final Object value) {
		getDelegated().setSelected(Boolean.valueOf(String.valueOf(value)));
	}

}
