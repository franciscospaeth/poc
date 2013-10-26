package com.spaeth.appbase.adds.vaadin.component.mediator;

import java.util.Collection;
import java.util.Collections;

import com.spaeth.appbase.core.datasource.event.ValueChangeListener;
import com.spaeth.appbase.core.datasource.event.ValidationListener;
import com.spaeth.appbase.core.datasource.validation.ConstraintViolation;
import com.vaadin.data.Validator;
import com.vaadin.ui.Component;

public class VaadinDataSourceValidatorAdapter implements Validator, ValidationListener, ValueChangeListener {

	private static final long serialVersionUID = 1L;
	private Collection<ConstraintViolation> validationConstraints = Collections.emptySet();
	private Component repainOnValidate = null;

	public VaadinDataSourceValidatorAdapter() {
		super();
	}

	@Override
	public void validate(final Object value) throws InvalidValueException {
		if (!validationConstraints.isEmpty()) {
			throw new Validator.InvalidValueException(validationConstraints.iterator().next().getMessage());
		}
	}

	@Override
	public boolean isValid(final Object value) {
		return validationConstraints.isEmpty();
	}

	@Override
	public void onValidate(final ValidationEvent event) {
		validationConstraints = event.getViolationConstraints();
		if (repainOnValidate != null) {
			repainOnValidate.requestRepaint();
		}
	}

	@Override
	public void onChange(final DataSourceValueChangedEvent event) {
		this.validationConstraints = Collections.emptySet();
		if (repainOnValidate != null) {
			repainOnValidate.requestRepaint();
		}
	}

	public void setRepainOnValidate(final Component repainOnValidate) {
		this.repainOnValidate = repainOnValidate;
	}

}
