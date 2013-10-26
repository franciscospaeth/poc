package com.spaeth.appbase.core.view.assembling;

public interface Interpreter<RepresentationType> {

	Object interpret(RepresentationType representation, Context<RepresentationType> context);

}
