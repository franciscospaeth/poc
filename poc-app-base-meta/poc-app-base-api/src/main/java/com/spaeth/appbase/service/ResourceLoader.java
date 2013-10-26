package com.spaeth.appbase.service;

import java.io.InputStream;

public interface ResourceLoader {

	InputStream getResource(String resourceName);
	
}
