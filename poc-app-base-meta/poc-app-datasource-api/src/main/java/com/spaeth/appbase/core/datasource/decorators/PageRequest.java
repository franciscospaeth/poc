package com.spaeth.appbase.core.datasource.decorators;

import java.io.Serializable;
import java.util.Map;

public interface PageRequest {

	public abstract int getPageSize();

	public abstract Map<String, Serializable> getParameters();

}