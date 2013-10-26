package com.spaeth.appbase.view.json.test;

public class SecondBean {

	private double doubleValue;
	private boolean booleanValue;

	public double getDoubleValue() {
		return this.doubleValue;
	}

	public void setDoubleValue(final double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public boolean isBooleanValue() {
		return this.booleanValue;
	}

	public void setBooleanValue(final boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	@Override
	public String toString() {
		return "SecondBean [doubleValue=" + this.doubleValue + ", booleanValue=" + this.booleanValue + "]";
	}

}
