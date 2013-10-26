package com.spaeth.appbase.adds.swing.component;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import org.apache.commons.lang3.ObjectUtils;

import com.spaeth.appbase.adds.swing.component.customized.JFieldFrame;
import com.spaeth.appbase.component.api.ITextField;
import com.spaeth.appbase.core.model.DefaultActionParameters;
import com.spaeth.appbase.model.Action;

public class TextField extends VisualFieldComponent<JFieldFrame<JTextField>> implements ITextField {

	private static final long serialVersionUID = 1L;

	private Action action;

	public TextField() {
	}

	@Override
	protected JFieldFrame<JTextField> createDelegated() {
		JTextField delegated = new JTextField();
		delegated.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				executeAction();
			}
		});

		delegated.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(final FocusEvent e) {
				setValue(getDelegated().getHoldedComponent().getText());
			}

			@Override
			public void focusGained(final FocusEvent e) {
			}
		});

		delegated.setMargin(new Insets(1, 1, 1, 1));
		return new JFieldFrame<JTextField>(delegated);
	}

	@Override
	public void setAction(final Action action) {
		this.action = action;
	}

	@Override
	public Action getAction() {
		return action;
	}

	private void executeAction() {
		if (action != null) {
			action.execute(new DefaultActionParameters.Builder(TextField.this).build());
		}
	}

	@Override
	public void setCaption(final String caption) {
		getDelegated().setCaption(caption);
	}

	@Override
	public String getCaption() {
		return getDelegated().getCaption();
	}

	@Override
	protected void updateView(final Object value) {
		getDelegated().getHoldedComponent().setText(ObjectUtils.toString(value));
	}

}
