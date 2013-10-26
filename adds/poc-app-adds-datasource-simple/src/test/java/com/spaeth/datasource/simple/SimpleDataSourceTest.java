package com.spaeth.datasource.simple;

import java.util.Collection;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.spaeth.appbase.model.AccessPolicy;
import com.spaeth.datasource.DataSource;
import com.spaeth.datasource.DataSourceListener;
import com.spaeth.datasource.validation.ConstraintViolation;

public class SimpleDataSourceTest {

	private boolean onDataChanged = false;
	private boolean onReadOnlyChanged = false;

	@Before
	public void prepare() {
		this.onDataChanged = false;
		this.onReadOnlyChanged = false;
	}

	@Test
	public void testFluency() {
		final SimpleDataSource dataSourceRoot = buildDataSource();

		Assert.assertEquals(2, dataSourceRoot.getPropertyNames().size());
		Assert.assertEquals(1, dataSourceRoot.getProperty("Property1").getPropertyNames().size());
		Assert.assertEquals("Property1.Property11",
				dataSourceRoot.getProperty("Property1").getProperty("Property1.Property11").getName());
	}

	@Test
	public void simpleValueChangedEventTest() {

		final SimpleDataSource dataSourceRoot = buildDataSource();

		dataSourceRoot.getProperty("Property1").getProperty("Property1.Property11")
				.addDataSourceListener(new DataSourceListener() {
					@Override
					public void onSecurityAccessibilityChanged(final DataSource dataSourceProperty,
							final AccessPolicy oldSecurityAccessibility,
							final AccessPolicy newSecurityAccessibility) {
						SimpleDataSourceTest.this.onReadOnlyChanged = true;
					}

					@Override
					public void onDataChanged(final DataSource dataSourceProperty, final Object oldValue,
							final Object newValue) {
						SimpleDataSourceTest.this.onDataChanged = true;
					}

					@Override
					public void onValidate(final DataSource dataSourceProperty,
							final Collection<ConstraintViolation> constraintViolations) {
					}
				});

		dataSourceRoot.getProperty("Property1").setDataAccessPolicy(AccessPolicy.WRITE);
		dataSourceRoot.setSecurityConstraintDataAccessPolicy(AccessPolicy.WRITE);

		dataSourceRoot.getProperty("Property1").getProperty("Property1.Property11").set("value");

		Assert.assertEquals(true, this.onDataChanged);

	}

	@Test
	public void simpleReadOnlyEventTest() {

		final SimpleDataSource dataSourceRoot = buildDataSource();

		dataSourceRoot.getProperty("Property1").getProperty("Property1.Property11")
				.addDataSourceListener(new DataSourceListener() {
					@Override
					public void onSecurityAccessibilityChanged(final DataSource dataSourceProperty,
							final AccessPolicy oldSecurityAccessibility,
							final AccessPolicy newSecurityAccessibility) {
						SimpleDataSourceTest.this.onReadOnlyChanged = true;
					}

					@Override
					public void onDataChanged(final DataSource dataSourceProperty, final Object oldValue,
							final Object newValue) {
						SimpleDataSourceTest.this.onDataChanged = true;
					}

					@Override
					public void onValidate(final DataSource dataSourceProperty,
							final Collection<ConstraintViolation> constraintViolations) {
					}
				});

		dataSourceRoot.getProperty("Property1").getProperty("Property1.Property11")
				.setDataAccessPolicy(AccessPolicy.READ);

		Assert.assertEquals(true, this.onReadOnlyChanged);

	}

	@Test
	public void simpleRetroFeededEventTest() {

		final SimpleDataSource dataSourceRoot = buildDataSource();

		dataSourceRoot.getProperty("Property1").addDataSourceListener(new DataSourceListener() {
			@Override
			public void onSecurityAccessibilityChanged(final DataSource dataSourceProperty,
					final AccessPolicy oldSecurityAccessibility,
					final AccessPolicy newSecurityAccessibility) {
				SimpleDataSourceTest.this.onReadOnlyChanged = true;
			}

			@Override
			public void onDataChanged(final DataSource dataSourceProperty, final Object oldValue, final Object newValue) {
				SimpleDataSourceTest.this.onDataChanged = true;
			}

			@Override
			public void onValidate(final DataSource dataSourceProperty,
					final Collection<ConstraintViolation> constraintViolations) {
			}
		});

		dataSourceRoot.getProperty("Property1").setDataAccessPolicy(AccessPolicy.WRITE);
		dataSourceRoot.setDataAccessPolicy(AccessPolicy.WRITE);

		dataSourceRoot.getProperty("Property1").getProperty("Property1.Property11").set("value");

		Assert.assertEquals(true, this.onDataChanged);

	}

	@Test
	public void simpleCascadedValueChangeEventTest() {

		final SimpleDataSource dataSourceRoot = buildDataSource();

		dataSourceRoot.getProperty("Property1").getProperty("Property1.Property11")
				.addDataSourceListener(new DataSourceListener() {
					@Override
					public void onSecurityAccessibilityChanged(final DataSource dataSourceProperty,
							final AccessPolicy oldSecurityAccessibility,
							final AccessPolicy newSecurityAccessibility) {
						SimpleDataSourceTest.this.onReadOnlyChanged = true;
					}

					@Override
					public void onDataChanged(final DataSource dataSourceProperty, final Object oldValue,
							final Object newValue) {
						SimpleDataSourceTest.this.onDataChanged = true;
					}

					@Override
					public void onValidate(final DataSource dataSourceProperty,
							final Collection<ConstraintViolation> constraintViolations) {
					}
				});

		dataSourceRoot.getProperty("Property1").setDataAccessPolicy(AccessPolicy.WRITE);
		dataSourceRoot.setDataAccessPolicy(AccessPolicy.WRITE);

		dataSourceRoot.getProperty("Property1").set("value");

		Assert.assertEquals(true, this.onDataChanged);

	}

	@Test
	public void simpleCascadedReadOnlyChangeEventTest() {

		final SimpleDataSource dataSourceRoot = buildDataSource();

		dataSourceRoot.getProperty("Property1").getProperty("Property1.Property11")
				.addDataSourceListener(new DataSourceListener() {
					@Override
					public void onSecurityAccessibilityChanged(final DataSource dataSourceProperty,
							final AccessPolicy oldSecurityAccessibility,
							final AccessPolicy newSecurityAccessibility) {
						SimpleDataSourceTest.this.onReadOnlyChanged = true;
					}

					@Override
					public void onDataChanged(final DataSource dataSourceProperty, final Object oldValue,
							final Object newValue) {
						SimpleDataSourceTest.this.onDataChanged = true;
					}

					@Override
					public void onValidate(final DataSource dataSourceProperty,
							final Collection<ConstraintViolation> constraintViolations) {
					}
				});

		dataSourceRoot.getProperty("Property1").setDataAccessPolicy(AccessPolicy.WRITE);

		Assert.assertEquals(true, this.onReadOnlyChanged);
		Assert.assertEquals(false, dataSourceRoot.getProperty("Property1").getProperty("Property1.Property11")
				.getDataAccessPolicy() == AccessPolicy.READ);

		dataSourceRoot.setDataAccessPolicy(AccessPolicy.WRITE);

		Assert.assertEquals(false, dataSourceRoot.getProperty("Property1").getProperty("Property1.Property11")
				.getDataAccessPolicy() == AccessPolicy.READ);
	}

	private SimpleDataSource buildDataSource() {
		return new SimpleDataSourceBuilder()//
				.withAccessibilityPolicy(AccessPolicy.READ)//
				// property 1
				.newProperty("Property1")//
				.withAccessibilityPolicy(AccessPolicy.READ)//
				.withType(String.class)//
				.newProperty("Property1.Property11")//
				.withType(Double.class)//
				.add()//
				.add()//
				// property 2
				.newProperty()//
				.withName("Property2")//
				.withType(Integer.class)//
				.add()//
				.build();
	}

}
