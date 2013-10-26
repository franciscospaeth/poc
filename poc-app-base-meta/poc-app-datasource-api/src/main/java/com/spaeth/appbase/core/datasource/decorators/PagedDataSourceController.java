package com.spaeth.appbase.core.datasource.decorators;

import java.util.Collection;

/**
 * Controller for PagedDataSources. Basically this controller performs the
 * requests agains the data provider in order to retrieve a paged result set of
 * data.
 * 
 * In order to provide so {@link PagedPageRequest} and {@link PageResponse} are
 * used to retrieve the data set.
 * 
 * @author Francisco Spaeth <francisco.spaeth@gmail.com>
 * 
 * @param <ResultType>
 *            result type provided by this controller
 */
public interface PagedDataSourceController<ResultElementType> {

	PageResponse<? extends Collection<ResultElementType>> load(PageRequest pageRequest);

	PagedPageRequest createPageRequest(int page, int pageSize);

	ReferencedPageRequest<ResultElementType> createPageRequest(ResultElementType elementPage, int pageSize);

}
