package com.spaeth.appbase.adds.springioc.service;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.spaeth.appbase.adds.web.service.HttpGlobals;
import com.spaeth.appbase.core.service.ObjectProvider;
import com.spaeth.appbase.core.service.Repository;

public class WebAppSpringContextObjectProvider implements ObjectProvider {

	public WebAppSpringContextObjectProvider() {
	}

	@Override
	public <T> T provide(Class<T> requiredClass, Repository repository) {
		HttpServlet servlet = repository.getInstance(HttpGlobals.class).getServlet();

		if (servlet == null) {
			return null;
		}

		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servlet
				.getServletContext());
		if (webApplicationContext != null) {
			try {
				return webApplicationContext.getBean(requiredClass);
			} catch (NoSuchBeanDefinitionException e) {
				return null;
			}
		}
		return null;
	}

}
