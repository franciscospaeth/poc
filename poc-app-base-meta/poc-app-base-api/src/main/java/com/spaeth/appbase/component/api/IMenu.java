package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.MenuHolder;
import com.spaeth.appbase.component.MenuItem;

public interface IMenu extends MenuItem, MenuHolder {

	void setEnabled(boolean enabled);

}
