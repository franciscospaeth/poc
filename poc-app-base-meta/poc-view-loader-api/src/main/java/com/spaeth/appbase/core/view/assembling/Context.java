package com.spaeth.appbase.core.view.assembling;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.spaeth.appbase.model.ViewModel;
import com.spaeth.appbase.service.ActionProvider;

public abstract class Context<RepresentationType> {

	private final Interpreter<RepresentationType> interpreter;
	private final Assembler<RepresentationType, ? extends Context<RepresentationType>> rootAssembler;
	private final Map<String, Class<?>> usesDeclarations = new HashMap<String, Class<?>>();
	private final ViewModel viewModel;
	private final ActionProvider actionProvider;
	private final Map<String, Object> contextObjects = new HashMap<String, Object>();
	private final ScriptInterpreter scriptInterpreter;

	public Context(final Interpreter<RepresentationType> interpreter,
			final Assembler<RepresentationType, ? extends Context<RepresentationType>> rootAssembler,
			final ViewModel viewModel, ActionProvider actionProvider, ScriptInterpreter scriptInterpreter) {
		super();
		this.viewModel = viewModel;
		this.interpreter = interpreter;
		this.rootAssembler = rootAssembler;
		this.actionProvider = actionProvider;
		this.scriptInterpreter = scriptInterpreter;
	}

	public void addUsesDeclaration(final String usesName, final Class<?> usedClass) {
		this.usesDeclarations.put(usesName, usedClass);
	}

	public Class<?> getUsedClass(final String name) {
		return this.usesDeclarations.get(name);
	}

	public Object interpret(final RepresentationType representation) {
		return this.interpreter.interpret(representation, this);
	}

	public void objectCreated(final String name, final Object object) {
		addContextObject(name, object);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void assemble(final Object result, final RepresentationType jsonObject) {
		((Assembler) (this.rootAssembler)).assemble(result, jsonObject, this);
	}

	public ViewModel getViewModel() {
		return this.viewModel;
	}

	public ActionProvider getActionProvider() {
		return actionProvider;
	}

	public void addContextObject(final String name, final Object value) {
		if (this.contextObjects.containsKey(name)) {
			throw new IllegalStateException("Name '" + name + "' is already associated with another object '"
					+ this.contextObjects.get(name).getClass().getSimpleName() + "'");
		}
		this.contextObjects.put(name, value);
	}

	public Object getContextObject(final String name) {
		if (!this.contextObjects.containsKey(name)) {
			throw new IllegalStateException("Name '" + name + "' is not associated to any object, available are: "
					+ this.contextObjects + ".");
		}
		return this.contextObjects.get(name);
	}

	public void interpretScript(String script) {
		scriptInterpreter.interpretScript(this, script);
	}

	public <M> M interpretScript(String script, Class<M> resultType) {
		return scriptInterpreter.interpretScript(this, script, resultType);
	}

	public Map<String, Object> getContextObjects() {
		return Collections.unmodifiableMap(contextObjects);
	}

}