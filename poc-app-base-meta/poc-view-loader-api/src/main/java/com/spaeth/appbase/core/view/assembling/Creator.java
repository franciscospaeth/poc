package com.spaeth.appbase.core.view.assembling;

public interface Creator<RepresentationType> {

	Object createObjectForRepresentation(RepresentationType representation, Context<RepresentationType> context);

}
