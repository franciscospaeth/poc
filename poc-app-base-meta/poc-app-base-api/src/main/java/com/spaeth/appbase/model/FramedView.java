package com.spaeth.appbase.model;

/**
 * View characterization for framed view, meaning the view shall present title,
 * icon and give the option to be closed or not.
 * 
 * @author "Francisco Spaeth (francisco.spaeth@gmail.com)"
 * 
 * @param <ResultContent>
 */
public interface FramedView extends TitledView, IconedView {

	boolean isClosable();

}
