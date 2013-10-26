package com.spaeth.appbase.adds.xmlview.service;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.model.ViewModel;

public interface XmlViewContextFactory {

	XmlViewContext createContext(ViewModel viewModel);
	
}
