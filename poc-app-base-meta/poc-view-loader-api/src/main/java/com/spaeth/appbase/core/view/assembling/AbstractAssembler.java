package com.spaeth.appbase.core.view.assembling;

public abstract class AbstractAssembler<RepresentationType, ContextType extends Context<RepresentationType>> implements
		Assembler<RepresentationType, ContextType> {

	private final Assembler<RepresentationType, ContextType> nextAssembler;

	public AbstractAssembler(final Assembler<RepresentationType, ContextType> nextAssembler) {
		super();
		this.nextAssembler = nextAssembler;
	}

	@Override
	public final void assemble(final Object result, final RepresentationType jsonObject, final ContextType context) {
		beforeAssembleChain(result, jsonObject, context);
		if ((this.nextAssembler != null) && !arbitraryEndOfChain(result, jsonObject, context)) {
			this.nextAssembler.assemble(result, jsonObject, context);
		}
		afterAssembleChain(result, jsonObject, context);
	}

	protected void afterAssembleChain(final Object result, final RepresentationType jsonObject,
			final Context<RepresentationType> context) {
	}

	protected void beforeAssembleChain(final Object result, final RepresentationType jsonObject,
			final Context<RepresentationType> context) {
	}

	protected boolean arbitraryEndOfChain(final Object result, final RepresentationType jsonObject,
			final Context<RepresentationType> context) {
		return false;
	}

}
