package com.spaeth.appbase.model;

import java.util.Set;

public interface ActionParameters {

	Object getSource();

	<M> M getValue(String parameterName, Class<M> expectedType);
	
	Set<String> getParametersName();

}
