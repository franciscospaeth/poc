package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.MenuItem;
import com.spaeth.appbase.model.Action;

public interface IMenuOption extends MenuItem {

	Action getAction();
	
	void setAction(Action action);
	
}
