package com.spaeth.appbase.model;

import java.io.InputStream;

/**
 * View characterization for file views, meaning that this will result in a file
 * or download action.
 * 
 * @author "Francisco Spaeth (francisco.spaeth@gmail.com)"
 * 
 */
public interface StreamView extends View<InputStream> {

	String getName();

}
