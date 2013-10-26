package com.spaeth.appbase.core.service;

public interface StateHolder {

	<M extends Object> M getObject(Class<M> objectClass);
	
	<M extends Object> void addObject(M object, Class<? super M> objectClass);
	
	<M extends Object> M removeObject(Class<M> objectClass);
	
	void clear();
	
}
