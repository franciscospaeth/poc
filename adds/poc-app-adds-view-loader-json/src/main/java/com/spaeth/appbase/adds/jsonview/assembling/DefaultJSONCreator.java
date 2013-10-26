package com.spaeth.appbase.adds.jsonview.assembling;

import org.apache.commons.beanutils.ConstructorUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.spaeth.appbase.adds.jsonview.JSONContext;
import com.spaeth.appbase.core.view.assembling.Context;

public class DefaultJSONCreator extends AbstractJSONCreator {

	public DefaultJSONCreator() {
		super();
	}

	public DefaultJSONCreator(final AbstractJSONCreator nextCreator) {
		super(nextCreator);
	}

	@Override
	protected Object executeCreation(final Class<?> classToCreate, final JSONObject representation,
			final Context<JSONObject> creatorContext) {
		try {
			if (representation.has(JSONPropertyNameConvention.CONSTRUCTOR_ARGUMENTS)) {
				final JSONContext jsonContext = (JSONContext) creatorContext;
				final JSONArray jsonValues = representation
						.getJSONArray(JSONPropertyNameConvention.CONSTRUCTOR_ARGUMENTS);
				final Object[] values = (Object[]) jsonContext.interpretValue(jsonValues);
				return ConstructorUtils.invokeConstructor(classToCreate, values);
			} else {
				return classToCreate.newInstance();
			}
		} catch (final Exception e) {
			throw new JSONViewException(e);
		}
	}

	@Override
	protected boolean handle(final Class<?> classToHandle) {
		return true;
	}

}
