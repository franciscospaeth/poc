package com.spaeth.appbase.service;

import com.spaeth.appbase.model.StartupInfo;

/**
 * Service (front-controller) responsible to coordinate the model creation, view
 * creation and delegation to the right view port.
 * 
 * @author "Francisco Spaeth (francisco.spaeth@gmail.com)"
 * 
 */
public interface ApplicationController {

	void process(final StartupInfo startupInfo);

}
