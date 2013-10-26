package com.spaeth.appbase.adds.web.service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpGlobalsImpl implements HttpGlobals {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpServlet servlet;

	public HttpGlobalsImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.spaeth.appbase.tapestry.RequestGlobals#register(javax.servlet.http
	 * .HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void register(HttpServlet servlet, final HttpServletRequest request, final HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.servlet = servlet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spaeth.appbase.tapestry.RequestGlobals#getRequest()
	 */
	@Override
	public HttpServletRequest getRequest() {
		return this.request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spaeth.appbase.tapestry.RequestGlobals#getResponse()
	 */
	@Override
	public HttpServletResponse getResponse() {
		return this.response;
	}

	@Override
	public HttpServlet getServlet() {
		return servlet;
	}

}
