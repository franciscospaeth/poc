package com.spaeth.appbase.adds.xmlview;

import com.spaeth.appbase.component.Component;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.VerticalLayout;

public class ViewLayout extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final StreamProvider icon;
	private final String title;
	private final boolean closable;

	public ViewLayout(final StreamProvider icon, final String title, final boolean closable) {
		super();
		this.icon = icon;
		this.title = title;
		this.closable = closable;
	}

	public String getTitle() {
		return title;
	}

	public boolean isClosable() {
		return closable;
	}

	public StreamProvider getIcon() {
		return icon;
	}

	@Override
	public Class<? extends Component> getComponentClass() {
		return VerticalLayout.class;
	}

}
