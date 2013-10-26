package com.spaeth.appbase.event;

import java.io.Serializable;
import java.util.EventListener;

public interface ActionUpdateEventListener extends EventListener, Serializable {

	void actionUpdated(ActionUpdateEvent actionUpdateEvent);

}
