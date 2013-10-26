package com.spaeth.appbase.core.datasource.event;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.DataSourceDiff;

public interface CommitListener {
	void onPostCommit(CommitEvent event);

	/**
	 * Executed before a commit operation.
	 * 
	 * @param event
	 * @throws CommitException
	 *             when commit operation needs to be aborted
	 */
	void onPreCommit(CommitEvent event) throws CommitException;

	public static class CommitEvent extends DataSourceEvent {

		private static final long serialVersionUID = 1L;
		private DataSourceDiff collectionDiff = null;

		public CommitEvent(final DataSource dataSource, final DataSourceDiff diff) {
			super(dataSource);
			this.collectionDiff = diff;
		}

		public DataSourceDiff getDiff() {
			return collectionDiff;
		}

	}

	public static class CommitException extends Exception {

		private static final long serialVersionUID = 1L;

	}

}
