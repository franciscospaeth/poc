package com.spaeth.appbase.adds.xmlview.service;

import javax.inject.Inject;

import com.spaeth.appbase.adds.xmlview.DefaultViewXmlContext;
import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.core.service.ActionProviderFacade;
import com.spaeth.appbase.core.service.ActionProviderFacadeActionProviderAdapter;
import com.spaeth.appbase.model.ViewModel;
import com.spaeth.appbase.service.ActionProvider;
import com.spaeth.appbase.service.ResourceLoader;

public class DefaultXmlViewContextFactory implements XmlViewContextFactory {

	private FrontXmlTranslator frontXmlTranslator;
	private ActionProviderFacade actionProviderFacade;
	private ResourceLoader resourceLoader;

	@Inject
	public DefaultXmlViewContextFactory(FrontXmlTranslator frontXmlTranslator, ActionProviderFacade actionProviderFacade, ResourceLoader resourceLoader) {
		super();
		this.frontXmlTranslator = frontXmlTranslator;
		this.actionProviderFacade = actionProviderFacade;
		this.resourceLoader = resourceLoader;
	}

	@Override
	public XmlViewContext createContext(final ViewModel viewModel) {
		ActionProvider actionProvider = new ActionProviderFacadeActionProviderAdapter(actionProviderFacade, viewModel);
		return new DefaultViewXmlContext(frontXmlTranslator, actionProvider, viewModel, resourceLoader);
	}

}
