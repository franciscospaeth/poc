package com.spaeth.appbase.model;

public interface ViewCloseHandler {

	public static final ViewCloseHandler NOT_CLOSABLE = new ViewCloseHandler() {
		@Override
		public void execute() {
			throw new IllegalStateException("this view cannot be closed");
		}
	};

	public static final ViewCloseHandler DUMMY = new ViewCloseHandler() {
		@Override
		public void execute() {
		}
	};

	void execute();

}
