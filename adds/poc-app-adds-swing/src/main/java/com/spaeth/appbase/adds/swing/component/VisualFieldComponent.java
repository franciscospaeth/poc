package com.spaeth.appbase.adds.swing.component;

import javax.swing.JComponent;

import com.spaeth.appbase.component.FieldComponent;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;

public abstract class VisualFieldComponent<ComponentClass extends JComponent> extends DetacheableComponent<ComponentClass> implements
		FieldComponent, ValueChangeListener {

	private static final long serialVersionUID = 1L;
	private DataSource dataSource;
	private boolean feedingBack = false;

	public VisualFieldComponent() {
	}

	protected boolean isValidatorNeeded() {
		return false;
	}

	@Override
	public void setDataSource(final DataSource dataSource) {
		if (dataSource != null) {
			this.dataSource.removeDataSourceValueChangeListener(this);
		}

		this.dataSource = dataSource;

		if (dataSource != null) {
			dataSource.addDataSourceValueChangeListener(this);
		}

	}

	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

	@Override
	public Object getValue() {
		if (dataSource == null) {
			return null;
		}
		return dataSource.get();
	}

	@Override
	public void setValue(final Object value) {
		if (dataSource == null) {
			return;
		}
		if (!feedingBack) {
			try {
				feedingBack = true;
				dataSource.setModel(value);
			} finally {
				feedingBack = false;
			}
		}
	}

	@Override
	public void onChange(final DataSourceValueChangedEvent event) {
		if (!feedingBack) {
			try {
				feedingBack = true;
				internalOnChange(event);
			} finally {
				feedingBack = false;
			}
		}
	}

	protected void internalOnChange(final DataSourceValueChangedEvent event) {
		updateView(event.getNewValue());
	}

	protected abstract void updateView(Object value);

}
