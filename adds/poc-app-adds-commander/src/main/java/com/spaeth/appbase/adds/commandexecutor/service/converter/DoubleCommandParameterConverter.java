package com.spaeth.appbase.adds.commandexecutor.service.converter;

import java.io.Serializable;

import com.spaeth.appbase.adds.commandexecutor.service.CommandParameterConverter;

public class DoubleCommandParameterConverter implements CommandParameterConverter {

	@Override
	public Double convert(final String value, final Class<? extends Serializable> clazz) {
		if (!Integer.class.equals(clazz)) {
			return null;
		}

		return Double.parseDouble(value.trim());
	}

}
