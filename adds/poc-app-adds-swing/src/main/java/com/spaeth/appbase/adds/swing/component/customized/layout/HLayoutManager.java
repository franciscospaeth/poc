package com.spaeth.appbase.adds.swing.component.customized.layout;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;

public class HLayoutManager extends OrderedLayoutManager<HLayoutFlow> {

	public HLayoutManager(final HLayoutFlow flowType, final int gap) {
		super(flowType, gap);
	}

	@Override
	protected int getAxisLocation(final Point point) {
		return point.x;
	}

	@Override
	protected int getCounterAxisLocation(final Point point) {
		return point.y;
	}

	@Override
	protected int getAxisSize(final Dimension dim) {
		return dim.width;
	}

	@Override
	protected int getCounterAxisSize(final Dimension dim) {
		return dim.height;
	}

	@Override
	protected int sumCounterAxisInsets(final Insets insets) {
		return insets.left + insets.right;
	}

	@Override
	protected Dimension createDimension(final int axisSize, final int counterAxisSize) {
		return new Dimension(axisSize, counterAxisSize);
	}

	@Override
	protected Point createPoint(final int axisLocation, final int counterAxisLocation) {
		return new Point(axisLocation, counterAxisLocation);
	}

	@Override
	protected boolean isRegularFlow() {
		return flowType == HLayoutFlow.LEFT_TO_RIGHT;
	}

	@Override
	protected int getVerticalGap() {
		return 0;
	}

	@Override
	protected int getHorizontalGap() {
		return gap;
	}

}
