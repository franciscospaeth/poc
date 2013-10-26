package com.spaeth.appbase.view.json.assembling;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.spaeth.appbase.adds.jsonview.assembling.DefaultJSONCreator;
import com.spaeth.appbase.adds.jsonview.assembling.JSONAssembler;
import com.spaeth.appbase.adds.jsonview.assembling.JSONContextBuilder;
import com.spaeth.appbase.adds.jsonview.assembling.JSONInterpreter;
import com.spaeth.appbase.core.view.assembling.Context;
import com.spaeth.appbase.view.json.test.FirstBean;

public class JSONInterpreterIntegrationTest {

	private boolean firstBeanCreated = false;
	private boolean secondBeanCreated = false;

	@Test
	public void t_estBasicInterpretation() throws JSONException {
		final String representation = "{" + //
				"uses : [" + //
				"'com.spaeth.appbase.view.json.test.FirstBean'," + //
				"'com.spaeth.appbase.view.json.test.SecondBean'" + //
				"]," + //
				"type : 'FirstBean'," + //
				"name : 'firstBean'," + //
				"properties : {name : 'Francisco Spaeth'," + //
				"age : 29," + //
				"secondBean : {" + //
				"type : 'SecondBean'," + //
				"name : 'secondBean'," + //
				"properties : {doubleValue : 10.5," + //
				"booleanValue : true}}" + //
				"}" + //
				"}";
		final JSONInterpreter jsonInterpreter = new JSONInterpreter(new DefaultJSONCreator());

		final Context<JSONObject> context = new JSONContextBuilder(new JSONAssembler(), jsonInterpreter, null, null).build(
				/*new ObjectCreatedListener() {
					@Override
					public void objectCreated(final String name, final Object object) {
						if (object instanceof FirstBean) {
							JSONInterpreterIntegrationTest.this.firstBeanCreated = true;
						} else if (object instanceof SecondBean) {
							JSONInterpreterIntegrationTest.this.secondBeanCreated = true;
						}
					}
				},*/ new JSONObject(representation), null);

		final Object interpreted = context.interpret(new JSONObject(representation));

		Assert.assertNotNull(interpreted);
		Assert.assertEquals(29, ((FirstBean) interpreted).getAge());
		Assert.assertEquals("Francisco Spaeth", ((FirstBean) interpreted).getName());
		Assert.assertNotNull(((FirstBean) interpreted).getSecondBean());
		Assert.assertEquals(10.5, ((FirstBean) interpreted).getSecondBean().getDoubleValue());
		Assert.assertTrue(this.firstBeanCreated);
		Assert.assertTrue(this.secondBeanCreated);
	}

}
