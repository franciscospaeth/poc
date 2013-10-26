package com.spaeth.datasource.simple;

import junit.framework.Assert;

import org.junit.Test;

import com.spaeth.datasource.DataSource;
import com.spaeth.test.model.Address;
import com.spaeth.test.model.TestBean;

public class SimpleDataSourceIntegrationTest {

	@Test
	public void cascadeChangeTest() {
		final TestBean tb = new TestBean();
		tb.setAddress(new Address());

		tb.setName("J F K");
		tb.getAddress().setStreet("Adams street");
		tb.getAddress().setNumber(142);

		final SimpleDataSourceBuilderHelper sdsh = new BeanBasedBuilderHelper(tb);

		final SimpleDataSourceBuilder dsb = new SimpleDataSourceBuilder();
		final SimpleDataSource ds = dsb.withPropagationStrategy(BeanPropagationStrategy.INSTANCE).withType(TestBean.class)//
				.newProperty("name").add()//
				.newProperty("address")//
				.newProperty("street").add()//
				.newProperty("number").add()//
				.add().withSimpleDataSourceBuilderHelper(sdsh).build();

		final Address v = new Address();
		v.setNumber(1);
		v.setStreet("Other Street");
		ds.getProperty("address").set(v);

		Assert.assertEquals("Other Street", ds.getProperty("address", "street").get());
	}

	@Test
	public void regularUseTest() {
		final TestBean tb = new TestBean();
		tb.setAddress(new Address());

		tb.setName("J F K");
		tb.getAddress().setStreet("Adams street");
		tb.getAddress().setNumber(142);

		final SimpleDataSourceBuilderHelper sdsh = new BeanBasedBuilderHelper(tb);
		
		final SimpleDataSourceBuilder dsb = new SimpleDataSourceBuilder();
		final SimpleDataSource ds = dsb.withType(TestBean.class)//
				.newProperty("name").add()//
				.newProperty("address")//
				.newProperty("street").add()//
				.newProperty("number").add()//
				.add().withSimpleDataSourceBuilderHelper(sdsh).build();

		final DataSource nameProperty = ds.getProperty("name");
		final DataSource addressProperty = ds.getProperty("address");
		final DataSource streetProperty = addressProperty.getProperty("street");
		final DataSource numberProperty = addressProperty.getProperty("number");

		Assert.assertEquals(tb.getName(), nameProperty.get());
		Assert.assertEquals(tb.getAddress(), addressProperty.get());
		Assert.assertEquals(tb.getAddress().getStreet(), streetProperty.get());
		Assert.assertEquals(tb.getAddress().getNumber(), numberProperty.get());
		
	}
	
}
