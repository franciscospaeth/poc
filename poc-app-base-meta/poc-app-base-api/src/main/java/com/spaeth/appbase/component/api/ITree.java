package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.CollectionViewerComponent;
import com.spaeth.appbase.component.FieldComponent;
import com.spaeth.appbase.component.VisualComponent;

public interface ITree extends FieldComponent, VisualComponent, CollectionViewerComponent {

	void setParentProperty(String propertyName);

	String getParentProperty();

	void setCaptionProperty(String propertyName);

	String getCaptionProperty();

}
