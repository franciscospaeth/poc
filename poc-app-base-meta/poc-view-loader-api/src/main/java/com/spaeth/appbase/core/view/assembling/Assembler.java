package com.spaeth.appbase.core.view.assembling;

public interface Assembler<RepresentationType, ContextType extends Context<RepresentationType>> {

	void assemble(Object result, RepresentationType representation, ContextType context);

}
