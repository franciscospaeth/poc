Unified Component Collection
============================

The main idea behing Unified Component Collection is the ability to implement an application prototype that can be deployed to different envoriments and different front-end frameworks.

Current implementation supports Vaadin and draft implementation for Swing is available.

Sample
============================
    
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
    
    	public void initialize(ViewModel viewModel, StartupInfo startupInfo) {
    	}
    
    	public void onShown(ViewShowEvent event) {
    	}
    
    	public void close() {
    	}
    
    	public void onClose(ViewCloseEvent event) {
    	}
    
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
