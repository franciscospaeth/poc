package com.spaeth.appbase.adds.jsonview.vaadin.assembling;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.adds.jsonview.JSONContext;
import com.spaeth.appbase.adds.jsonview.assembling.JSONViewException;
import com.spaeth.appbase.adds.vaadin.component.mediator.VaadinButtonActionMediator;
import com.spaeth.appbase.core.model.Action;
import com.spaeth.appbase.core.view.assembling.AbstractSpecificClassAsssembler;
import com.spaeth.appbase.core.view.assembling.Assembler;
import com.spaeth.appbase.core.view.assembling.Context;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class VaadinButtonJSONAssembler extends AbstractSpecificClassAsssembler<Button, JSONObject, JSONContext> {

	private static final Logger logger = LoggerFactory.getLogger(VaadinButtonJSONAssembler.class);

	private static final String ACTION = "action";
	private static final String ACTION_NAME = "name";
	private static final String ACTION_PARAMETERS = "parameters";
	private static final String ON_CLICK = "onClick";

	public VaadinButtonJSONAssembler(final Assembler<JSONObject, JSONContext> nextAssembler) {
		super(Button.class, nextAssembler);
	}

	@Override
	protected void internalAssemble(final Button result, final JSONObject representation,
			final Context<JSONObject> context) {
		try {
			if (representation.has(ON_CLICK)) {
				final String localScript = representation.getString(ON_CLICK);
				result.addListener(new ClickListener() {
					private static final long serialVersionUID = 1L;
					@Override
					public void buttonClick(final ClickEvent event) {
						context.interpretScript(localScript);
					}
				});
			} else if (representation.has(ACTION)) {
				actionInterpretation(result, representation, context);
			}
		} catch (final Exception e) {
			throw new JSONViewException(e);
		}
	}

	private void actionInterpretation(final Button result, final JSONObject representation,
			final Context<JSONObject> context) throws JSONException {

		final JSONObject actionRepresentation = representation.getJSONObject(ACTION);
		if (actionRepresentation.has(ACTION_NAME)) {
			final String actionName = actionRepresentation.getString(ACTION_NAME);
			final JSONArray parametersArray = actionRepresentation.getJSONArray(ACTION_PARAMETERS);
			final Object[] parameters = new Object[parametersArray.length()];

			for (int i = 0; i < parametersArray.length(); i++) {
				parameters[i] = parametersArray.get(i);
			}

			Action action = null;
			if (context.getActionProvider() == null) {
				logger.warn("No model bounded to context, action will not be bounded for component {}", result);
			} else {
				action = context.getActionProvider().getAction(actionName);
			}

			final VaadinButtonActionMediator clickListener = new VaadinButtonActionMediator(action,
					result, parameters);
			action.addUpdateListener(clickListener);
			result.addListener(clickListener);
		}
	}

}
