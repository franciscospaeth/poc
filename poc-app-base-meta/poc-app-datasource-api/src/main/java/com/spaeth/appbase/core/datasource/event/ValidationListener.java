package com.spaeth.appbase.core.datasource.event;

import java.util.Collection;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.validation.ConstraintViolation;

public interface ValidationListener {

	void onValidate(ValidationEvent event);

	public static class ValidationEvent extends DataSourceEvent {

		private static final long serialVersionUID = 1L;
		private Collection<ConstraintViolation> violationConstraints;

		public ValidationEvent(DataSource dataSource, Collection<ConstraintViolation> violationConstraints) {
			super(dataSource);
			this.violationConstraints = violationConstraints;
		}

		public Collection<ConstraintViolation> getViolationConstraints() {
			return violationConstraints;
		}

	}

}
