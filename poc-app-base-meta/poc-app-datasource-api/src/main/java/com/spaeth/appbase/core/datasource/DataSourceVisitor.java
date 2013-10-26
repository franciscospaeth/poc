package com.spaeth.appbase.core.datasource;

/**
 * Visitor interface in order to walk through a {@link DataSource}.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 */
public interface DataSourceVisitor {

	/**
	 * Invoked each time a DataSource is found in the hierarchy of DataSources
	 * in which the visitor was setup.
	 * 
	 * @param dataSource
	 *            found dataSource that visitor shall visit
	 */
	void visit(DataSource dataSource);

}
