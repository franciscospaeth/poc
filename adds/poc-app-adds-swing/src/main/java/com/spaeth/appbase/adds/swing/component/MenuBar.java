package com.spaeth.appbase.adds.swing.component;

import java.awt.FlowLayout;
import java.util.Collections;
import java.util.List;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import org.apache.commons.collections.CollectionUtils;

import com.spaeth.appbase.adds.swing.ComponentWrapperHelper;
import com.spaeth.appbase.component.MenuItem;
import com.spaeth.appbase.component.api.IMenuBar;

public class MenuBar extends DetacheableComponent<JPanel> implements IMenuBar {

	private static final long serialVersionUID = 1L;
	private List<MenuItem> options = Collections.emptyList();
	private JMenuBar menuBar;

	public MenuBar() {
		setWidth(Measure.ALL);
		setHeight(new Measure(MeasureUnit.PIXEL, 25));
	}

	@Override
	public List<MenuItem> getOptions() {
		return options;
	}

	@Override
	public void setOptions(final List<MenuItem> options) {
		for (Object mi : CollectionUtils.subtract(options, this.options)) {
			ComponentWrapperHelper.unwrap((com.spaeth.appbase.component.Component) mi,
					com.spaeth.appbase.adds.swing.component.MenuItem.class).initialize(menuBar);
		}
		for (Object mi : CollectionUtils.subtract(this.options, options)) {
			menuBar.remove(ComponentWrapperHelper.unwrap((com.spaeth.appbase.component.Component) mi,
					com.spaeth.appbase.adds.swing.component.MenuItem.class).getDelegated());
		}
		this.options = options;
	}

	@Override
	protected JPanel createDelegated() {
		menuBar = new JMenuBar();
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(menuBar);
		return panel;
	}
}