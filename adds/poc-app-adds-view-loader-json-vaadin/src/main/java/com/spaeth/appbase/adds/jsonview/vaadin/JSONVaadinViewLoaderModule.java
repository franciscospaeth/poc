package com.spaeth.appbase.adds.jsonview.vaadin;

import java.util.List;

import com.spaeth.appbase.adds.jsonview.assembling.DefaultJSONCreator;
import com.spaeth.appbase.adds.jsonview.assembling.JSONAssembler;
import com.spaeth.appbase.adds.jsonview.assembling.JSONContextBuilder;
import com.spaeth.appbase.adds.jsonview.assembling.JSONInterpreter;
import com.spaeth.appbase.adds.jsonview.assembling.ReferenceableObjectCreator;
import com.spaeth.appbase.adds.jsonview.vaadin.assembling.PagerJSONAssembler;
import com.spaeth.appbase.adds.jsonview.vaadin.assembling.VaadinButtonJSONAssembler;
import com.spaeth.appbase.adds.jsonview.vaadin.assembling.VaadinComponentContainerJSONAssembler;
import com.spaeth.appbase.adds.jsonview.vaadin.assembling.VaadinContainerViewerJSONAssembler;
import com.spaeth.appbase.adds.jsonview.vaadin.assembling.VaadinPropertyViewerJSONAssembler;
import com.spaeth.appbase.adds.jsonview.vaadin.assembling.VaadinValidatableJSONAssembler;
import com.spaeth.appbase.adds.vaadin.component.Pager;
import com.spaeth.appbase.core.service.Binder;
import com.spaeth.appbase.core.service.BuildRepository;
import com.spaeth.appbase.core.service.Builder;
import com.spaeth.appbase.core.service.Configurator;
import com.spaeth.appbase.core.service.Decorator;
import com.spaeth.appbase.core.service.Module;
import com.spaeth.appbase.core.service.Overrider;
import com.spaeth.appbase.core.services.ActionProviderFacade;
import com.spaeth.appbase.core.view.assembling.Assembler;
import com.spaeth.appbase.core.view.assembling.ScriptInterpreter;

public class JSONVaadinViewLoaderModule implements Module {

	@Override
	public void bind(final Binder binder) {
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void build(final Builder builder) {
		builder.build(ScriptInterpreter.class, new Builder.BuildTemplate<ScriptInterpreter>() {
			@Override
			public ScriptInterpreter build(BuildRepository repository) {
				return new JavaScriptInterpreter();
			}
		});
		
		builder.build(JSONInterpreter.class, new Builder.BuildTemplate<JSONInterpreter>() {
			@Override
			public JSONInterpreter build(final BuildRepository repository) {
				// interpreter
				final DefaultJSONCreator defaultJSONCreator = new DefaultJSONCreator();
				final ReferenceableObjectCreator rootObjectCreator = new ReferenceableObjectCreator(defaultJSONCreator);
				final JSONInterpreter interpreter = new JSONInterpreter(rootObjectCreator);
				return interpreter;
			}
		});

		builder.build(JSONContextBuilder.class, new Builder.BuildTemplate<JSONContextBuilder>() {
			@SuppressWarnings("unchecked")
			@Override
			public JSONContextBuilder build(final BuildRepository repository) {
				Assembler asm = repository.getInstance(Assembler.class);
				JSONInterpreter interpreter = repository.getInstance(JSONInterpreter.class);
				ActionProviderFacade actionProvider = repository.getInstance(ActionProviderFacade.class);
				ScriptInterpreter scriptInterpreter = repository.getInstance(ScriptInterpreter.class);
				return new JSONContextBuilder(asm, interpreter, actionProvider, scriptInterpreter);
			}
		});

		builder.build(Assembler.class, new Builder.BuildTemplate<Assembler>() {
			@Override
			public Assembler build(final BuildRepository repository) {
				final VaadinPropertyViewerJSONAssembler propertyAssembler = new VaadinPropertyViewerJSONAssembler(
						new JSONAssembler());
				final VaadinValidatableJSONAssembler validatableAssembler = new VaadinValidatableJSONAssembler(
						propertyAssembler);
				final VaadinContainerViewerJSONAssembler containerAssembler = new VaadinContainerViewerJSONAssembler(
						validatableAssembler);
				final VaadinButtonJSONAssembler buttonAssembler = new VaadinButtonJSONAssembler(containerAssembler);
				final PagerJSONAssembler pagerJSONAssembler = new PagerJSONAssembler(Pager.class, buttonAssembler);
				final VaadinComponentContainerJSONAssembler assembler = new VaadinComponentContainerJSONAssembler(
						pagerJSONAssembler);

				return assembler;
			}
		});
	}

	@Override
	public void configure(final Configurator configurator) {
	}

	@Override
	public void decorate(final Decorator decorator) {
	}

	@Override
	public void override(final Overrider overrider) {
	}

	@Override
	public List<Class<? extends Module>> requires() {
		return null;
	}

}