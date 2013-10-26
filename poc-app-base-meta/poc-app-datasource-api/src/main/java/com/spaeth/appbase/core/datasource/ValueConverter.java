package com.spaeth.appbase.core.datasource;

public interface ValueConverter {

	public static final ValueConverter DUMMY = new ValueConverter() {
		@Override
		public Object getValueFromModel(final DataSource ds, final Object model) {
			return model;
		}

		@Override
		public Object getModelFromValue(final DataSource ds, final Object value) {
			return value;
		}
	};

	/**
	 * Invoked in order to convert a value (usually coming from gui) to a model
	 * value.
	 * 
	 * @param ds
	 * @param value
	 * @return
	 */
	Object getModelFromValue(DataSource ds, Object value);

	/**
	 * Invoked in order to convert a model value (usually coming from m2m
	 * interfaces or persistence api) to a value.
	 * 
	 * @param ds
	 * @param model
	 * @return
	 */
	Object getValueFromModel(DataSource ds, Object model);

}
