package com.spaeth.appbase.adds.swing.component;

import javax.swing.JButton;

import com.spaeth.appbase.adds.swing.component.mediator.SwingButtonActionMediator;
import com.spaeth.appbase.component.SizeDefinition;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.api.IButton;
import com.spaeth.appbase.model.Action;

public class Button extends DetacheableComponent<JButton> implements IButton {

	private static final long serialVersionUID = 1L;

	private SwingButtonActionMediator mediator;

	private StreamProvider iconStreamProvider;

	private SizeDefinition size = SizeDefinition.BIG;

	public Button() {
	}

	@Override
	protected JButton createDelegated() {
		return new JButton();
	}

	@Override
	public void setAction(final Action action) {
		if (mediator != null) {
			this.getDelegated().removeActionListener(mediator);
			action.removeUpdateListener(mediator);
		}

		if (action != null) {
			mediator = new SwingButtonActionMediator(action, this.getDelegated(), this);
		} else {
			mediator = null;
		}

		if (mediator != null) {
			this.getDelegated().addActionListener(mediator);
			action.addUpdateListener(mediator);
		}
	}

	@Override
	public Action getAction() {
		return mediator.getAction();
	}

	@Override
	public void setText(final String text) {
		getDelegated().setText(text);
	}

	@Override
	public String getText() {
		return getDelegated().getText();
	}

	@Override
	public StreamProvider getIcon() {
		return iconStreamProvider;
	}

	@Override
	public void setIcon(final StreamProvider streamProvider) {
		this.iconStreamProvider = streamProvider;
		if (streamProvider != null) {
			getDelegated().setIcon(new StreamProviderIconAdapter(streamProvider));
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
		// TODO
		this.size = sizeDefinition;
		switch (sizeDefinition) {
		case BIG:
		case HUGE:
		case NORMAL:
		case SMALL:
		}
	}

}
