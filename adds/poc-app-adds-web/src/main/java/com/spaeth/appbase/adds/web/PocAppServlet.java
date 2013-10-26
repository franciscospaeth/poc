package com.spaeth.appbase.adds.web;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PocAppServlet implements Servlet {

	private final String EXPECTED_SERVLET_CLASS_NAME = "com.spaeth.appbase.adds.web.PocAppServletImpl";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Servlet delegated;

	public PocAppServlet() {
		try {
			Class<?> componentFactoryClass = Class.forName(EXPECTED_SERVLET_CLASS_NAME);
			delegated = (Servlet) componentFactoryClass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException(String.format("implementation '%s' not found in order to build servlet",
					EXPECTED_SERVLET_CLASS_NAME));
		}
	}

	@Override
	public void init(final ServletConfig config) throws ServletException {
		delegated.init(config);
	}

	@Override
	public ServletConfig getServletConfig() {
		return delegated.getServletConfig();
	}

	@Override
	public void service(final ServletRequest req, final ServletResponse res) throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		logger.debug("profiling: starting timing at " + startTime);
		delegated.service(req, res);
		long endTime = System.currentTimeMillis();
		logger.debug("profiling: end time is {}, total request process time: {}ms", endTime, endTime - startTime);
	}

	@Override
	public String getServletInfo() {
		return delegated.getServletInfo();
	}

	@Override
	public void destroy() {
		delegated.destroy();
	}

}
