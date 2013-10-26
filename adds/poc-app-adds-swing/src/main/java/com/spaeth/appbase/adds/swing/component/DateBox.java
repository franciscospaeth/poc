package com.spaeth.appbase.adds.swing.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.JFormattedTextField;

import com.spaeth.appbase.adds.swing.component.customized.JFieldFrame;
import com.spaeth.appbase.component.api.IDateBox;

public class DateBox extends VisualFieldComponent<JFieldFrame<JFormattedTextField>> implements IDateBox {

	private static final long serialVersionUID = 1L;
	private final DateFormat dateFormat = new SimpleDateFormat();
	private DateResolution dateResolution;

	@Override
	protected JFieldFrame<JFormattedTextField> createDelegated() {
		JFormattedTextField delegated = new JFormattedTextField();
		setDateResolution(DateResolution.HOUR);
		return new JFieldFrame<JFormattedTextField>(delegated);
	}

	@Override
	public void setDateResolution(final DateResolution dateResolution) {
		this.dateResolution = dateResolution;
		// TODO
		switch (dateResolution) {
		case YEAR:
		case MONTH:
		case DAY:
		case HOUR:
		case MINUTE:
		case SECOND:
		}
	}

	@Override
	public DateResolution getDateResolution() {
		return dateResolution;
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
		dateFormat.setTimeZone(timeZone);
	}

	@Override
	public TimeZone getTimeZone() {
		return dateFormat.getTimeZone();
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
		getDelegated().getHoldedComponent().setValue(value);
	}

}
