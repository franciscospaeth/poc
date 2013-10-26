package com.spaeth.appbase.adds.swing.component;

import javax.swing.JPanel;

import com.spaeth.appbase.adds.swing.component.customized.JFieldFrame;
import com.spaeth.appbase.component.api.IOptionGroup;

public class OptionGroup extends VisualCollectionFieldComponent<JFieldFrame<JPanel>> implements IOptionGroup {

	private static final long serialVersionUID = 1L;

	@Override
	protected JFieldFrame<JPanel> createDelegated() {
		return new JFieldFrame<JPanel>(new JPanel());
	}

	@Override
	public void setMultiSelect(final boolean multiSelect) {
	}

	@Override
	public boolean isMultiSelect() {
		return false;
	}

	@Override
	public void setCaptionProperty(final String propertyName) {
	}

	@Override
	public String getCaptionProperty() {
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
		System.err.println("OPTION GROUP IS NOT BEING UPDATE");
	}

}
