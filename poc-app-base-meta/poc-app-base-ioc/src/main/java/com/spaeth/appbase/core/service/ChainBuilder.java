package com.spaeth.appbase.core.service;

import java.util.List;

public interface ChainBuilder {

	<T> T build(Class<T> commandInterface, List<T> commands);

}
