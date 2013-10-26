package com.spaeth.appbase.core.datasource;

/**
 * Interface that allows the implementation retrieve a DataSource in a later
 * moment. This allows for example that in build time you can use a reference
 * that will hold a DataSource in a certain time in the future. This is the
 * principle used for lookUps in {@link ProxyDataSource}, that is needed a
 * reference from a {@link CollectionDataSource} that is not yet built.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 */
public interface DataSourceHolder {

	/**
	 * DataSource hold by this implementor.
	 * 
	 * @return hold DataSource
	 */
	public DataSource getDataSource();

}
