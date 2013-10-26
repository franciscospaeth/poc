package com.spaeth.appbase.component.api;

import com.spaeth.appbase.component.CollectionViewerComponent;
import com.spaeth.appbase.component.VisualComponent;
import com.spaeth.appbase.core.datasource.DataSource;

public interface IRepeater extends VisualComponent, CollectionViewerComponent {

	void setComponentWhenEmpty(VisualComponent visualComponent);
	
	VisualComponent getComponentWhenEmpty();
	
	Repeated getRepeated();

	void setRepeated(Repeated repeated);

	public static interface Repeated {

		VisualComponent create(DataSource element);

	}

}
