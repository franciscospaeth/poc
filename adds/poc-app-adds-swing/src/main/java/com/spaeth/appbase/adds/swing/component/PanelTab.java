package com.spaeth.appbase.adds.swing.component;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.spaeth.appbase.adds.swing.ComponentWrapperHelper;
import com.spaeth.appbase.component.DetacheableComponent;
import com.spaeth.appbase.component.StreamProvider;
import com.spaeth.appbase.component.VisualComponent.Measure;
import com.spaeth.appbase.component.VisualComponent.MeasureUnit;
import com.spaeth.appbase.component.api.IOrderedLayout.VerticalLayoutFlow;
import com.spaeth.appbase.component.api.IPanelTab;

public class PanelTab extends Component<JPanel> implements IPanelTab {

	private static final long serialVersionUID = 1L;

	private final OrderedLayout componentContainer;

	private String caption;
	private StreamProvider icon;
	private boolean closable;
	private JTabbedPane parent = null;

	public PanelTab() {
		this.componentContainer = new VerticalLayout(VerticalLayoutFlow.TOP_DOWN);
		this.componentContainer.setWidth(new Measure(MeasureUnit.PERCENTAGE, 100));
		this.componentContainer.setHeight(new Measure(MeasureUnit.PERCENTAGE, 100));
		initialize();
	}

	public PanelTab(final OrderedLayout componentContainer) {
		this.componentContainer = componentContainer;
		initialize();
	}

	private void initialize() {
		JPanel panel = getDelegated();
		panel.setLayout(new BorderLayout());
		panel.add(componentContainer.getDelegated());
	}

	@Override
	protected JPanel createDelegated() {
		return new JPanel();
	}

	OrderedLayout getContent() {
		return componentContainer;
	}

	@Override
	void internalSetParent(final com.spaeth.appbase.component.Component parent) {
		if (!(parent instanceof TabbedPanel)) {
			throw new IllegalArgumentException(String.format("a tab could be added just on %s components",
					TabbedPanel.class.getName()));
		}

		JTabbedPane tabbedPane = ComponentWrapperHelper.unwrap(parent, TabbedPanel.class).getDelegated();

		tabbedPane.addTab(caption, icon != null ? new StreamProviderIconAdapter(icon) : null, getDelegated());

		super.internalSetParent(parent);

		this.parent = tabbedPane;
	}

	@Override
	public void setCaption(final String caption) {
		this.caption = caption;
		synchronizeParentComponent();
	}

	@Override
	public String getCaption() {
		return caption;
	}

	@Override
	public void setClosable(final boolean closable) {
		this.closable = closable;
		synchronizeParentComponent();
	}

	@Override
	public boolean isClosable() {
		return closable;
	}

	@Override
	public void setVisible(final boolean visible) {
		getDelegated().setVisible(visible);
		componentContainer.setVisible(visible);
	}

	@Override
	public boolean isVisible() {
		return getDelegated().isVisible();
	}

	@Override
	public void addComponent(final DetacheableComponent component) {
		componentContainer.addComponent(component);
	}

	@Override
	public void removeComponent(final com.spaeth.appbase.component.DetacheableComponent component) {
		componentContainer.removeComponent(component);
	}

	@Override
	public List<DetacheableComponent> getComponents() {
		return componentContainer.getComponents();
	}

	@Override
	public void setIcon(final StreamProvider iconStreamProvider) {
		this.icon = iconStreamProvider;

	}

	@Override
	public StreamProvider getIcon() {
		return icon;
	}

	private void synchronizeParentComponent() {
		if (parent == null) {
			return;
		}
		int indexOf = Arrays.asList(parent.getComponents()).indexOf(getDelegated());
		if (this.parent != null && indexOf > -1) {
			parent.setTitleAt(indexOf, caption);
			if (icon == null) {
				parent.setIconAt(indexOf, null);
			} else {
				parent.setIconAt(indexOf, new StreamProviderIconAdapter(icon));
			}
		}
	}

}
