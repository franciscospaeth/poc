package com.spaeth.appbase.view.json.test;

public class FirstBean {

	private String name;
	private int age;
	private SecondBean secondBean;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(final int age) {
		this.age = age;
	}

	public SecondBean getSecondBean() {
		return this.secondBean;
	}

	public void setSecondBean(final SecondBean secondBean) {
		this.secondBean = secondBean;
	}

	@Override
	public String toString() {
		return "FirstBean [name=" + this.name + ", age=" + this.age + ", secondBean=" + this.secondBean + "]";
	}

}
