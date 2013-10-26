package com.spaeth.appbase.adds.commandexecutor.service.converter;

import java.io.Serializable;

import com.spaeth.appbase.adds.commandexecutor.service.CommandParameterConverter;

public class IntegerCommandParameterConverter implements CommandParameterConverter {

	@Override
	public Integer convert(final String value, final Class<? extends Serializable> clazz) {
		if (!Integer.class.equals(clazz)) {
			return null;
		}

		return Integer.parseInt(value.trim());
	}

}
