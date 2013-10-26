package com.spaeth.appbase.core.datasource.event;

import com.spaeth.appbase.core.datasource.DataSource;

public interface ValueChangeListener {

	void onChange(DataSourceValueChangedEvent event);

	public enum DataSourceEventCause {
		SET, RESET, COLLECTION_ELEMENT_ADDED, COLLECTION_ELEMENT_REMOVED, EVENT_CASCADE, COMMIT
	}

	public static class DataSourceValueChangedEvent extends DataSourceEvent {

		private static final long serialVersionUID = 1L;

		private final Object oldValue;
		private final Object newValue;
		private final DataSourceEventCause cause;

		public DataSourceValueChangedEvent(final DataSourceEventCause cause, final DataSource source,
				final Object oldValue, final Object newValue) {
			super(source);
			this.oldValue = oldValue;
			this.newValue = newValue;
			this.cause = cause;
		}

		public Object getNewValue() {
			return newValue;
		}

		public Object getOldValue() {
			return oldValue;
		}

		public DataSourceEventCause getCause() {
			return cause;
		}

		@Override
		public String toString() {
			return "DataSourceValueChangedEvent [oldValue=" + oldValue + ", newValue=" + newValue + ", cause=" + cause
					+ "]";
		}

	}

}
