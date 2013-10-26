package com.spaeth.appbase.component.api;

import java.util.Locale;
import java.util.TimeZone;

import com.spaeth.appbase.component.FieldComponent;
import com.spaeth.appbase.component.VisualComponent;

public interface IDateBox extends VisualComponent, FieldComponent {

	public enum DateResolution {
		SECOND, MINUTE, HOUR, DAY, MONTH, YEAR;
	}

	void setDateResolution(DateResolution dateResolution);

	DateResolution getDateResolution();

	void setLocale(Locale locale);

	Locale getLocale();

	void setTimeZone(TimeZone timeZone);

	TimeZone getTimeZone();

}
