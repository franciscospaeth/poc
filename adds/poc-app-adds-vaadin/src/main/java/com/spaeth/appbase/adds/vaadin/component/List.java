package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.component.api.IList;
import com.vaadin.ui.ListSelect;

public class List extends VisualCollectionFieldComponent<ListSelect> implements IList {

	private static final long serialVersionUID = 1L;

	@Override
	protected ListSelect createDelegated() {
		ListSelect delegated = new ListSelect();
		delegated.setImmediate(true);
		return delegated;
	}

	@Override
	public void setVisibleRows(final int num) {
		getDelegated().setRows(num);
	}

	@Override
	public int getVisibleRows() {
		return getDelegated().getRows();
	}

	@Override
	public void setMultiSelect(final boolean multiSelect) {
		getDelegated().setMultiSelect(multiSelect);
	}

	@Override
	public boolean isMultiSelect() {
		return getDelegated().isMultiSelect();
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
