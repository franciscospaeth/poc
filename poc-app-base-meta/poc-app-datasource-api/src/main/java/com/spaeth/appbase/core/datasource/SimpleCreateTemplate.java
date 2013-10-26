package com.spaeth.appbase.core.datasource;

/**
 * Default implementation for {@link CreateTemplate}, that instantiate an object
 * dynamically.
 * 
 * This class can be used as a parent class for future implementation as it
 * works with a template method {@link #executeCreation()} that is composed by
 * {@link #executeCreation(Object)} and {@link #complement(Object, Object)}.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 * @param <CreationType>
 * @param <ContextType>
 */
public class SimpleCreateTemplate<CreationType, ContextType> implements CreateTemplate<CreationType, ContextType> {

	private Class<? extends CreationType> classToInstantiate;

	public SimpleCreateTemplate(Class<? extends CreationType> classToInstantiate) {
		super();
		this.classToInstantiate = classToInstantiate;
	}

	@Override
	public final CreationType create(ContextType context) {
		CreationType result = executeCreation(context);
		complement(result, context);
		return result;
	}

	protected CreationType executeCreation(ContextType context) {
		try {
			return classToInstantiate.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void complement(CreationType created, ContextType context) {
	}

}
