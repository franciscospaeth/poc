package com.spaeth.appbase.core.viewport;

import com.spaeth.appbase.ViewPort;
import com.spaeth.appbase.component.ComponentContainer;
import com.spaeth.appbase.component.DetacheableComponent;
import com.spaeth.appbase.event.ViewShowEvent;
import com.spaeth.appbase.model.View;

public abstract class ComponentContainerViewPort implements ViewPort {

	@Override
	public boolean showView(final View<?> view) {
		final ComponentContainer viewPortComponentContainer = getViewPortComponentContainer(view);

		if (viewPortComponentContainer == null) {
			return false;
		}

		Object content = view.getContent();

		if (!(content instanceof DetacheableComponent)) {
			return false;
		}

		viewPortComponentContainer.addComponent((DetacheableComponent) content);

		onShowView(view, viewPortComponentContainer);

		return true;
	}

	protected void onShowView(final View<?> view, final ComponentContainer viewPortComponentContainer) {
		view.onShown(new ViewShowEvent(this));
	}

	protected abstract ComponentContainer getViewPortComponentContainer(final View<?> view);

}
