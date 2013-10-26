package com.spaeth.appbase.adds.jsonview.assembling;

import org.json.JSONArray;
import org.json.JSONObject;

import com.spaeth.appbase.adds.jsonview.JSONContext;
import com.spaeth.appbase.core.model.Action;
import com.spaeth.appbase.core.model.ViewModel;
import com.spaeth.appbase.core.service.ActionProvider;
import com.spaeth.appbase.core.services.ActionProviderFacade;
import com.spaeth.appbase.core.view.assembling.Assembler;
import com.spaeth.appbase.core.view.assembling.Context;
import com.spaeth.appbase.core.view.assembling.Interpreter;
import com.spaeth.appbase.core.view.assembling.ScriptInterpreter;

public class JSONContextBuilder {

	private final Assembler<JSONObject, JSONContext> assembler;
	private final Interpreter<JSONObject> interpreter;
	private final ActionProviderFacade actionProviderFacade;
	private final ScriptInterpreter scriptInterpreter;

	public JSONContextBuilder(final Assembler<JSONObject, JSONContext> assembler,
			final Interpreter<JSONObject> interpreter, ActionProviderFacade actionProvider, ScriptInterpreter scriptInterpreter) {
		super();
		this.assembler = assembler;
		this.actionProviderFacade = actionProvider;
		this.interpreter = interpreter;
		this.scriptInterpreter = scriptInterpreter;
	}

	private Context<JSONObject> prepareContext(final JSONObject representation, final Context<JSONObject> context) {
		if (!representation.has(JSONPropertyNameConvention.USES_DECLARATION_PROPERTY)) {
			return context;
		}
		// loading uses
		try {
			final JSONArray jsonArray = representation
					.getJSONArray(JSONPropertyNameConvention.USES_DECLARATION_PROPERTY);
			for (int i = 0; i < jsonArray.length(); i++) {
				final String use = jsonArray.getString(i);
				final Class<?> useClass = Class.forName(use);
				context.addUsesDeclaration(useClass.getSimpleName(), useClass);
			}
		} catch (final Exception e) {
			// TODO: shall throw exception pertinent to failure
			throw new RuntimeException(e);
		}
		return context;
	}

	/**
	 * Used to load uses and meta-data.
	 * 
	 * @param definitionObjectRepresentation
	 * @return
	 */
	public final Context<JSONObject> build(final JSONObject definitionObjectRepresentation, final ViewModel viewModel) {
		final Context<JSONObject> context = new JSONContext(this.interpreter, this.assembler, 
				viewModel, new ActionProvider() {
					@Override
					public Action getAction(String actionName) {
						return actionProviderFacade.getAction(viewModel, actionName);
					}
				}, scriptInterpreter);
		prepareContext(definitionObjectRepresentation, context);
		return context;
	}

}
