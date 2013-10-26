package com.spaeth.datasource.simple.validation;

import org.junit.Assert;
import org.junit.Test;

import com.spaeth.datasource.simple.BeanBasedBuilderHelper;
import com.spaeth.datasource.simple.SimpleDataSource;
import com.spaeth.datasource.simple.SimpleDataSourceBuilder;
import com.spaeth.datasource.simple.SimpleDataSourceBuilderHelper;
import com.spaeth.datasource.validation.StringSizeValidator;
import com.spaeth.test.model.Address;
import com.spaeth.test.model.TestBean;

public class ValidatorIntegration {

	@Test
	public void testIntegration() {
		final TestBean tb = new TestBean();
		tb.setAddress(new Address());

		tb.setName("J F K");
		tb.getAddress().setStreet("Adams street..........");
		tb.getAddress().setNumber(142);

		final SimpleDataSourceBuilderHelper sdsh = new BeanBasedBuilderHelper(tb);

		final SimpleDataSourceBuilder dsb = new SimpleDataSourceBuilder();
		final SimpleDataSource ds = dsb.withType(TestBean.class)//
				.newProperty("name").withValidator(new StringSizeValidator("Invalid size of name.", 2, 10)).add()//
				.newProperty("address")//
				.newProperty("street").withValidator(new StringSizeValidator("Invalid size of street.", 5, 10)).add()//
				.newProperty("number").add()//
				.add().withSimpleDataSourceBuilderHelper(sdsh).build();

		Assert.assertEquals(false, ds.validate().isValid());

		ds.getProperty("address", "street").set("teste");

		Assert.assertEquals(true, ds.validate().isValid());

		ds.getProperty("name").set("s");

		Assert.assertEquals(false, ds.validate().isValid());
	}

}
