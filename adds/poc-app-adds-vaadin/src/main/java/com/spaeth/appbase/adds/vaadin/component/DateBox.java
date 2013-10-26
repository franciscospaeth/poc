package com.spaeth.appbase.adds.vaadin.component;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.spaeth.appbase.adds.vaadin.component.mediator.VaadinDataSourcePropertyAdapter;
import com.spaeth.appbase.component.api.IDateBox;
import com.vaadin.ui.PopupDateField;

public class DateBox extends VisualFieldComponent<PopupDateField> implements IDateBox {

	private static final long serialVersionUID = 1L;

	@Override
	protected PopupDateField createDelegated() {
		PopupDateField delegated = new PopupDateField();
		delegated.setResolution(PopupDateField.RESOLUTION_HOUR);
		delegated.setImmediate(true);
		return delegated;
	}

	@Override
	public void setDateResolution(final DateResolution dateResolution) {
		switch (dateResolution) {
		case YEAR:
			getDelegated().setResolution(PopupDateField.RESOLUTION_YEAR);
			break;
		case MONTH:
			getDelegated().setResolution(PopupDateField.RESOLUTION_MONTH);
			break;
		case DAY:
			getDelegated().setResolution(PopupDateField.RESOLUTION_HOUR);
			break;
		case HOUR:
			getDelegated().setResolution(PopupDateField.RESOLUTION_HOUR);
			break;
		case MINUTE:
			getDelegated().setResolution(PopupDateField.RESOLUTION_MIN);
			break;
		case SECOND:
			getDelegated().setResolution(PopupDateField.RESOLUTION_SEC);
			break;
		}
	}

	@Override
	public DateResolution getDateResolution() {
		switch (getDelegated().getResolution()) {
		case PopupDateField.RESOLUTION_YEAR:
			return DateResolution.YEAR;
		case PopupDateField.RESOLUTION_MONTH:
			return DateResolution.MONTH;
		case PopupDateField.RESOLUTION_DAY:
			return DateResolution.DAY;
		case PopupDateField.RESOLUTION_HOUR:
			return DateResolution.HOUR;
		case PopupDateField.RESOLUTION_MIN:
			return DateResolution.MINUTE;
		case PopupDateField.RESOLUTION_SEC:
			return DateResolution.SECOND;
		}
		return null;
	}

	@Override
	public void setLocale(final Locale locale) {
		getDelegated().setLocale(locale);
	}

	@Override
	public Locale getLocale() {
		return getDelegated().getLocale();
	}

	@Override
	public void setTimeZone(final TimeZone timeZone) {
		getDelegated().setTimeZone(timeZone);
	}

	@Override
	public TimeZone getTimeZone() {
		return getDelegated().getTimeZone();
	}

	@Override
	protected VaadinDataSourcePropertyAdapter createDataSourcePropertyAdapter() {
		VaadinDataSourcePropertyAdapter property = new VaadinDataSourcePropertyAdapter();
		property.setHoldedType(Date.class);
		return property;
	}

}
