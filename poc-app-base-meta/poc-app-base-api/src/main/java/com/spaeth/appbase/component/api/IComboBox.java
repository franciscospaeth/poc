package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.CollectionViewerComponent;
import com.spaeth.appbase.component.FieldComponent;
import com.spaeth.appbase.component.VisualComponent;

public interface IComboBox extends VisualComponent, FieldComponent, CollectionViewerComponent {

	void setCaptionProperty(String propertyName);

	String getCaptionProperty();

}
