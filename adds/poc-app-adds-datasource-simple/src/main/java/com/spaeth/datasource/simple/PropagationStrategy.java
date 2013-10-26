package com.spaeth.datasource.simple;

/**
 * Interface to provide the logic to be used on propagate changes from a dataSource to its properties.
 * 
 * @author "Francisco Spaeth (francisco.spaeth@gmail.com)"
 * 
 */
public interface PropagationStrategy {

	/**
	 * @param dataSource
	 * @param oldValue
	 * @param newValue
	 */
	void propagate(SimpleDataSource dataSource, Object oldValue, Object newValue);

}
