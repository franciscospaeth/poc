package com.spaeth.appbase.adds.swing.component.customized.layout;

import org.apache.commons.lang3.StringUtils;

import com.spaeth.appbase.component.VisualComponent.MeasureUnit;

public class OrderedLayoutConstraint {

	private int width;
	private int height;
	private float expandRatio;
	private MeasureUnit widthUnit;
	private MeasureUnit heightUnit;
	private boolean widthDefined = false;
	private boolean heightDefined = false;

	public OrderedLayoutConstraint(final String width, final String height, final float expandRatio) {
		setWidth(width);
		setHeight(height);
		this.expandRatio = expandRatio;
	}

	public void setHeight(final String height) {
		if (StringUtils.isBlank(height)) {
			this.height = 0;
			this.heightDefined = false;
			return;
		}
		if (height.contains("%")) {
			heightUnit = MeasureUnit.PERCENTAGE;
			this.height = Integer.parseInt(height.replace("%", ""));
		} else {
			heightUnit = MeasureUnit.PIXEL;
			this.height = Integer.parseInt(height);
		}
		this.heightDefined = true;
	}

	public void setWidth(final String width) {
		if (StringUtils.isBlank(width)) {
			this.width = 0;
			this.widthDefined = false;
			return;
		}
		if (width.contains("%")) {
			widthUnit = MeasureUnit.PERCENTAGE;
			this.width = Integer.parseInt(width.replace("%", ""));
		} else {
			widthUnit = MeasureUnit.PIXEL;
			this.width = Integer.parseInt(width);
		}
		this.widthDefined = true;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(final int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(final int height) {
		this.height = height;
	}

	public float getExpandRatio() {
		return expandRatio;
	}

	public void setExpandRatio(final float expandRatio) {
		this.expandRatio = expandRatio;
	}

	public MeasureUnit getWidthUnit() {
		return widthUnit;
	}

	public void setWidthUnit(final MeasureUnit widthUnit) {
		this.widthUnit = widthUnit;
	}

	public MeasureUnit getHeightUnit() {
		return heightUnit;
	}

	public void setHeightUnit(final MeasureUnit heightUnit) {
		this.heightUnit = heightUnit;
	}

	public boolean isWidthDefined() {
		return widthDefined;
	}

	public boolean isHeightDefined() {
		return heightDefined;
	}

}
