package com.spaeth.appbase.component;

import java.io.InputStream;

import com.spaeth.appbase.event.StreamProviderUpdateListener;

public interface StreamProvider {

	InputStream getStream();

	void addUpdateListener(StreamProviderUpdateListener listener);

	void removeUpdateListener(StreamProviderUpdateListener listener);

}
