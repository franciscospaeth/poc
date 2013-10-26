package com.spaeth.appbase.core.datasource;

/**
 * Interface to rule the difference of edited datasources.
 * 
 * @author spaeth
 * 
 */
public interface DataSourceDiff {

	/**
	 * Number of difference between the original value and the edited one.
	 * 
	 * @return
	 */
	int getDiffCount();

	static final DataSourceDiff DUMMY = new DataSourceDiff() {
		@Override
		public int getDiffCount() {
			return 0;
		}
	};

}
