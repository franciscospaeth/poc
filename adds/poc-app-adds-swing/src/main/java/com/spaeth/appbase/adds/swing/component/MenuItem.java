package com.spaeth.appbase.adds.swing.component;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public abstract class MenuItem extends Component<JMenuItem> implements com.spaeth.appbase.component.MenuItem {

	private static final long serialVersionUID = 1L;

	void initialize(final JComponent menu) {
		if (menu instanceof JMenuBar) {
			((JMenuBar) menu).add(getDelegated());
		} else if (menu instanceof JMenu) {
			((JMenu) menu).add(getDelegated());
		}
	}

}