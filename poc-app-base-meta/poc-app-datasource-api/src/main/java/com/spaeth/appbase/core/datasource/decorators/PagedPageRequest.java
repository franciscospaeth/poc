package com.spaeth.appbase.core.datasource.decorators;

import java.util.Collections;

/**
 * Object that represents a page request done by a controller. The page request
 * basically holds the requested page and the parameters to fulfill the request.
 * Parameters on this sense means all data needed to return a filtered set of
 * data according rules on business side.
 * 
 * The following rules needs to be satisfied in order to fulfill the request:
 * <ul>
 * <li>pages starts on 0, meaning 0 the first page;</li>
 * <li>parameters cannot be null, when empty they should return an empty map (
 * {@link Collections#emptyMap()})</li>
 * </ul>
 * 
 * @see DefaultPagedPageRequest
 * 
 * @author spaeth
 * 
 */
public interface PagedPageRequest extends PageRequest {

	int getPage();

}
