package com.spaeth.appbase.adds.xmlview.model;

import java.util.Map.Entry;

import org.mozilla.javascript.ScriptableObject;

import com.spaeth.appbase.adds.xmlview.XmlViewContext;
import com.spaeth.appbase.core.model.action.AbstractAction;
import com.spaeth.appbase.event.ActionUpdateEvent;
import com.spaeth.appbase.model.ActionParameters;

public class JavaScriptAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private String script;
	private XmlViewContext xmlViewContext;

	public JavaScriptAction(String script, XmlViewContext xmlViewContext) {
		this.script = script;
		this.xmlViewContext = xmlViewContext;
	}

	@Override
	public void internalExecute(ActionParameters parameters) {
		org.mozilla.javascript.Context c = org.mozilla.javascript.Context.enter();
		ScriptableObject scope = c.initStandardObjects();

		// adding view model
		for (Entry<String, Object> e : xmlViewContext.getRegisterMap().entrySet()) {
			Object wrappedObject = org.mozilla.javascript.Context.javaToJS(e.getValue(), scope);
			ScriptableObject.putProperty(scope, e.getKey(), wrappedObject);
		}
		
		for (String s : parameters.getParametersName()) {
			ScriptableObject.putProperty(scope, s, parameters.getValue(s, Object.class));
		}

		c.evaluateString(scope, script, this.getClass().getSimpleName(), 1, null);

		org.mozilla.javascript.Context.exit();
	}

	@Override
	protected ActionUpdateEvent createActionEvent() {
		return new ActionUpdateEvent(this, this);
	}

}
