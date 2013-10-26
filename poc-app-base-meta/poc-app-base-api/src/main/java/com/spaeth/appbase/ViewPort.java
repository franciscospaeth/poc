package com.spaeth.appbase;

import com.spaeth.appbase.model.View;

public interface ViewPort {

	/**
	 * Show a view, returns true case the show operation was finished and false
	 * otherwise. When the view is qualified to be exposed by this
	 * {@link ViewPort}, {@link View#onShown(ViewPort)} shall be invoked to
	 * notify the view that it is now exposed.
	 * 
	 * @param view
	 * @return
	 */
	boolean showView(View<?> view);

	/**
	 * Invoked in order to notify that a view was closed.
	 * 
	 * @param view
	 */
	void viewClosed(View<?> view);

}
