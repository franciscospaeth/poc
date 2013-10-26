package com.spaeth.appbase.component.stream;

import java.io.InputStream;

import com.spaeth.appbase.service.ResourceLoader;

public class ClassPathStreamProvider extends AbstractStreamProvider {

	private String resourcePath;
	private ResourceLoader resourceLoader;
	
	public ClassPathStreamProvider(ResourceLoader resourceLoader, String resourcePath) {
		this.resourcePath = resourcePath;
		this.resourceLoader = resourceLoader;
	}
	
	@Override
	protected InputStream createInputStream() {
		return resourceLoader.getResource(resourcePath);
	}

}
