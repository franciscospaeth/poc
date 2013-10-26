package com.spaeth.appbase.adds.jsonview.assembling;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.core.view.assembling.Context;

@Deprecated
public class ActionDataLoaderCreator extends AbstractJSONCreator {

	private static final Logger logger = LoggerFactory.getLogger(ActionDataLoaderCreator.class);

	public static final String ACTION_IDENTIFIER = "identifier";

	public ActionDataLoaderCreator() {
		super();
	}

	public ActionDataLoaderCreator(final AbstractJSONCreator nextCreator) {
		super(nextCreator);
	}

	@Override
	protected Object executeCreation(final Class<?> classToCreate, final JSONObject representation,
			final Context<JSONObject> creatorContext) throws JSONException {
		String identifier = null;
		if (representation.has(ACTION_IDENTIFIER)) {
			identifier = representation.getString(ACTION_IDENTIFIER);
		}
		if (!StringUtils.isNotBlank(identifier)) {
			throw new IllegalArgumentException("Action name should be valid for ActionDataLoader: "
					+ representation.toString());
		}

		//Action action = null;
		if (creatorContext.getActionProvider() == null) {
			logger.warn("No model bounded to context, action will not be created for component {}", representation);
		} else {
			//action = creatorContext.getActionProvider().getAction(identifier);
		}

		return null;//new ActionDataLoader<ResultDataSet<?>>(action);
	}

	@Override
	protected boolean handle(final Class<?> classToHandle) {
		return false;//ActionDataLoader.class == classToHandle;
	}

}
