package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.component.api.IComboBox;
import com.vaadin.ui.NativeSelect;

public class ComboBox extends VisualCollectionFieldComponent<NativeSelect> implements IComboBox {

	private static final long serialVersionUID = 1L;

	@Override
	protected NativeSelect createDelegated() {
		NativeSelect delegated = new NativeSelect();
		delegated.setImmediate(true);
		delegated.setNullSelectionAllowed(false);
		return delegated;
	}

	@Override
	public void setCaptionProperty(final String propertyName) {
		getDelegated().setItemCaptionPropertyId(propertyName);
	}

	@Override
	public String getCaptionProperty() {
		Object propertyName = getDelegated().getItemCaptionPropertyId();
		if (propertyName == null) {
			return null;
		}
		return String.valueOf(propertyName);
	}
	
}
