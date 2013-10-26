package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.adds.vaadin.component.mediator.VaadinDataSourcePropertyAdapter;
import com.spaeth.appbase.adds.vaadin.component.mediator.VaadinDataSourceValidatorAdapter;
import com.spaeth.appbase.component.ComponentContainer;
import com.spaeth.appbase.component.FieldComponent;
import com.spaeth.appbase.core.datasource.DataSource;
import com.vaadin.data.Property;
import com.vaadin.data.Property.Viewer;
import com.vaadin.data.Validatable;

public abstract class VisualFieldComponent<ComponentClass extends com.vaadin.ui.Component> extends
		DetacheableComponent<ComponentClass> implements FieldComponent {

	private static final long serialVersionUID = 1L;

	protected VaadinDataSourceValidatorAdapter validator;
	protected final VaadinDataSourcePropertyAdapter property;

	public VisualFieldComponent() {
		property = createDataSourcePropertyAdapter();
		getDelegated(Viewer.class).setPropertyDataSource(property);
		if (isValidatorNeeded()) {
			validator = new VaadinDataSourceValidatorAdapter();
			validator.setRepainOnValidate(getDelegated());
			getDelegated(Validatable.class).addValidator(validator);
		}
	}

	protected boolean isValidatorNeeded() {
		return getDelegated() instanceof Validatable && true;
	}

	@Override
	public void setDataSource(final DataSource dataSource) {
		if (this.property.getDataSource() != null) {
			if (validator != null) {
				this.property.getDataSource().removeDataSourceValidationListener(validator);
				this.property.getDataSource().removeDataSourceValueChangeListener(validator);
			}
			this.property.getDataSource().removeDataSourceValueChangeListener(property);
			this.property.getDataSource().removeDataSourceAccessPolicyChangeListener(property);
		}
		this.property.setDataSource(dataSource);
		if (dataSource != null) {
			dataSource.addDataSourceAccessPolicyChangeListener(property);
			dataSource.addDataSourceValueChangeListener(property);
			if (validator != null) {
				dataSource.addDataSourceValueChangeListener(validator);
				dataSource.addDataSourceValidationListener(validator);
			}
		}
	}

	protected VaadinDataSourcePropertyAdapter createDataSourcePropertyAdapter() {
		return new VaadinDataSourcePropertyAdapter();
	}

	@Override
	public DataSource getDataSource() {
		return property.getDataSource();
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
	public Object getValue() {
		return getDelegated(Property.class).getValue();
	}

	@Override
	public void setValue(final Object value) {
		getDelegated(Property.class).setValue(value);
	}
	
	@Override
	public void setParent(ComponentContainer componentContainer) {
		super.setParent(componentContainer);
	}

}
