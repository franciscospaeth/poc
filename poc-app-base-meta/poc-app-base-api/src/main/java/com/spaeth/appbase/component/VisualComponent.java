package com.spaeth.appbase.component;

public interface VisualComponent extends Component, DetacheableComponent {

	Measure getWidth();

	float getNormalizedWidth();

	void setWidth(Measure width);

	Measure getHeight();

	float getNormalizedHeight();

	void setHeight(Measure height);

	boolean isVisible();

	void setVisible(boolean visible);

	public static class Measure {

		public static final Measure ZERO = new Measure(null, 0);
		public static final Measure ALL = new Measure(MeasureUnit.PERCENTAGE, 100);

		private MeasureUnit unit;
		private int value;

		public Measure(final MeasureUnit unit, final int value) {
			super();
			this.unit = unit;
			this.value = value;
		}

		public MeasureUnit getUnit() {
			return unit;
		}

		public void setUnit(final MeasureUnit unit) {
			this.unit = unit;
		}

		public int getValue() {
			return value;
		}

		public void setValue(final int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			if (unit == null) {
				return "";
			}
			return String.valueOf(value) + (unit == MeasureUnit.PERCENTAGE ? "%" : "");
		}

	}

	public enum MeasureUnit {
		PERCENTAGE, PIXEL;
	}

}
