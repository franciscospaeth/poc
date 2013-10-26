package com.spaeth.appbase.core.datasource;

/**
 * Creation template in order to be used in operation that a instance needs to
 * be created based on a given template.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 * @param <CreationType>
 *            type returned by the creation process
 * @param <ContextType>
 *            context provided for the creation of resultant object
 */
public interface CreateTemplate<CreationType, ContextType> {

	/**
	 * Shall create an object based on the given context.
	 * 
	 * @param context
	 *            object provided from the environment in order to let the
	 *            implementor decide how to create the object
	 * @return object created based on the given context
	 */
	CreationType create(ContextType context);

}
