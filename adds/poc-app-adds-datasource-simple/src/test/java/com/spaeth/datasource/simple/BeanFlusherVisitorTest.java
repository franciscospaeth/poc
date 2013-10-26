package com.spaeth.datasource.simple;

import junit.framework.Assert;

import org.junit.Test;

import com.spaeth.datasource.DataSource;
import com.spaeth.test.model.Address;
import com.spaeth.test.model.TestBean;

public class BeanFlusherVisitorTest {

	@Test
	public void testRegularApply() {

		final TestBean tb = new TestBean();
		tb.setAddress(new Address());

		tb.setName("J F K");
		tb.getAddress().setStreet("Adams street");
		tb.getAddress().setNumber(142);

		final BeanFlusherVisitor visitor = new BeanFlusherVisitor(tb);

		final SimpleDataSourceBuilder dsb = new SimpleDataSourceBuilder();
		final SimpleDataSource ds = dsb.withType(TestBean.class).withInitialValue(tb)//
				.newProperty("name").withInitialValue(tb.getName()).withType(String.class).add()//
				.newProperty("address").withInitialValue(tb.getAddress()).withType(Address.class)//
				.newProperty("street").withInitialValue("Another Street").withType(String.class).add()//
				.newProperty("number").withInitialValue(tb.getAddress().getNumber()).withType(Integer.class).add()//
				.add().build();

		final DataSource nameProperty = ds.getProperty("name");
		final DataSource addressProperty = ds.getProperty("address");
		final DataSource streetProperty = addressProperty.getProperty("street");
		final DataSource numberProperty = addressProperty.getProperty("number");

		Assert.assertEquals("J F K", nameProperty.get());
		Assert.assertEquals("Another Street", streetProperty.get());
		Assert.assertEquals(142, numberProperty.get());

		nameProperty.set("Francisco");

		Assert.assertTrue(nameProperty.isChanged());
		Assert.assertFalse(addressProperty.isChanged());
		Assert.assertFalse(streetProperty.isChanged());
		Assert.assertFalse(numberProperty.isChanged());
		Assert.assertEquals("Francisco", nameProperty.get());

		streetProperty.set("Other Street");

		Assert.assertTrue(nameProperty.isChanged());
		Assert.assertFalse(addressProperty.isChanged());
		Assert.assertTrue(streetProperty.isChanged());
		Assert.assertFalse(numberProperty.isChanged());
		Assert.assertEquals("Other Street", streetProperty.get());

		streetProperty.set("Another Street");
		numberProperty.set(142);

		ds.accept(visitor);

		Assert.assertEquals("Francisco", tb.getName());
		Assert.assertEquals(142, tb.getAddress().getNumber());
		Assert.assertEquals("Adams street", tb.getAddress().getStreet());

	}

}