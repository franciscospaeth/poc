package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.Component;
import com.spaeth.appbase.component.ComponentContainer;
import com.spaeth.appbase.component.StreamProvider;

public interface IPanelTab extends Component, ComponentContainer {

	void setCaption(String caption);

	String getCaption();

	void setClosable(boolean closable);

	boolean isClosable();

	void setVisible(boolean visible);

	boolean isVisible();

	void setIcon(StreamProvider streamProvider);

	StreamProvider getIcon();

}
