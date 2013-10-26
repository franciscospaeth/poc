package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.adds.vaadin.component.mediator.VaadinDataSourcePropertyAdapter;
import com.spaeth.appbase.component.api.IOptionGroup;

public class OptionGroup extends VisualCollectionFieldComponent<com.vaadin.ui.OptionGroup> implements IOptionGroup {

	private static final long serialVersionUID = 1L;

	@Override
	protected com.vaadin.ui.OptionGroup createDelegated() {
		com.vaadin.ui.OptionGroup delegated = new com.vaadin.ui.OptionGroup();
		delegated.setImmediate(true);
		return delegated;
	}

	@Override
	public boolean isMultiSelect() {
		return getDelegated().isMultiSelect();
	}

	@Override
	public void setMultiSelect(final boolean multiSelect) {
		getDelegated().setMultiSelect(multiSelect);
	}

	@Override
	protected VaadinDataSourcePropertyAdapter createDataSourcePropertyAdapter() {
		return new VaadinDataSourcePropertyAdapter(null);
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
