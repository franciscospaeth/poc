package com.spaeth.appbase.core.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.spaeth.appbase.service.ResourceLoader;

public class DefaultResourceLoader implements ResourceLoader {

	private static final String CLASSPATH_PREFIX = "classpath:";
	private final ClassLoader classLoader;

	public DefaultResourceLoader() {
		this.classLoader = getClass().getClassLoader();
	}

	public DefaultResourceLoader(final ClassLoader classLoader) {
		super();
		this.classLoader = classLoader;
	}

	@Override
	public InputStream getResource(final String resourceName) {
		if (resourceName.startsWith(CLASSPATH_PREFIX)) {
			return classLoader.getResourceAsStream(resourceName.substring(CLASSPATH_PREFIX.length()));
		} else {
			try {
				URL url = new URL(resourceName);
				URLConnection conn = url.openConnection();
				conn.setUseCaches(false);
				return conn.getInputStream();
			} catch (MalformedURLException e) {
				return null;
			} catch (IOException e) {
				return null;
			}
		}
	}

}
