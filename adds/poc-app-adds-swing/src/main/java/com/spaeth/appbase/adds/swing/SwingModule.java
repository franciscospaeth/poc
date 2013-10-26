package com.spaeth.appbase.adds.swing;

import java.util.List;
import java.util.Map;

import com.spaeth.appbase.Application;
import com.spaeth.appbase.StaticStateHolder;
import com.spaeth.appbase.ViewPort;
import com.spaeth.appbase.adds.swing.component.constructor.ComponentConstructor;
import com.spaeth.appbase.adds.swing.component.constructor.GenericComponentConstructor;
import com.spaeth.appbase.component.Button;
import com.spaeth.appbase.component.CheckBox;
import com.spaeth.appbase.component.ComboBox;
import com.spaeth.appbase.component.DataGrid;
import com.spaeth.appbase.component.DateBox;
import com.spaeth.appbase.component.FileField;
import com.spaeth.appbase.component.HorizontalLayout;
import com.spaeth.appbase.component.Image;
import com.spaeth.appbase.component.Label;
import com.spaeth.appbase.component.Menu;
import com.spaeth.appbase.component.MenuBar;
import com.spaeth.appbase.component.MenuOption;
import com.spaeth.appbase.component.OptionGroup;
import com.spaeth.appbase.component.PanelTab;
import com.spaeth.appbase.component.PasswordField;
import com.spaeth.appbase.component.ScrollPanel;
import com.spaeth.appbase.component.StaticLabel;
import com.spaeth.appbase.component.TabbedPanel;
import com.spaeth.appbase.component.TextArea;
import com.spaeth.appbase.component.TextField;
import com.spaeth.appbase.component.VerticalLayout;
import com.spaeth.appbase.core.annotations.BuilderMethod;
import com.spaeth.appbase.core.annotations.ConfigurationMethod;
import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.Configurator.MappedConfiguration;
import com.spaeth.appbase.core.service.Configurator.OrderedConfiguration;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.appbase.core.service.ScopeConstants;
import com.spaeth.appbase.core.service.StateHolder;

public class SwingModule implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.new Binding(Application.class, SwingApplication.class).withScope(ScopeConstants.SESSION_SCOPE);
		binder.new Binding(StateHolder.class, StaticStateHolder.class);
	}

	@BuilderMethod(eagerlyLoaded = true)
	public ComponentFactory buildComponentFactory(@SuppressWarnings("rawtypes") final Map<Class, ComponentConstructor> constructors) {
		return new ComponentFactory(constructors);
	}

	@ConfigurationMethod(ComponentFactory.class)
	public void configureComponentFactory(final MappedConfiguration<Class<?>, ComponentConstructor> config) {
		config.add(VerticalLayout.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.VerticalLayout.class));
		config.add(HorizontalLayout.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.HorizontalLayout.class));
		config.add(StaticLabel.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.StaticLabel.class));
		config.add(Label.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.Label.class));
		config.add(Button.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.Button.class));
		config.add(TextField.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.TextField.class));
		config.add(TabbedPanel.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.TabbedPanel.class));
		config.add(PanelTab.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.PanelTab.class));
		config.add(MenuBar.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.MenuBar.class));
		config.add(Menu.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.Menu.class));
		config.add(MenuOption.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.MenuOption.class));
		config.add(Image.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.Image.class));
		config.add(List.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.List.class));
		config.add(ComboBox.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.ComboBox.class));
		config.add(CheckBox.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.CheckBox.class));
		config.add(DateBox.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.DateBox.class));
		config.add(ScrollPanel.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.ScrollPanel.class));
		config.add(PasswordField.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.PasswordField.class));
		config.add(DataGrid.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.DataGrid.class));
		config.add(FileField.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.FileField.class));
		config.add(TextArea.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.TextArea.class));
		config.add(OptionGroup.class, new GenericComponentConstructor(com.spaeth.appbase.adds.swing.component.OptionGroup.class));

	}

	@ConfigurationMethod(ViewPort.class)
	@Principal
	public void configureViewPort(final OrderedConfiguration<ViewPort> config, final Application<SwingApplication> application) {
		// creating viewPorts
		final ModalWindowViewPort modalWindowVP = new ModalWindowViewPort(application);
		final MainViewPort mainVP = new MainViewPort(application);

		// adding to configuration
		config.add("MODAL_WINDOW_VIEW_PORT", modalWindowVP, "before:*");
		config.add("MAIN_VIEW_PORT", mainVP, "before:*");
	}

}