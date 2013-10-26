package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.VisualComponent;

public interface IImage extends VisualComponent {

	void setStreamProvider(StreamProvider streamProvider);

	StreamProvider getStreamProvider();

}
