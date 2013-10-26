package com.spaeth.appbase.adds.vaadin.component;

import com.spaeth.appbase.adds.vaadin.component.mediator.VaadinButtonActionMediator;
import com.spaeth.appbase.component.SizeDefinition;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.api.IButton;
import com.spaeth.appbase.model.Action;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Reindeer;

public class Button extends DetacheableComponent<com.vaadin.ui.Button> implements IButton {

	private static final long serialVersionUID = 1L;

	private VaadinButtonActionMediator mediator;

	private StreamProvider iconStreamProvider;

	private SizeDefinition size = SizeDefinition.BIG;

	public Button() {
		setSize(SizeDefinition.NORMAL);
	}

	@Override
	protected com.vaadin.ui.Button createDelegated() {
		return new com.vaadin.ui.Button();
	}

	@Override
	public void setAction(final Action action) {
		if (mediator != null) {
			this.getDelegated().removeListener(mediator);
			action.removeUpdateListener(mediator);
		}

		if (action != null) {
			mediator = new VaadinButtonActionMediator(action, this.getDelegated(), this);
		} else {
			mediator = null;
		}

		if (mediator != null) {
			this.getDelegated().addListener(mediator);
			action.addUpdateListener(mediator);
		}
	}

	@Override
	public Action getAction() {
		return mediator.getAction();
	}

	@Override
	public void setText(final String text) {
		getDelegated().setCaption(text);
	}

	@Override
	public String getText() {
		return getDelegated().getCaption();
	}

	@Override
	public StreamProvider getIcon() {
		return iconStreamProvider;
	}

	@Override
	public void setIcon(final StreamProvider streamProvider) {
		this.iconStreamProvider = streamProvider;
		if (streamProvider != null) {
			getDelegated().setIcon(new StreamProviderResourceAdapter(streamProvider));
		} else {
			getDelegated().setIcon(null);
		}
	}

	@Override
	public SizeDefinition getSize() {
		return size;
	}

	@Override
	public void setSize(final SizeDefinition sizeDefinition) {
		this.size = sizeDefinition;
		switch (sizeDefinition) {
		case BIG:
		case HUGE:
			getDelegated().setStyleName(null);
			break;
		case NORMAL:
			getDelegated().setStyleName(Reindeer.BUTTON_SMALL);
			break;
		case SMALL:
			getDelegated().setStyleName(BaseTheme.BUTTON_LINK);
		}
	}

}
