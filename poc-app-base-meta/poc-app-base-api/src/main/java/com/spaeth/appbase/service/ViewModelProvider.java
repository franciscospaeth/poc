package com.spaeth.appbase.service;

import com.spaeth.appbase.model.StartupInfo;
import com.spaeth.appbase.model.ViewModel;

/**
 * Service responsible to provide a model for a given startup info.
 * 
 * @author "Francisco Spaeth (francisco.spaeth@gmail.com)"
 * 
 */
public interface ViewModelProvider {

	ViewModel getModelFor(StartupInfo startupInfo);

}
