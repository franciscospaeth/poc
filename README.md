Unified Component Collection
============================

The main idea behind Unified Component Collection is the ability to implement an application prototype that can be deployed to different environments and different front-end frameworks.

Current implementation supports Vaadin and draft implementation for Swing is available.

Sample
============================
```java
package foo.bar.view;

...
import foo.bar.model.Person;
...

public class PreviewMainView implements View<ComponentContainer>, MainView {

	private static final long serialVersionUID = 1L;
	private DataSource ds;

	public ComponentContainer getContent() {
		return createFirstContainer();
	}

	private ComponentContainer createFirstContainer() {
		HorizontalLayout result = new HorizontalLayout();

		result.setWidth(new Measure(MeasureUnit.PERCENTAGE, 50));
		result.setHeight(new Measure(MeasureUnit.PERCENTAGE, 100));

		Label title = new Label();
		title.setDataSource(getDataSource().getDataSource("name"));
		title.setHeight(new Measure(MeasureUnit.PERCENTAGE, 30));
		result.addComponent(title);

		TextField personName = new TextField();
		personName.setDataSource(getDataSource().getDataSource("name"));
		personName.setHeight(new Measure(MeasureUnit.PIXEL, 30));
		result.addComponent(personName);

		TextField personAge = new TextField();
		personAge.setDataSource(getDataSource().getDataSource("age"));
		personAge.setHeight(new Measure(MeasureUnit.PIXEL, 30));
		result.addComponent(personAge);

		result.setExpandRation(new float[] { 1, 1, 1 });

		MenuBar menu = new MenuBar();
		Menu m = new Menu();
		m.setText("Sample MenuItem");
		MenuOption menuOption = new MenuOption(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void internalExecute(ActionParameters parameters) {
				ds.getDataSource("name").set("Action Executed");
			}

			@Override
			protected ActionUpdateEvent createActionEvent() {
				return new ActionUpdateEvent(this, this);
			}

		});
		menuOption.setText("Execute Action");
		List<MenuItem> options = Collections.<MenuItem> singletonList(menuOption);

		m.setOptions(options);
		menu.setOptions(Collections.<MenuItem> singletonList(m));

		result.addComponent(menu);

		getDataSource().set(new Person("Test Name", 28));

		return result;
	}

	public void initialize(ViewModel viewModel, StartupInfo startupInfo) {}

	public void onShown(ViewShowEvent event) {}

	public void close() {}

	public void onClose(ViewCloseEvent event) {}

	public ViewModel getModel() {
		return null;
	}

	private DataSource getDataSource() {
		if (ds == null) {
			ds = new BeanDataSourceBuilder(Person.class) //
					.addDataSource(new BeanDataSourceBuilder("representation")) //
					.addDataSource(new BeanDataSourceBuilder("name")) //
					.addDataSource(new BeanDataSourceBuilder("age")) //
					.build();
		}
		return ds; //
	}

}
```
The sample class exposed below show how could a view be implemented.

In order to have the view running, a initialization listener (kind of entry point) needs to be implemented and an application module needs to be declared.

A sample of initialization listener would be something like:

```java
package foo.bar;

import javax.inject.Inject;

import com.spaeth.appbase.event.ApplicationInitListener;
import com.spaeth.appbase.event.ApplicationInitializationEvent;
import com.spaeth.appbase.service.ApplicationController;

import foo.bar.startup.MainViewStartupInfo;

public class PreviewInitListener implements ApplicationInitListener{

	@Inject
	private ApplicationController applicationController;
	
	public void applicationInitialized(ApplicationInitializationEvent event) {
		applicationController.process(new MainViewStartupInfo());
	}

}
```

This basically says to the application controller that there is an intention to show a view. A module for the current application would be something similar to:

```java
package foo.bar;

import com.spaeth.appbase.core.annotations.ConfigurationMethod;
import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.ClassBasedRepositoryViewProvider;
import com.spaeth.appbase.core.service.Configurator.MappedConfiguration;
import com.spaeth.appbase.core.service.Configurator.OrderedConfiguration;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.appbase.core.service.ScopeConstants;
import com.spaeth.appbase.event.ApplicationInitListener;
import com.spaeth.appbase.model.View;

import foo.bar.dao.PersonRepository;
import foo.bar.startup.MainViewStartupInfo;
import foo.bar.view.PreviewMainView;

public class PreviewModule implements Module {

	public void configure(final Binder binder) {
		binder.new Binding(PersonRepository.class).withScope(ScopeConstants.SESSION_SCOPE);
	}

	@ConfigurationMethod(ApplicationInitListener.class)
	@Principal
	public void configureApplicationInitListener(final OrderedConfiguration<ApplicationInitListener> config) {
		config.addInstance("init", PreviewInitListener.class);
	}

	@ConfigurationMethod(ClassBasedRepositoryViewProvider.class)
	public void configureClassBasedRepositoryViewProvider(final MappedConfiguration<Class<?>, Class<? extends View<?>>> config) {
		config.add(MainViewStartupInfo.class, PreviewMainView.class);
	}

}
```

The idea behind this process is basically that there is a bunch of views implementation available. Every time a a `StartupInfo` object is processed by the `ApplicationController` a suitable view implementation will be selected, instantiated, associated to a `ViewModel` (depending on implementation) and exposed by a `ViewPort`.

A startup implementation could hold contextualized information in order to setup a View, once it is used during view initialization `public void initialize(ViewModel viewModel, StartupInfo startupInfo)`. A simple sample of a `StartupInfo`:

```java
package foo.bar.startup;

import com.spaeth.appbase.model.StartupInfo;

public class MainViewStartupInfo extends StartupInfo {

    private final String fileName;

    public MainViewStartupInfo(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFileName() {
        return fileName;
    }

}
```

In order to show a view, it is submited to a chain of `ViewPort`s, and it can decide whether or not to show the view based, among other things, on the `View` characterization. Sample of view characterizations are: `ModalView`, `TitledView`, `MainView`, `IconedView`
