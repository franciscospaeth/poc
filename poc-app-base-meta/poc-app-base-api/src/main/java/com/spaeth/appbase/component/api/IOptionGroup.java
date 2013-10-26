package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.CollectionViewerComponent;
import com.spaeth.appbase.component.FieldComponent;
import com.spaeth.appbase.component.VisualComponent;

public interface IOptionGroup extends VisualComponent, FieldComponent, CollectionViewerComponent {

	void setMultiSelect(boolean multiSelect);

	boolean isMultiSelect();

	void setCaptionProperty(String propertyName);

	String getCaptionProperty();

}
