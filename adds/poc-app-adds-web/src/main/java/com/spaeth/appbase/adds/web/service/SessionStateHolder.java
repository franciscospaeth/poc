package com.spaeth.appbase.adds.web.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.core.service.StateHolder;

public class SessionStateHolder implements StateHolder {

	private final Logger logger = LoggerFactory.getLogger(SessionStateHolder.class);

	private final static String prefix = "SESSION_STATE_HOLDER.";

	@Inject
	private HttpGlobals httpGlobals;

	@Override
	public <M> M getObject(final Class<M> objectClass) {
		String sessionName = getSessionName(objectClass);
		Object stored = getSession().getAttribute(sessionName);
		logger.info("retrieved {}:now on session: {}", objectClass, getSession().getAttributeNames());
		return objectClass.cast(stored);
	}

	private String getSessionName(final Class<?> objectClass) {
		return String.format("%s%s", prefix, objectClass.getName());
	}

	@Override
	public <M> void addObject(final M object, final Class<? super M> objectClass) {
		String sessionName = getSessionName(objectClass);
		getSession().setAttribute(sessionName, object);
		logger.info("saved:now on session: {}", describeAttributes(getSession()));
	}

	@Override
	public <M> M removeObject(final Class<M> objectClass) {
		M result = getObject(objectClass);
		getSession().removeAttribute(getSessionName(objectClass));
		logger.info("removed:now on session: {}", getSession().getAttributeNames());
		return result;
	}

	@Override
	public void clear() {
		Enumeration<?> names = getSession().getAttributeNames();
		Object o;
		while ((o = names.nextElement()) != null) {
			String name = String.valueOf(o);
			if (name.startsWith(prefix)) {
				getSession().removeAttribute(name);
			}
		}
	}

	HttpSession getSession() {
		return httpGlobals.getRequest().getSession();
	}

	public void setHttpGlobals(final HttpGlobals httpGlobals) {
		this.httpGlobals = httpGlobals;
	}

	private Object describeAttributes(final HttpSession session) {
		List<Object> attributeNames = new ArrayList<Object>();
		@SuppressWarnings("rawtypes")
		Enumeration rawAttributeNames = session.getAttributeNames();
		while (rawAttributeNames.hasMoreElements()) {
			attributeNames.add(rawAttributeNames.nextElement());
		}
		return attributeNames.toString();
	}

}
