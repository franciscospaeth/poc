package com.spaeth.appbase.adds.web.service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpGlobals {

	void register(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response);

	HttpServletRequest getRequest();

	HttpServletResponse getResponse();

	HttpServlet getServlet();

}
