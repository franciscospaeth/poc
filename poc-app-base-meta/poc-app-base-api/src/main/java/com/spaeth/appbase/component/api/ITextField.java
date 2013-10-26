package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.FieldComponent;
import com.spaeth.appbase.component.VisualComponent;
import com.spaeth.appbase.model.Action;

public interface ITextField extends VisualComponent, FieldComponent {

	void setAction(Action action);

	Action getAction();

}
