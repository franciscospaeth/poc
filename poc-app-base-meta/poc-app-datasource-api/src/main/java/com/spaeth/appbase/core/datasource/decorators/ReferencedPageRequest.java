package com.spaeth.appbase.core.datasource.decorators;

public interface ReferencedPageRequest<ResultType> extends PageRequest {

	ResultType getReferenced();

}
