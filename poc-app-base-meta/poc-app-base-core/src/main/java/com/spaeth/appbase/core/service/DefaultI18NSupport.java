package com.spaeth.appbase.core.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;

import com.spaeth.appbase.service.I18NSupport;
import com.spaeth.appbase.service.ResourceLoader;

public class DefaultI18NSupport implements I18NSupport {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Properties messages = new Properties();
	
	public DefaultI18NSupport(ResourceLoader resourceLoader) {
		try {
			InputStream res = resourceLoader.getResource("classpath:messages.properties");
			messages.load(res);
		} catch (IOException e) {
			logger.warn("not able to load messages for i18n due to: " + e.getMessage());
		}
	}
	
	@Override
	public String getString(final String name) {
		String result = messages.getProperty(name);
		if (result == null) return name;
		return result;
	}

	@Override
	public String getMessage(final String name, final Object... parameters) {
		ST st = new ST(getString(name));
		for (int i = 0; i < parameters.length; i++) {
			st.add("p".concat(String.valueOf(i+1)), parameters[i]);
		}
		return st.render();
	}

}
