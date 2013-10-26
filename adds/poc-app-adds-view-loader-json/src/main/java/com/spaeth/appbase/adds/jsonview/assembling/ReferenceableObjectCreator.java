package com.spaeth.appbase.adds.jsonview.assembling;

import org.json.JSONObject;

import com.spaeth.appbase.core.view.assembling.AbstractCreator;
import com.spaeth.appbase.core.view.assembling.Context;
import com.spaeth.appbase.core.view.assembling.Creator;

public class ReferenceableObjectCreator extends AbstractCreator<JSONObject> {

	public ReferenceableObjectCreator() {
		super();
	}

	public ReferenceableObjectCreator(final Creator<JSONObject> nextCreator) {
		super(nextCreator);
	}

	@Override
	public Object executeCreation(final JSONObject representation, final Context<JSONObject> creatorContext) {
		try {
			if (representation.has(JSONPropertyNameConvention.REFERENCE_TO)
					&& !representation.has(JSONPropertyNameConvention.TYPE_PROPERTY)) {
				String referenceName = representation.getString(JSONPropertyNameConvention.REFERENCE_TO);
				final Object contextObject = creatorContext.getContextObject(referenceName);
				if (contextObject == null) {
					throw new JSONViewException("Object " + representation
							+ " is not present in referenceable object list.");
				}
				return contextObject;
			}
			return super.executeCreation(representation, creatorContext);
		} catch (final JSONViewException e) {
			throw e;
		} catch (final Exception e) {
			throw new JSONViewException(e);
		}
	}

}
