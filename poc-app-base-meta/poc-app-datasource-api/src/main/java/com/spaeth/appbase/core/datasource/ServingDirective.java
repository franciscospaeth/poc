package com.spaeth.appbase.core.datasource;

/**
 * Basically a {@link ServingDirective} is used to expose how a given dataSource
 * behaves. In certain conditions the DataSource definition in not accessible
 * letting his children dataSources not accessible. A {@link ServingDirective}
 * is used in a descriptor object in order to flag what kind of dataSource is
 * served by the given path.
 * 
 * This allows proxies dataSource deliver a kind of other proxyObject for
 * certain properties event not knowing the future value of it. Suppose you have
 * a dataSource that has two properties: name and titles, a regular value and a
 * collection respectively. When dataSource is bounded to target components they
 * can provide a compatible interface to the served values (in this case a
 * valueDataSource and a collectionDataSource).
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 */
public enum ServingDirective {

	/**
	 * Value provided to regular dataSource values
	 */
	VALUE,

	/**
	 * Value provided to collection dataSource values
	 */
	COLLECTION;

}
