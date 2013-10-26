package com.spaeth.appbase.adds.commandexecutor.service;

import java.io.Serializable;

public interface CommandParameterConverter {

	Serializable convert(String value, Class<? extends Serializable> clazz);

}
