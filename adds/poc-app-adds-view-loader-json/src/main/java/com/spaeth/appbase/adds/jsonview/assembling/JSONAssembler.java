package com.spaeth.appbase.adds.jsonview.assembling;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.adds.jsonview.JSONContext;
import com.spaeth.appbase.core.view.assembling.AbstractAssembler;
import com.spaeth.appbase.core.view.assembling.Assembler;
import com.spaeth.appbase.core.view.assembling.Context;

public class JSONAssembler extends AbstractAssembler<JSONObject, JSONContext> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public JSONAssembler() {
		super(null);
	}

	public JSONAssembler(final Assembler<JSONObject, JSONContext> nextAssembler) {
		super(nextAssembler);
	}

	@Override
	protected void beforeAssembleChain(final Object result, final JSONObject jsonObject,
			final Context<JSONObject> context) {
		if (jsonObject.has(JSONPropertyNameConvention.PROPERTIES)) {
			try {
				final JSONObject propertiesObject = jsonObject.getJSONObject(JSONPropertyNameConvention.PROPERTIES);
				final PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(result);
				for (final PropertyDescriptor pd : properties) {
					if (propertiesObject.has(pd.getName())) {
						try {
							final Object propertyValue = propertiesObject.get(pd.getName());
							final Object value = ((JSONContext) context).interpretValue(propertyValue);
							MethodUtils.invokeMethod(result, pd.getWriteMethod().getName(), value);
						} catch (final Exception e) {
							this.logger.warn("Error setting property '" + pd.getName() + "' of " + result.getClass()
									+ " (" + result + ") " + ": " + e.getMessage(), e);
						}
					}
				}
			} catch (final JSONViewException e) {
				throw e;
			} catch (final Exception e) {
				throw new JSONViewException(e);
			}
		}
	}

}
