package com.spaeth.appbase.adds.jsonview.vaadin.assembling;

import org.json.JSONArray;
import org.json.JSONObject;

import com.spaeth.appbase.adds.jsonview.JSONContext;
import com.spaeth.appbase.adds.jsonview.assembling.JSONViewException;
import com.spaeth.appbase.core.view.assembling.AbstractAssembler;
import com.spaeth.appbase.core.view.assembling.Assembler;
import com.spaeth.appbase.core.view.assembling.Context;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;

public class VaadinComponentContainerJSONAssembler extends AbstractAssembler<JSONObject, JSONContext> {

	private static final String CHILDREN = "children";
	private static final String EXPAND_RATIOS = "expandRatios";

	public VaadinComponentContainerJSONAssembler(final Assembler<JSONObject, JSONContext> nextAssembler) {
		super(nextAssembler);
	}

	public VaadinComponentContainerJSONAssembler() {
		super(null);
	}

	@Override
	protected void beforeAssembleChain(final Object result, final JSONObject jsonObject,
			final Context<JSONObject> context) throws JSONViewException {
		if (!(result instanceof AbstractComponentContainer)) {
			return;
		}

		final AbstractComponentContainer componentContainer = (AbstractComponentContainer) result;

		try {
			if (jsonObject.has(CHILDREN)) {
				final JSONArray array = jsonObject.getJSONArray(CHILDREN);
				for (int i = 0; i < array.length(); i++) {
					final Object element = array.get(i);
					if (!(element instanceof JSONObject)) {
						throw new JSONViewException("Just objects are allowed inside 'childs' property in element "
								+ jsonObject);
					}
					final Object interpreted = context.interpret((JSONObject) element);

					if (!(interpreted instanceof Component)) {
						throw new JSONViewException(
								"child elements should result in Component objects, but it doesn't happends with "
										+ element);
					}

					componentContainer.addComponent((Component) interpreted);
				}
			}
		} catch (final JSONViewException e) {
			throw e;
		} catch (final Exception e) {
			throw new JSONViewException(e);
		}
	}

	@Override
	protected void afterAssembleChain(final Object result, final JSONObject jsonObject,
			final Context<JSONObject> context) {
		if (!(result instanceof AbstractComponentContainer)) {
			return;
		}

		final AbstractComponentContainer componentContainer = (AbstractComponentContainer) result;

		try {
			if ((componentContainer instanceof AbstractOrderedLayout) && jsonObject.has(EXPAND_RATIOS)) {
				final AbstractOrderedLayout aol = (AbstractOrderedLayout) componentContainer;
				final JSONArray array = jsonObject.getJSONArray(EXPAND_RATIOS);
				for (int i = 0; i < array.length(); i++) {
					final double element = array.getDouble(i);
					aol.setExpandRatio(aol.getComponent(i), (float) element);
				}
			}
		} catch (final Exception e) {
			throw new JSONViewException(e);
		}
	}

}
