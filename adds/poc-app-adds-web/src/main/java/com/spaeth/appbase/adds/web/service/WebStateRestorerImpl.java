package com.spaeth.appbase.adds.web.service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spaeth.appbase.core.service.IoCThreadManager;

public class WebStateRestorerImpl implements WebRequestFilter {

	private final HttpGlobals globals;
	private final IoCThreadManager perthreadManager;

	public WebStateRestorerImpl(final HttpGlobals globals, final IoCThreadManager perthreadManager) {
		this.globals = globals;
		this.perthreadManager = perthreadManager;
	}

	@Override
	public void start(final HttpServlet servlet, final HttpServletRequest request, final HttpServletResponse response) {
		this.globals.register(servlet, request, response);
	}

	@Override
	public void end(final HttpServlet servlet, final HttpServletRequest request, final HttpServletResponse response) {
		this.perthreadManager.cleanup();
	}

}
