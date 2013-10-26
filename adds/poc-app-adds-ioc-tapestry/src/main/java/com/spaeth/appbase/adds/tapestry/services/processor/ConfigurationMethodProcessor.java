package com.spaeth.appbase.adds.tapestry.services.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.tapestry5.ioc.def.ContributionDef2;
import org.apache.tapestry5.ioc.internal.ConfigurationType;
import org.apache.tapestry5.ioc.services.PlasticProxyFactory;

import com.spaeth.appbase.adds.tapestry.services.customized.ContributionDefImpl;
import com.spaeth.appbase.adds.tapestry.services.customized.TapestryReflectionModule;
import com.spaeth.appbase.core.annotations.ConfigurationMethod;
import com.spaeth.appbase.core.marker.MarkerUtils;
import com.spaeth.appbase.core.service.Configurator.MappedConfiguration;
import com.spaeth.appbase.core.service.Configurator.OrderedConfiguration;
import com.spaeth.appbase.core.service.Configurator.UnorderedConfiguration;
import com.spaeth.appbase.core.service.Module;

public class ConfigurationMethodProcessor extends AbstractMethodsProcessor {

	private static final Map<Class<?>, ConfigurationType> PARAMETER_TYPE_TO_CONFIGURATION_TYPE = new HashMap<Class<?>, ConfigurationType>();
	static {
		PARAMETER_TYPE_TO_CONFIGURATION_TYPE.put(OrderedConfiguration.class, ConfigurationType.ORDERED);
		PARAMETER_TYPE_TO_CONFIGURATION_TYPE.put(MappedConfiguration.class, ConfigurationType.MAPPED);
		PARAMETER_TYPE_TO_CONFIGURATION_TYPE.put(UnorderedConfiguration.class, ConfigurationType.UNORDERED);
	}

	private final Module module;
	private final TapestryReflectionModule result;
	private final PlasticProxyFactory proxyFactory;

	public ConfigurationMethodProcessor(final Module module, final TapestryReflectionModule result, final PlasticProxyFactory proxyFactory) {
		super(ConfigurationMethod.class);
		this.module = module;
		this.result = result;
		this.proxyFactory = proxyFactory;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void processInternal(final Method method) {

		ConfigurationMethod annotation = method.getAnnotation(ConfigurationMethod.class);

		ConfigurationType type = null;

		for (Class<?> parameterType : method.getParameterTypes()) {
			ConfigurationType thisParameter = PARAMETER_TYPE_TO_CONFIGURATION_TYPE.get(parameterType);

			if (thisParameter != null) {
				if (type != null) {
					throw new RuntimeException("method " + method + " shall present just one configuration parameter");
				}

				type = thisParameter;
			}
		}

		if (type == null) {
			throw new RuntimeException("method " + method + " shall have a configuration parameter");
		}

		Set<Class> markers = Collections.emptySet();

		Class<? extends Annotation> firstMarker = MarkerUtils.getFirstMarker(method);
		if (firstMarker != null) {
			markers = Collections.<Class> singleton(firstMarker);
		}

		MarkerUtils.getFirstMarker(method);

		ContributionDef2 def = new ContributionDefImpl(module, method, proxyFactory, annotation.value(), markers);

		result.addContributionDef(def);

	}

}
