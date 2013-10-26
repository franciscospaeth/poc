package com.spaeth.appbase.core.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.spaeth.appbase.model.ActionParameters;

public class DefaultActionParameters implements ActionParameters {

	private final Object source;
	private final Map<String, Object> parameters = new HashMap<String, Object>();

	private DefaultActionParameters(Object source) {
		super();
		this.source = source;
	}

	@Override
	public Object getSource() {
		return source;
	}

	@Override
	public <M> M getValue(String parameterName, Class<M> expectedType) {
		Object value = parameters.get(parameterName);

		if (value == null) {
			return null;
		}
		
		return expectedType.cast(value);
	}

	@Override
	public Set<String> getParametersName() {
		return parameters.keySet();
	}
	
	public static class Builder {
		
		private DefaultActionParameters result;
		
		public Builder(Object sender) {
			result = new DefaultActionParameters(sender);
		}
		
		public Builder addParameter(String parameterName, Object value) {
			result.parameters.put(parameterName, value);
			return this;
		}
		
		public DefaultActionParameters build() {
			return result;
		}
		
	}

}
