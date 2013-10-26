package com.spaeth.appbase.core.datasource;

import com.spaeth.appbase.core.security.model.AccessPolicy;

/**
 * Provider of {@link AccessPolicy} for {@link DataSource}. Given a
 * {@link DataSource}, every refresh of its {@link AccessPolicy}, all providers
 * are invoked in order to check how shall it be presented.
 * 
 * The use of this entry point is generic in a certain way that could be used in
 * order to define the access policy of dataSources based on environment
 * variable like authenticated user and its permission to operate the
 * dataSource.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 */
public interface AccessPolicyProvider {

	/**
	 * Given a dataSource what shall be the applied {@link AccessPolicy} to it.
	 * 
	 * @param dataSource
	 *            dataSource to be evaluated
	 * @return {@link AccessPolicy} to be applied based on the
	 *         {@link AccessPolicyProvider}
	 */
	AccessPolicy getAccessPolicy(DataSource dataSource);

}
