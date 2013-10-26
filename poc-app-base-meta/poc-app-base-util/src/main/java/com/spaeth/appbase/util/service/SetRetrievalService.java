package com.spaeth.appbase.util.service;

import java.io.Serializable;
import java.util.Collection;

import com.spaeth.appbase.core.datasource.decorators.PageRequest;
import com.spaeth.appbase.core.datasource.decorators.PageResponse;

public interface SetRetrievalService<ManagedClass extends Serializable> {

	PageResponse<? extends Collection<ManagedClass>> load(PageRequest pageRequest);

}
