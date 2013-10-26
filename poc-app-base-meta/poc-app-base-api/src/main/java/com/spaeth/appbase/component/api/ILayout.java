package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.ComponentContainer;
import com.spaeth.appbase.component.VisualComponent;

public interface ILayout extends ComponentContainer, VisualComponent {

	public enum LayoutType {
		VERTICAL_LAYOUT, HORIZONTAL_LAYOUT;
	}

	void setSpaced(boolean spaced);

	boolean isSpaced();

	void setMarginVisible(boolean margin);

	boolean isMarginVisible();

}
