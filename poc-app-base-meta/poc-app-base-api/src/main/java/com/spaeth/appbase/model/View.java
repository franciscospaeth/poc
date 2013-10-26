package com.spaeth.appbase.model;

import java.io.Serializable;

import com.spaeth.appbase.event.ViewCloseEvent;
import com.spaeth.appbase.event.ViewShowEvent;

public interface View<ResultType> extends Serializable {

	/**
	 * Returns a interface object for the view.
	 * 
	 * @return
	 */
	ResultType getContent();

	/**
	 * Method to trigger process after view is prepared with all its
	 * dependencies configured. This is normally trigger by application
	 * controller after it gets the view from provider.
	 */
	void initialize(ViewModel viewModel, StartupInfo startupInfo);

	/**
	 * Invoked when exposed by a viewPort.
	 */
	void onShown(ViewShowEvent event);

	/**
	 * Responsible to close the view and deallocate all resources that were
	 * allocated.
	 */
	void close();

	/**
	 * Executed when view is closed.
	 */
	void onClose(ViewCloseEvent event);

	ViewModel getModel();

}
