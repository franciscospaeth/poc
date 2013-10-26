package com.spaeth.appbase.adds.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.image4j.codec.ico.ICOEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.adds.web.service.WebRequestFilter;
import com.spaeth.appbase.core.AppBaseModule;
import com.spaeth.appbase.core.service.FrontRepository;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.appbase.core.service.RepositoryBuilder;
import com.vaadin.Application;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

public class PocAppServletImpl extends AbstractApplicationServlet {

	private static final long serialVersionUID = 1L;
	public static final String REGISTRY_CONTEXT_NAME = "com.spaeth.appbase.ioc.Repository";
	public static final ThreadLocal<Application> application = new ThreadLocal<Application>();

	private final Logger logger = LoggerFactory.getLogger(PocAppServletImpl.class);
	private ServletConfig config;
	private FrontRepository repository;

	@Override
	protected Class<? extends Application> getApplicationClass() throws ClassNotFoundException {
		return Application.class;
	}

	@Override
	protected Application getNewApplication(final HttpServletRequest request) throws ServletException {
		com.spaeth.appbase.Application<?> application = this.repository.getInstance(com.spaeth.appbase.Application.class);
		Object realInstance = application.getInstance();

		if (!(realInstance instanceof Application)) {
			throw new IllegalStateException(String.format("application got from repository should be compatible with %s",
					Application.class.getName()));
		}

		return (Application) realInstance;
	}

	@Override
	public void init(final ServletConfig servletConfig) throws ServletException {
		this.config = servletConfig;
		final ServletContext context = this.config.getServletContext();
		final String modules = servletConfig.getInitParameter("modules");
		this.repository = RepositoryBuilder.build(parseModulesClasses(modules));
		context.setAttribute(REGISTRY_CONTEXT_NAME, this.repository);
		super.init(servletConfig);
	}

	@SuppressWarnings("unchecked")
	private Collection<Class<? extends Module>> parseModulesClasses(final String modules) {
		final String[] classes = modules.split(",");

		final List<Class<? extends Module>> result = new ArrayList<Class<? extends Module>>();
		result.add(AppBaseModule.class);

		for (final String str : classes) {
			try {
				result.add((Class<? extends Module>) Class.forName(str.trim()));
			} catch (final ClassNotFoundException e) {
				throw new IllegalArgumentException("not able to load configured module '" + str.trim() + "' due to " + e.getMessage(), e);
			}
		}

		return result;
	}

	@Override
	public void destroy() {
		super.destroy();
		this.repository.shutdown();
		this.config.getServletContext().removeAttribute(REGISTRY_CONTEXT_NAME);
		this.repository = null;
		this.config = null;
	}

	@Override
	protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final WebRequestFilter webInterationCycleController = this.repository.getInstance(WebRequestFilter.class);
		webInterationCycleController.start(this, request, response);
		application.set((Application) this.repository.getInstance(com.spaeth.appbase.Application.class).getInstance());

		// special handling for favicon
		if (request.getRequestURI().toString().endsWith("/favicon.ico")) {
			returnFavicon(response);
		} else {
			super.service(request, response);
		}

		webInterationCycleController.end(this, request, response);
	}

	protected void returnFavicon(final HttpServletResponse response) throws IOException {
		try {
			response.setContentType("image/vnd.microsoft.icon");
			Resource resource = application.get().getMainWindow().getIcon();
			if (resource != null && resource instanceof StreamResource) {
				BufferedImage bi = ImageIO.read(((StreamResource) resource).getStreamSource().getStream());
				ICOEncoder.write(bi, response.getOutputStream());
				logger.info("perform rendering of main window");
			} else {
				logger.info("not able to read resource in order to convert to favicon");
			}
		} catch (Exception e) {
			logger.warn("error reading image in order to give favicon back: " + e.getMessage(), e);
		}
	}

	public static Application getApplication() {
		return application.get();
	}

}
