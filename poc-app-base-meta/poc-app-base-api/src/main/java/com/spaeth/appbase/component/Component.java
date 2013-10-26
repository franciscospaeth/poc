package com.spaeth.appbase.component;

import java.io.Serializable;

public interface Component extends Serializable {

	Component getParent();

}
