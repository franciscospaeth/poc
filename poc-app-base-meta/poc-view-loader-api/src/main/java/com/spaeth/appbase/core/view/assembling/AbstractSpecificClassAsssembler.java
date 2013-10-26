package com.spaeth.appbase.core.view.assembling;

public abstract class AbstractSpecificClassAsssembler<AssemblingType, RepresentationType, ContextType extends Context<RepresentationType>>
		extends AbstractAssembler<RepresentationType, ContextType> {

	private final Class<AssemblingType> assemblingClass;

	public AbstractSpecificClassAsssembler(final Class<AssemblingType> assemblingClass,
			final Assembler<RepresentationType, ContextType> nextAssembler) {
		super(nextAssembler);
		this.assemblingClass = assemblingClass;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void beforeAssembleChain(final Object result, final RepresentationType representation,
			final com.spaeth.appbase.core.view.assembling.Context<RepresentationType> context) {
		if ((result == null) || !this.assemblingClass.isAssignableFrom(result.getClass())) {
			return;
		}
		internalAssemble((AssemblingType) result, representation, context);
	}

	protected abstract void internalAssemble(final AssemblingType result, final RepresentationType representation,
			Context<RepresentationType> context);

}