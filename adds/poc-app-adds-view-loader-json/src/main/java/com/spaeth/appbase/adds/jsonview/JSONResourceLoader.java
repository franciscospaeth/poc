package com.spaeth.appbase.adds.jsonview;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JSONResourceLoader {

	private final static Map<String, JSONResourceProvider> resourceProviders = new HashMap<String, JSONResourceLoader.JSONResourceProvider>();

	static {
		resourceProviders.put("classpath", new JSONResourceProvider() {
			@Override
			public InputStream getResource(final String resource) {
				return JSONResourceLoader.class.getClassLoader().getResourceAsStream(resource);
			}
		});
		resourceProviders.put("file", new JSONResourceProvider() {
			@Override
			public InputStream getResource(String resource) {
				try {
					return new FileInputStream(new File(resource));
				} catch (Exception e) {
					return null;
				}
			}
		});
	}

	public static InputStream getResource(final String resourceIdentification) {
		final String resourceProviderType = resourceIdentification.substring(0, resourceIdentification.indexOf(':'));
		final JSONResourceProvider jsonResourceProvider = resourceProviders.get(resourceProviderType);
		if (jsonResourceProvider == null) {
			throw new IllegalArgumentException("Resource provider " + jsonResourceProvider
					+ " is not known, please register it or check the resource identification: "
					+ resourceIdentification);
		}
		final String resourceId = resourceIdentification.substring(resourceIdentification.indexOf(':') + 1);
		final InputStream resource = jsonResourceProvider.getResource(resourceId);
		if (resource == null) {
			throw new NullPointerException("resource " + resourceIdentification + " was not found.");
		}
		return resource;
	}

	public static interface JSONResourceProvider {

		InputStream getResource(String resource);

	}

}
