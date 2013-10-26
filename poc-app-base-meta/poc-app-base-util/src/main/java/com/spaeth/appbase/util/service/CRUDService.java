package com.spaeth.appbase.util.service;

import java.io.Serializable;

public interface CRUDService<ManagedClass extends Serializable> {

	void save(ManagedClass object);

	void delete(ManagedClass object);

	ManagedClass retrieve(ManagedClass object);

}
