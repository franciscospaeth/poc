package com.spaeth.appbase.adds.springioc.service;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spaeth.appbase.core.service.ObjectProvider;
import com.spaeth.appbase.core.service.Repository;

public class StaticAppSpringContextObjectProvider implements ObjectProvider {

	private final ApplicationContext applicationContext;

	public StaticAppSpringContextObjectProvider(final String... resources) {
		ClassPathXmlApplicationContext cpac = new ClassPathXmlApplicationContext(resources);
		applicationContext = cpac;
	}

	@Override
	public <T> T provide(final Class<T> requiredClass, final Repository repository) {
		try {
			return applicationContext.getBean(requiredClass);
		} catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}

}
