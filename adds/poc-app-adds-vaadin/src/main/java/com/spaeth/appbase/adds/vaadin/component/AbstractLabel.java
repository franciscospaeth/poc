package com.spaeth.appbase.adds.vaadin.component;

import java.io.Serializable;

import com.spaeth.appbase.component.SizeDefinition;
import com.vaadin.ui.themes.Reindeer;

public class AbstractLabel implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String HUGE_STYLE_NAME = Reindeer.LABEL_H1;
	public static String BIG_STYLE_NAME = Reindeer.LABEL_H2;
	public static String NOMRAL_STYLE_NAME = "";
	public static String SMALL_STYLE_NAME = Reindeer.LABEL_SMALL;

	private final com.vaadin.ui.Component delegated;

	public AbstractLabel(final Component<? extends com.vaadin.ui.Component> delegated) {
		super();
		this.delegated = delegated.getDelegated();
	}

	public com.vaadin.ui.Component getDelegated() {
		return delegated;
	}

	public void setTextSizeDefinition(final SizeDefinition size) {
		switch (size) {
		case BIG:
			getDelegated().setStyleName(BIG_STYLE_NAME);
			break;
		case NORMAL:
			getDelegated().setStyleName(NOMRAL_STYLE_NAME);
			break;
		case HUGE:
			getDelegated().setStyleName(HUGE_STYLE_NAME);
			break;
		case SMALL:
			getDelegated().setStyleName(SMALL_STYLE_NAME);
			break;
		}
	}

	public SizeDefinition getTextSizeDefinition() {
		if (HUGE_STYLE_NAME.equals(getDelegated().getStyleName())) {
			return SizeDefinition.HUGE;
		}
		if (BIG_STYLE_NAME.equals(getDelegated().getStyleName())) {
			return SizeDefinition.BIG;
		}
		if (SMALL_STYLE_NAME.equals(getDelegated().getStyleName())) {
			return SizeDefinition.SMALL;
		}
		return SizeDefinition.NORMAL;
	}

}