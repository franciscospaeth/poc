package com.spaeth.appbase.adds.commandexecutor.service;

import java.io.Serializable;

public interface Command {

	String getName();

	String getParameterAsString(int i);

	int getParameterAsInt(int i);

	double getParameterAsDouble(int i);

	<M extends Serializable> M getParameter(int i, Class<M> clazz);

}
