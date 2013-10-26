package com.spaeth.appbase.adds.springioc;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.spaeth.appbase.adds.springioc.annotation.Spring;
import com.spaeth.appbase.adds.springioc.service.StaticAppSpringContextObjectProvider;
import com.spaeth.appbase.core.annotations.BuilderMethod;
import com.spaeth.appbase.core.annotations.ConfigurationMethod;
import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.Configurator.OrderedConfiguration;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.appbase.core.service.ObjectProvider;

public class SpringModule implements Module {

	@Override
	public void configure(final Binder binder) {
	}

	@BuilderMethod
	@Spring
	public ObjectProvider buildObjectProvider(final List<String> config) {
		return new StaticAppSpringContextObjectProvider(config.toArray(new String[] {}));
	}

	@ConfigurationMethod(ObjectProvider.class)
	@Principal
	public void configureObjectProvider(final OrderedConfiguration<ObjectProvider> config, @Spring final ObjectProvider objectProvider) {
		config.add("SPRING_OBJECT_PROVIDER", objectProvider, "before:*");
	}

	@ConfigurationMethod(ObjectProvider.class)
	@Spring
	public void configureSpringObjectProvider(final OrderedConfiguration<String> config) {
		String systemContext = System.getProperty("poc.spring.context");
		if (systemContext != null) {
			for (String s : StringUtils.split(systemContext, ',')) {
				config.add("DEFAULT_SYSTEM_CONTEXT_" + s, s);
			}
		}
	}

}
