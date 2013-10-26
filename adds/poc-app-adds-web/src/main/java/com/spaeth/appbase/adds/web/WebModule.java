package com.spaeth.appbase.adds.web;

import java.util.List;

import com.spaeth.appbase.adds.web.service.HttpGlobals;
import com.spaeth.appbase.adds.web.service.HttpGlobalsImpl;
import com.spaeth.appbase.adds.web.service.SessionStateHolder;
import com.spaeth.appbase.adds.web.service.WebRequestFilter;
import com.spaeth.appbase.adds.web.service.WebStateRestorerImpl;
import com.spaeth.appbase.core.annotations.BuilderMethod;
import com.spaeth.appbase.core.annotations.ConfigurationMethod;
import com.spaeth.appbase.core.marker.Principal;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.ChainBuilder;
import com.spaeth.appbase.core.service.Configurator.OrderedConfiguration;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.appbase.core.service.ScopeConstants;
import com.spaeth.appbase.core.service.StateHolder;

public class WebModule implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.new Binding(HttpGlobals.class, HttpGlobalsImpl.class).withScope(ScopeConstants.THREAD_SCOPE);
		binder.new Binding(StateHolder.class, SessionStateHolder.class);
	}

	@BuilderMethod
	@Principal
	public WebRequestFilter buildWebRequestFilter(final List<WebRequestFilter> filters, final ChainBuilder chainBuilder) {
		return chainBuilder.build(WebRequestFilter.class, filters);
	}

	@ConfigurationMethod(WebRequestFilter.class)
	@Principal
	public void configureWebRequestFilter(final OrderedConfiguration<WebRequestFilter> filters) {
		filters.addInstance("GLOBALS_RESTORER", WebStateRestorerImpl.class, "before:*");
	}

}
