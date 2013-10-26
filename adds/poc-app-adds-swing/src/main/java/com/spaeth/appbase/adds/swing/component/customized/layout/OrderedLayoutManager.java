package com.spaeth.appbase.adds.swing.component.customized.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import com.spaeth.appbase.component.VisualComponent.MeasureUnit;

public abstract class OrderedLayoutManager<FlowType> implements LayoutManager2 {

	protected final int gap;
	protected final Map<Component, OrderedLayoutConstraint> constraints = new HashMap<Component, OrderedLayoutConstraint>();
	protected final FlowType flowType;

	public OrderedLayoutManager(final FlowType flowType, final int gap) {
		super();
		this.gap = gap;
		this.flowType = flowType;
	}

	@Override
	public void layoutContainer(final Container parent) {
		Map<Component, Dimension> dims = calculateDimensions(parent);

		// calculate expanded dimensions
		expandDimensions(dims, parent);

		// set bounds
		Rectangle rect = null;
		for (Component c : parent.getComponents()) {
			rect = getComponentBounds(parent, c, dims, rect);
			c.setBounds(rect);
		}
	}

	protected Map<Component, Dimension> calculateDimensions(final Container parent) {
		Map<Component, Dimension> dims = new HashMap<Component, Dimension>();
		// calculate dimensions
		for (Component c : parent.getComponents()) {
			if (!c.isVisible()) {
				continue;
			}
			dims.put(c, getLayoutedDimension(parent, c, constraints.get(c)));
		}
		return dims;
	}

	private Dimension getLayoutedDimension(final Container parent, final Component component,
			final OrderedLayoutConstraint constraint) {
		// calculate regular size of component
		int width = 0;
		int height = 0;

		// if not defined
		if (constraint == null) {
			return new Dimension(width, height);
		}

		Dimension availableDim = getAvailableDimension(parent);

		// calculating width
		if (!constraint.isWidthDefined()) {
			width = component.getPreferredSize().width;
		} else if (constraint.getWidthUnit() == MeasureUnit.PERCENTAGE) {
			width = (int) (constraint.getWidth() / 100.0 * availableDim.width);
		} else if (constraint.getWidthUnit() == MeasureUnit.PIXEL) {
			width = constraint.getWidth();
		}

		// calculating height
		if (!constraint.isHeightDefined()) {
			height = component.getPreferredSize().height;
		} else if (constraint.getHeightUnit() == MeasureUnit.PERCENTAGE) {
			height = (int) (constraint.getHeight() / 100.0 * availableDim.height);
		} else if (constraint.getHeightUnit() == MeasureUnit.PIXEL) {
			height = constraint.getHeight();
		}

		return new Dimension(width, height);
	}

	protected Dimension getAvailableDimension(final Container parent) {
		int availableWidth = parent.getWidth() - (parent.getInsets().left + parent.getInsets().right)
				- getTotalAxisGapSpace(parent, getHorizontalGap());
		int availableHeight = parent.getHeight() - (parent.getInsets().top + parent.getInsets().bottom)
				- getTotalAxisGapSpace(parent, getVerticalGap());
		availableWidth = Math.max(0, availableWidth);
		availableHeight = Math.max(0, availableHeight);
		return new Dimension(availableWidth, availableHeight);
	}

	private void expandDimensions(final Map<Component, Dimension> calculatedRawDims, final Container parent) {

		// max width supported
		int sum = 0;
		for (Dimension d : calculatedRawDims.values()) {
			sum += getAxisSize(d);
		}

		// expand ratio total
		float totalExpandRatio = 0;
		for (OrderedLayoutConstraint olc : constraints.values()) {
			totalExpandRatio += olc.getExpandRatio();
		}

		int parentAvailableAxisSize = getAxisSize(getAvailableDimension(parent));
		int expandable = parentAvailableAxisSize - sum;

		// resize all dimensions according expand rate
		int total = 0;
		for (Component c : parent.getComponents()) {

			// if not visible skip
			if (!c.isVisible()) {
				continue;
			}

			OrderedLayoutConstraint olc = constraints.get(c);
			Dimension dim = calculatedRawDims.get(c);
			int additional = (int) (expandable * (olc.getExpandRatio() / totalExpandRatio));

			// if no additional value should be added no resize is needed
			if (additional <= 0) {
				continue;
			}

			// final size
			int axisSize = getAxisSize(dim) + additional;

			// if due to rounding the size is bigger just adapt it
			total += axisSize;
			if (total > parentAvailableAxisSize) {
				axisSize -= total - parentAvailableAxisSize;
			}

			// resize it
			calculatedRawDims.put(c, createDimension(axisSize, getCounterAxisSize(dim)));
		}

	}

	private int getTotalAxisGapSpace(final Container parent, final int gap) {
		int i = 0;
		for (Component c : parent.getComponents()) {
			if (c.isVisible()) {
				i++;
			}
		}
		i = Math.max(0, i - 1);
		return i * gap;
	}

	protected Rectangle getComponentBounds(final Container parent, final Component c,
			final Map<Component, Dimension> dims, final Rectangle rect) {

		Dimension dim = dims.get(c);

		Insets insets = parent.getInsets();

		int availableSpace = getCounterAxisSize(parent.getSize()) - sumCounterAxisInsets(insets);
		int axisStartLocation;
		Point point;

		if (isRegularFlow()) {
			// point for normal flow
			point = new Point(insets.left, insets.top);

			// initial axis location for the component
			if (rect != null) {
				axisStartLocation = getAxisLocation(rect.getLocation()) + getAxisSize(rect.getSize()) + gap;
			} else {
				axisStartLocation = getAxisLocation(point);
			}
		} else {
			// point for reverse flow
			point = new Point(insets.right, insets.bottom);

			if (rect != null) {
				axisStartLocation = getAxisLocation(rect.getLocation()) - gap - getAxisSize(dim);
			} else {
				axisStartLocation = getAxisLocation(point) - getAxisSize(dim);
			}
		}

		// initial counter axis location for the component
		int counterAxisLimitLocation = getCounterAxisLocation(point);
		int counterAxisStartLocation = counterAxisLimitLocation + (availableSpace - getCounterAxisSize(dim)) / 2;
		counterAxisStartLocation = Math.max(counterAxisLimitLocation, counterAxisStartLocation);

		return new Rectangle(createPoint(axisStartLocation, counterAxisStartLocation), dim);
	}

	@Override
	public void removeLayoutComponent(final Component comp) {
		constraints.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(final Container parent) {
		Map<Component, Dimension> dimensions = calculateDimensions(parent);

		int axisSize = 0;
		int counterAxisSize = 0;

		for (Dimension dim : dimensions.values()) {
			axisSize += getAxisSize(dim);
			counterAxisSize = Math.max(counterAxisSize, getCounterAxisSize(dim));
		}

		return createDimension(axisSize, counterAxisSize);
	}

	@Override
	public Dimension minimumLayoutSize(final Container parent) {
		return preferredLayoutSize(parent);
	}

	@Override
	public void addLayoutComponent(final Component comp, final Object constraints) {
		if (!(constraints instanceof OrderedLayoutConstraint)) {
			throw new IllegalArgumentException("constraints should be an " + OrderedLayoutConstraint.class + "instance");
		}
		this.constraints.put(comp, (OrderedLayoutConstraint) constraints);
		revalidate(comp);
	}

	protected void revalidate(final Component comp) {
		if (comp instanceof JComponent) {
			((JComponent) comp).revalidate();
		}
	}

	public void setWidth(final Component c, final String value) {
		constraints.get(c).setWidth(value);
		revalidate(c);
	}

	public void setHeight(final Component c, final String value) {
		constraints.get(c).setHeight(value);
		revalidate(c);
	}

	public void setExpandRatio(final Component c, final float expandRatio) {
		constraints.get(c).setExpandRatio(expandRatio);
		revalidate(c);
	}

	protected abstract int getAxisLocation(Point point);

	protected abstract int getCounterAxisLocation(Point point);

	protected abstract int getAxisSize(Dimension dim);

	protected abstract int getCounterAxisSize(Dimension dim);

	protected abstract int sumCounterAxisInsets(Insets insets);

	protected abstract Dimension createDimension(int axisSize, int counterAxisSize);

	protected abstract Point createPoint(int axisLocation, int counterAxisLocation);

	protected abstract boolean isRegularFlow();

	protected abstract int getVerticalGap();

	protected abstract int getHorizontalGap();

	// - not used
	@Override
	public void addLayoutComponent(final String name, final Component comp) {
	}

	@Override
	public Dimension maximumLayoutSize(final Container target) {
		return null;
	}

	@Override
	public float getLayoutAlignmentX(final Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(final Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(final Container target) {
	}

}
