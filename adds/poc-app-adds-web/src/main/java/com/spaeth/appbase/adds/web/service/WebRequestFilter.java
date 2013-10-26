package com.spaeth.appbase.adds.web.service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Applied in order to handle low level requests.
 * 
 * @author "Francisco Spaeth (francisco.spaeth@gmail.com)"
 * 
 */
public interface WebRequestFilter {

	public void start(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response);

	public void end(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response);

}
