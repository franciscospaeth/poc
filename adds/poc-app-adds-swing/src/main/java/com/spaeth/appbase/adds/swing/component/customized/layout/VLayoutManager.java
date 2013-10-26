package com.spaeth.appbase.adds.swing.component.customized.layout;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;

public class VLayoutManager extends OrderedLayoutManager<VLayoutFlow> {

	public VLayoutManager(final VLayoutFlow flowType, final int gap) {
		super(flowType, gap);
	}

	@Override
	protected int getAxisLocation(final Point point) {
		return point.y;
	}

	@Override
	protected int getCounterAxisLocation(final Point point) {
		return point.x;
	}

	@Override
	protected int getAxisSize(final Dimension dim) {
		return dim.height;
	}

	@Override
	protected int getCounterAxisSize(final Dimension dim) {
		return dim.width;
	}

	@Override
	protected int sumCounterAxisInsets(final Insets insets) {
		return insets.top + insets.bottom;
	}

	@Override
	protected Dimension createDimension(final int axisSize, final int counterAxisSize) {
		return new Dimension(counterAxisSize, axisSize);
	}

	@Override
	protected Point createPoint(final int axisLocation, final int counterAxisLocation) {
		return new Point(counterAxisLocation, axisLocation);
	}

	@Override
	protected boolean isRegularFlow() {
		return flowType == VLayoutFlow.TOP_DOWN;
	}

	@Override
	protected int getVerticalGap() {
		return gap;
	}

	@Override
	protected int getHorizontalGap() {
		return 0;
	}

	@Override
	public void addLayoutComponent(final Component comp, final Object constraints) {
		if (constraints instanceof OrderedLayoutConstraint) {
			if (!((OrderedLayoutConstraint) constraints).isWidthDefined()) {
				((OrderedLayoutConstraint) constraints).setWidth("100%");
			}
		}
		super.addLayoutComponent(comp, constraints);
	}

}
