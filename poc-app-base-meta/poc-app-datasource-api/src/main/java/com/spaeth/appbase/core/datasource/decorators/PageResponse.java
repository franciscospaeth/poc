package com.spaeth.appbase.core.datasource.decorators;

import java.util.Collection;

/**
 * Response object holding requested paged data.
 * 
 * @author Francisco Spaeth <francisco.spaeth@gmail.com>
 * 
 * @param <ResponseType>
 */
public interface PageResponse<ResponseType extends Collection<?>> extends PagedPageRequest {

	int getTotal();

	ResponseType getResult();

}
