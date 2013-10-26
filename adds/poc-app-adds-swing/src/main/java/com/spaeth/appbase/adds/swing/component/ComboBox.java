package com.spaeth.appbase.adds.swing.component;

import javax.swing.JComboBox;

import com.spaeth.appbase.adds.swing.component.customized.JFieldFrame;
import com.spaeth.appbase.component.api.IComboBox;

public class ComboBox extends VisualCollectionFieldComponent<JFieldFrame<JComboBox>> implements IComboBox {

	private static final long serialVersionUID = 1L;

	@Override
	protected JFieldFrame<JComboBox> createDelegated() {
		JComboBox delegated = new JComboBox();
		return new JFieldFrame<JComboBox>(delegated);
	}

	@Override
	public void setCaptionProperty(final String propertyName) {
	}

	@Override
	public String getCaptionProperty() {
		return null;
	}

	@Override
	public String getCaption() {
		return getDelegated().getCaption();
	}

	@Override
	public void setCaption(final String caption) {
		getDelegated().setCaption(caption);
	}

	@Override
	protected void updateView(final Object value) {
		System.err.println("COMBOBOX IS NOT BEING UPDATE");
	}

}
