package com.spaeth.datasource.simple;

import junit.framework.Assert;

import org.junit.Test;

import com.spaeth.appbase.model.AccessPolicy;
import com.spaeth.test.model.Address;
import com.spaeth.test.model.TestBean;

public class BeanBasedBuilderHelperTest {

	@Test
	public void testRegularInitialization() {
		final TestBean tb = new TestBean();
		tb.setAddress(new Address());

		tb.setName("J F K");
		tb.getAddress().setStreet("Adams street");
		tb.getAddress().setNumber(142);

		final BeanBasedBuilderHelper beanBasedBuilderHelper = new BeanBasedBuilderHelper(tb);

		Assert.assertEquals("J F K", beanBasedBuilderHelper.getInitialValueFor(new SimpleDataSource(null, null,
				AccessPolicy.WRITE, TestBean.class, tb), "name"));
		Assert.assertEquals(String.class, beanBasedBuilderHelper.getTypeFor(new SimpleDataSource(null, null,
				AccessPolicy.WRITE, TestBean.class, tb), "name"));

		Assert.assertEquals("Adams street", beanBasedBuilderHelper.getInitialValueFor(new SimpleDataSource(
				new SimpleDataSource(null, null, AccessPolicy.WRITE, TestBean.class, tb), "address",
				AccessPolicy.WRITE, Address.class, tb.getAddress()), "street"));
		Assert.assertEquals(String.class, beanBasedBuilderHelper.getTypeFor(new SimpleDataSource(new SimpleDataSource(
				null, null, AccessPolicy.WRITE, TestBean.class, tb), "address", AccessPolicy.WRITE,
				Address.class, tb.getAddress()), "street"));
	}

	@Test
	public void testNullPropertyInitialization() {
		final TestBean tb = new TestBean();
		tb.setAddress(null);

		tb.setName("J F K");

		final BeanBasedBuilderHelper beanBasedBuilderHelper = new BeanBasedBuilderHelper(tb);

		Assert.assertEquals(null, beanBasedBuilderHelper.getInitialValueFor(new SimpleDataSource(new SimpleDataSource(
				null, null, AccessPolicy.WRITE, TestBean.class, tb), "address", AccessPolicy.WRITE,
				Address.class, tb), "street"));
		Assert.assertEquals(String.class, beanBasedBuilderHelper.getTypeFor(new SimpleDataSource(new SimpleDataSource(
				null, null, AccessPolicy.WRITE, TestBean.class, tb), "address", AccessPolicy.WRITE,
				Address.class, tb), "street"));
	}

}
