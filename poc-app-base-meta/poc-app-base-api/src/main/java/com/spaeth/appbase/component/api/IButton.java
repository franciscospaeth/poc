package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.SizeDefinition;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.VisualComponent;
import com.spaeth.appbase.model.Action;

public interface IButton extends VisualComponent {

	void setAction(Action action);

	Action getAction();

	void setText(String text);

	String getText();

	StreamProvider getIcon();

	void setIcon(StreamProvider streamProvider);

	SizeDefinition getSize();

	void setSize(SizeDefinition sizeDefinition);

}
