package com.spaeth.appbase.component;

import java.util.List;

public interface ComponentContainer extends Component {

	void addComponent(DetacheableComponent component);

	void removeComponent(DetacheableComponent component);

	List<DetacheableComponent> getComponents();

}
