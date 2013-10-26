package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.SizeDefinition;
import com.spaeth.appbase.component.VisualComponent;

public interface IAbstractLabel extends VisualComponent {

	void setTextSizeDefinition(SizeDefinition size);

	SizeDefinition getTextSizeDefinition();

}
