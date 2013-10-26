package com.spaeth.appbase.adds.vaadin;

import java.util.List;
import java.util.Map;

import com.spaeth.appbase.Application;
import com.spaeth.appbase.ViewPort;
import com.spaeth.appbase.adds.vaadin.component.constructor.ComponentConstructor;
import com.spaeth.appbase.adds.vaadin.component.constructor.GenericComponentConstructor;
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
import com.spaeth.appbase.component.Repeater;
import com.spaeth.appbase.component.RichTextArea;
import com.spaeth.appbase.component.ScrollPanel;
import com.spaeth.appbase.component.StaticLabel;
import com.spaeth.appbase.component.TabbedPanel;
import com.spaeth.appbase.component.TextArea;
import com.spaeth.appbase.component.TextField;
import com.spaeth.appbase.component.Tree;
import com.spaeth.appbase.component.VerticalLayout;
import com.spaeth.appbase.core.annotations.BuilderMethod;
import com.spaeth.appbase.core.annotations.ConfigurationMethod;
import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.Configurator.MappedConfiguration;
import com.spaeth.appbase.core.service.Configurator.OrderedConfiguration;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.appbase.core.service.ScopeConstants;

public class VaadinModule implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.new Binding(Application.class, VaadinApplication.class).withScope(ScopeConstants.SESSION_SCOPE);
	}

	@BuilderMethod(eagerlyLoaded = true)
	public ComponentFactory buildComponentFactory(@SuppressWarnings("rawtypes") final Map<Class, ComponentConstructor> constructors) {
		return new ComponentFactory(constructors);
	}

	@ConfigurationMethod(ComponentFactory.class)
	public void configureComponentFactory(final MappedConfiguration<Class<?>, ComponentConstructor> ctors) {
		ctors.add(VerticalLayout.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.VerticalLayout.class));
		ctors.add(HorizontalLayout.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.HorizontalLayout.class));
		ctors.add(StaticLabel.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.StaticLabel.class));
		ctors.add(Label.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.Label.class));
		ctors.add(Button.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.Button.class));
		ctors.add(TextField.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.TextField.class));
		ctors.add(TabbedPanel.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.TabbedPanel.class));
		ctors.add(PanelTab.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.PanelTab.class));
		ctors.add(MenuBar.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.MenuBar.class));
		ctors.add(Menu.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.Menu.class));
		ctors.add(MenuOption.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.MenuOption.class));
		ctors.add(Image.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.Image.class));
		ctors.add(List.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.List.class));
		ctors.add(ComboBox.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.ComboBox.class));
		ctors.add(CheckBox.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.CheckBox.class));
		ctors.add(DateBox.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.DateBox.class));
		ctors.add(ScrollPanel.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.ScrollPanel.class));
		ctors.add(PasswordField.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.PasswordField.class));
		ctors.add(DataGrid.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.DataGrid.class));
		ctors.add(FileField.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.FileField.class));
		ctors.add(TextArea.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.TextArea.class));
		ctors.add(OptionGroup.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.OptionGroup.class));
		ctors.add(Tree.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.Tree.class));
		ctors.add(RichTextArea.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.RichtTextArea.class));
		ctors.add(Repeater.class, new GenericComponentConstructor(com.spaeth.appbase.adds.vaadin.component.Repeater.class));
	}

	@ConfigurationMethod(ViewPort.class)
	@Principal
	public void configureViewPort(final OrderedConfiguration<ViewPort> conf, final Application<VaadinApplication> application) {
		final ModalWindowViewPort modalWindowVP = new ModalWindowViewPort(application);
		final MainViewPort mainVP = new MainViewPort(application);
		final VaadinStreamViewPort streamViewPort = new VaadinStreamViewPort(application);

		// adding to configuration
		conf.add("MODAL_WINDOW_VIEW_PORT", modalWindowVP, "before:*");
		conf.add("MAIN_VIEW_PORT", mainVP, "before:*");
		conf.add("STREAM_VIEW_PORT", streamViewPort, "before:*");
	}

}
