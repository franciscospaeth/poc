package com.spaeth.appbase.component.api;

public interface IOrderedLayout extends ILayout {

	public enum HorizontalLayoutFlow {
		LEFT_TO_RIGHT, RIGHT_TO_LEFT;
	}
	
	public enum VerticalLayoutFlow {
		TOP_DOWN, BOTTOM_UP;
	}
	
	void setExpandRation(float[] expandRation);
	
	float[] getExpandRatio();
	
}
