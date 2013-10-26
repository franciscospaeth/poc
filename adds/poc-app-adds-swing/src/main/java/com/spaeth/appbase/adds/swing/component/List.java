package com.spaeth.appbase.adds.swing.component;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import com.spaeth.appbase.adds.swing.component.customized.JFieldFrame;
import com.spaeth.appbase.component.api.IList;

public class List extends VisualCollectionFieldComponent<JFieldFrame<JList>> implements IList {

	private static final long serialVersionUID = 1L;

	@Override
	protected JFieldFrame<JList> createDelegated() {
		JList delegated = new JList();
		return new JFieldFrame<JList>(delegated);
	}

	@Override
	public void setVisibleRows(final int num) {
		getDelegated().getHoldedComponent().setVisibleRowCount(num);
	}

	@Override
	public int getVisibleRows() {
		return getDelegated().getHoldedComponent().getVisibleRowCount();
	}

	@Override
	public void setMultiSelect(final boolean multiSelect) {
		getDelegated().getHoldedComponent().setSelectionMode(
				multiSelect ? ListSelectionModel.MULTIPLE_INTERVAL_SELECTION : ListSelectionModel.SINGLE_SELECTION);
	}

	@Override
	public boolean isMultiSelect() {
		return getDelegated().getHoldedComponent().getSelectionMode() == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
	}

	@Override
	public void setCaptionProperty(final String propertyName) {
		// TODO
	}

	@Override
	public String getCaptionProperty() {
		// TODO
		return null;
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
		System.err.println("LIST IS NOT BEING UPDATE");
	}

}
