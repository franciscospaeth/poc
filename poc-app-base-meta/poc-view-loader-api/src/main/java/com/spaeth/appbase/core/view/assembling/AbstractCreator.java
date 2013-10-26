package com.spaeth.appbase.core.view.assembling;

public abstract class AbstractCreator<RepresentationType> implements Creator<RepresentationType> {

	private final Creator<RepresentationType> nextCreator;

	public AbstractCreator() {
		this.nextCreator = null;
	}

	public AbstractCreator(final Creator<RepresentationType> nextCreator) {
		super();
		this.nextCreator = nextCreator;
	}

	public final Object createObjectForRepresentation(final RepresentationType representation,
			final Context<RepresentationType> context) {
		final Object createdObject = executeCreation(representation, context);
		if (createdObject != null) {
			return createdObject;
		}
		if (this.nextCreator == null) {
			return null;
		}
		return this.nextCreator.createObjectForRepresentation(representation, context);
	}

	public Object executeCreation(final RepresentationType representation,
			final Context<RepresentationType> creatorContext) {
		return null;
	}

}
