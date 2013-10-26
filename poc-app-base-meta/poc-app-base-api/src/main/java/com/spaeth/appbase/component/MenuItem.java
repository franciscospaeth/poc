package com.spaeth.appbase.component;

public interface MenuItem extends Component {

	String getText();

	void setText(String text);

	StreamProvider getIcon();

	void setIcon(StreamProvider icon);

	boolean isEnabled();

}
