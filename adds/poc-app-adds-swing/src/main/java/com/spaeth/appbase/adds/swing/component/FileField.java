package com.spaeth.appbase.adds.swing.component;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.spaeth.appbase.adds.swing.component.customized.JFieldFrame;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.api.IFileField;
import com.spaeth.appbase.core.datasource.DataSource;

public class FileField extends VisualFieldComponent<JFieldFrame<JPanel>> implements IFileField {

	private static final long serialVersionUID = 1L;

	@Override
	protected JFieldFrame<JPanel> createDelegated() {
		JPanel packed = new JPanel();
		packed.add(new JLabel("Missing upload component"));
		return new JFieldFrame<JPanel>(packed);
	}

	@Override
	public void setMessageIcon(final StreamProvider streamProvider) {
	}

	@Override
	public StreamProvider getMessageIcon() {
		return null;
	}

	@Override
	public void setNameDataSource(final DataSource dataSource) {
	}

	@Override
	public DataSource getNameDataSource() {
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
		System.err.println("FILE FIELD IS NOT BEING UPDATE");
	}

}