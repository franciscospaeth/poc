package com.spaeth.appbase.component;

public interface WrappedComponent<ComponentType extends Component> extends Component {

	Class<? extends Component> getComponentClass();

	ComponentType getDelegated();

}
