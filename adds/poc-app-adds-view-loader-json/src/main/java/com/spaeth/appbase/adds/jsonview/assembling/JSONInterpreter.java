package com.spaeth.appbase.adds.jsonview.assembling;

import static com.spaeth.appbase.adds.jsonview.assembling.JSONPropertyNameConvention.NAME_PROPERTY;

import org.json.JSONException;
import org.json.JSONObject;

import com.spaeth.appbase.core.view.assembling.Context;
import com.spaeth.appbase.core.view.assembling.Creator;
import com.spaeth.appbase.core.view.assembling.Interpreter;

public class JSONInterpreter implements Interpreter<JSONObject> {

	private final Creator<JSONObject> rootObjectCreator;

	public JSONInterpreter(final Creator<JSONObject> rootObjectCreator) {
		super();
		this.rootObjectCreator = rootObjectCreator;
	}

	@Override
	public Object interpret(final JSONObject representation, final Context<JSONObject> context) {
		final Object result = this.rootObjectCreator.createObjectForRepresentation(representation, context);
		if (representation.has(NAME_PROPERTY)) {
			try {
				context.objectCreated(representation.getString(NAME_PROPERTY), result);
			} catch (final JSONException e) {
				throw new NullPointerException("Not able to get name property from " + representation);
			}
		}
		context.assemble(result, representation);
		return result;
	}

}
