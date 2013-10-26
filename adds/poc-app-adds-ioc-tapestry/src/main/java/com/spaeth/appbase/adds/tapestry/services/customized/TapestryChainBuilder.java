package com.spaeth.appbase.adds.tapestry.services.customized;

import java.util.List;

import javax.inject.Inject;

import com.spaeth.appbase.core.service.ChainBuilder;

public class TapestryChainBuilder implements ChainBuilder {

	@Inject
	org.apache.tapestry5.ioc.services.ChainBuilder chainBuilder;

	@Override
	public <T> T build(final Class<T> commandInterface, final List<T> commands) {
		return this.chainBuilder.build(commandInterface, commands);
	}

}
