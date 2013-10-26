package com.spaeth.appbase.adds.jsonview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.spaeth.appbase.adds.jsonview.assembling.JSONViewException;
import com.spaeth.appbase.core.model.ViewModel;
import com.spaeth.appbase.core.service.ActionProvider;
import com.spaeth.appbase.core.view.assembling.Assembler;
import com.spaeth.appbase.core.view.assembling.Context;
import com.spaeth.appbase.core.view.assembling.Interpreter;
import com.spaeth.appbase.core.view.assembling.ScriptInterpreter;

public class JSONContext extends Context<JSONObject> {

	public JSONContext(final Interpreter<JSONObject> interpreter,
			final Assembler<JSONObject, ? extends Context<JSONObject>> rootAssembler, final ViewModel viewModel,
			final ActionProvider actionProvider, ScriptInterpreter scriptInterpreter) {
		super(interpreter, rootAssembler, viewModel, actionProvider, scriptInterpreter);
	}

	public Object interpretValue(final Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof JSONObject) {
			return interpret((JSONObject) value);
		} else if (value instanceof JSONArray) {
			return interpretJSONArray((JSONArray) value);
		} else {
			return value;
		}
	}

	private Object interpretJSONArray(final JSONArray value) {
		final Object[] result = new Object[value.length()];
		for (int i = 0; i < value.length(); i++) {
			try {
				result[i] = interpretValue(value.get(i));
			} catch (final JSONException e) {
				throw new JSONViewException(e);
			}
		}
		return result;
	}

}
