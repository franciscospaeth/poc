package com.spaeth.appbase.model;

import com.spaeth.appbase.model.Action;

public interface ActionSecurityConstraint {

	boolean isAccessible(Action action);

}
