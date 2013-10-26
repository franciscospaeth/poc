package com.spaeth.appbase.adds.tapestry.services.processor;

import java.util.UUID;

import org.apache.tapestry5.ioc.ServiceBindingOptions;
import org.apache.tapestry5.ioc.internal.ServiceBinderImpl;

import com.spaeth.appbase.core.service.Binder.BinderVisitor;
import com.spaeth.appbase.core.service.Binder.Binding;

public class BinderFlushingVisitor implements BinderVisitor {

	private final ServiceBinderImpl binder;

	public BinderFlushingVisitor(final ServiceBinderImpl binder) {
		super();
		this.binder = binder;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void visit(final Binding binding) {
		ServiceBindingOptions opts;
		if (binding.getInterf() == null) {
			opts = binder.bind(binding.getImplem());
		} else {
			opts = binder.bind((Class) binding.getInterf(), (Class) binding.getImplem());
		}
		opts.withId(UUID.randomUUID().toString());
		if (binding.getMarker() != null) {
			opts.withMarker((Class) binding.getMarker());
		}
		if (binding.isEager()) {
			opts.eagerLoad();
		}
		opts.scope(binding.getScope());
	}

}
