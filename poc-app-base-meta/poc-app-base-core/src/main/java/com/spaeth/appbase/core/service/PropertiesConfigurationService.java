package com.spaeth.appbase.core.service;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesConfigurationService extends DefaultConfigurationService {

	private static Logger logger = LoggerFactory.getLogger(PropertiesConfigurationService.class);

	public PropertiesConfigurationService(final String systemPropertyName) {
		super(propertyFileToMap(systemPropertyName));
	}

	protected static Map<String, Object> propertyFileToMap(final String systemPropertyName) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(systemPropertyName));
		} catch (Exception e) {
			logger.warn("not able to load properties from file: {}", systemPropertyName);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (Entry<Object, Object> e : properties.entrySet()) {
			map.put(String.valueOf(e.getKey()), e.getValue());
		}
		return map;
	}

}
