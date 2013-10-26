package com.spaeth.appbase.adds.swing;

import java.util.Arrays;

import com.spaeth.appbase.adds.swing.component.Button;
import com.spaeth.appbase.adds.swing.component.CheckBox;
import com.spaeth.appbase.adds.swing.component.ComboBox;
import com.spaeth.appbase.adds.swing.component.DateBox;
import com.spaeth.appbase.adds.swing.component.HorizontalLayout;
import com.spaeth.appbase.adds.swing.component.Menu;
import com.spaeth.appbase.adds.swing.component.MenuBar;
import com.spaeth.appbase.adds.swing.component.MenuItem;
import com.spaeth.appbase.adds.swing.component.MenuOption;
import com.spaeth.appbase.adds.swing.component.PanelTab;
import com.spaeth.appbase.adds.swing.component.PasswordField;
import com.spaeth.appbase.adds.swing.component.StaticLabel;
import com.spaeth.appbase.adds.swing.component.TabbedPanel;
import com.spaeth.appbase.adds.swing.component.TextArea;
import com.spaeth.appbase.adds.swing.component.TextField;
import com.spaeth.appbase.component.VisualComponent.Measure;
import com.spaeth.appbase.component.VisualComponent.MeasureUnit;
import com.spaeth.appbase.component.api.IOrderedLayout.HorizontalLayoutFlow;
import com.spaeth.appbase.event.ViewCloseEvent;
import com.spaeth.appbase.event.ViewShowEvent;
import com.spaeth.appbase.model.MainView;
import com.spaeth.appbase.model.StartupInfo;
import com.spaeth.appbase.model.View;
import com.spaeth.appbase.model.ViewModel;

public class MainViewPortTest {

	public static final class TestView implements View<Object>, MainView {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Object getContent() {
			HorizontalLayout result = new HorizontalLayout(HorizontalLayoutFlow.LEFT_TO_RIGHT);

			Button label1 = new Button();
			label1.setText("test1");

			Button label2 = new Button();
			label2.setText("test2");
			label2.setWidth(new Measure(MeasureUnit.PIXEL, 200));
			label2.setHeight(new Measure(MeasureUnit.PIXEL, 200));

			TextField textField = new TextField();
			textField.setCaption("textField caption:");

			TabbedPanel tabbedPanel = new TabbedPanel();
			tabbedPanel.setWidth(new Measure(MeasureUnit.PIXEL, 200));
			tabbedPanel.setHeight(new Measure(MeasureUnit.PIXEL, 200));
			PanelTab tab1 = new PanelTab();
			tab1.setCaption("test1");
			PanelTab tab2 = new PanelTab();
			tab2.setCaption("test2");
			StaticLabel sl2 = new StaticLabel();
			sl2.setText("Teste e tudo mais só para ver se expande as tabs");
			tab2.addComponent(sl2);

			CheckBox checkbox = new CheckBox();
			checkbox.setCaption("test");

			ComboBox comboBox = new ComboBox();
			comboBox.setCaption("combo Caption");

			DateBox dateBox = new DateBox();
			dateBox.setCaption("date box");

			tab1.addComponent(dateBox);
			tab1.addComponent(checkbox);
			tab1.addComponent(comboBox);

			Measure width = new Measure(com.spaeth.appbase.component.VisualComponent.MeasureUnit.PIXEL, 400);
			tabbedPanel.setWidth(width);

			result.addComponent(tabbedPanel);
			result.addComponent(label1);
			result.addComponent(label2);
			result.addComponent(textField);

			MenuBar menuBar = new MenuBar();
			Menu menu = new Menu();
			menu.setText("teste");
			MenuItem menu1 = new MenuOption();
			menu1.setText("teste");
			menuBar.setOptions(Arrays.<com.spaeth.appbase.component.MenuItem> asList(menu));
			menu.setOptions(Arrays.<com.spaeth.appbase.component.MenuItem> asList(menu1));

			tab2.addComponent(menuBar);

			PanelTab tab3 = new PanelTab();

			PasswordField passwordField = new PasswordField();
			passwordField.setCaption("password");
			tab3.addComponent(passwordField);

			TextArea textArea = new TextArea();
			textArea.setCaption("text area");
			tab3.addComponent(textArea);

			tabbedPanel.addTab(tab3);
			tabbedPanel.addTab(tab2);
			tabbedPanel.addTab(tab1);

			return result;
		}

		@Override
		public void initialize(final ViewModel viewModel, final StartupInfo startupInfo) {
		}

		@Override
		public void onShown(final ViewShowEvent event) {
		}

		@Override
		public void close() {
		}

		@Override
		public ViewModel getModel() {
			return null;
		}

		@Override
		public void onClose(final ViewCloseEvent event) {
		}

	}

	public static void main(final String[] args) {
		SwingApplication application = new SwingApplication(null);
		MainViewPort mainViewPort = new MainViewPort(application);
		mainViewPort.showView(new TestView());
	}

}
