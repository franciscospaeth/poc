package com.spaeth.appbase.core.service;

import java.util.ArrayList;
import java.util.List;

public final class Binder {

	private final List<Binding> bindings = new ArrayList<Binding>();

	@Deprecated
	public Binding bind(final Class<?> implementation) {
		return new Binding(implementation);
	}

	@Deprecated
	public <M> Binding bind(final Class<M> bindInterface, final Class<? extends M> implementation) {
		return new Binding(bindInterface, implementation);
	}

	public void accept(final BinderVisitor visitor) {
		for (Binding i : bindings) {
			visitor.visit(i);
		}
	}

	private void addBinding(final Binding binding) {
		this.bindings.add(binding);
	}

	/**
	 * Represents a binding done of an interface to an implementation of just an
	 * implementation.
	 * 
	 * @author spaeth
	 * 
	 */
	public class Binding {

		private final Class<?> interf;
		private final Class<?> implem;
		private String scope = ScopeConstants.SINGLETON_SCOPE;
		private boolean eager = false;
		private Class<?> marker = null;

		public Binding(final Class<?> implem) {
			super();
			this.implem = implem;
			this.interf = null;
			addBinding(this);
		}

		public <M> Binding(final Class<M> interf, final Class<? extends M> implem) {
			super();
			this.interf = interf;
			this.implem = implem;
			addBinding(this);
		}

		public Class<?> getInterf() {
			return this.interf;
		}

		public Class<?> getImplem() {
			return this.implem;
		}

		public String getScope() {
			return this.scope;
		}

		public boolean isEager() {
			return this.eager;
		}

		public Class<?> getMarker() {
			return marker;
		}

		public Binding withScope(final String scope) {
			this.scope = scope;
			return this;
		}

		public Binding eager() {
			this.eager = true;
			return this;
		}

		public Binding withMarker(final Class<?> marker) {
			this.marker = marker;
			return this;
		}

	}

	/**
	 * Visitor for bindings.
	 * 
	 * @author spaeth
	 * 
	 */
	public interface BinderVisitor {

		void visit(Binding binding);

	}

}
