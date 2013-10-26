package com.spaeth.appbase.adds.jsonview.vaadin;

import java.util.Map.Entry;

import org.mozilla.javascript.ScriptableObject;

import com.spaeth.appbase.core.view.assembling.Context;
import com.spaeth.appbase.core.view.assembling.ScriptInterpreter;

public class JavaScriptInterpreter implements ScriptInterpreter {

	@Override
	public void interpretScript(Context<?> context, String script) {
		org.mozilla.javascript.Context c = org.mozilla.javascript.Context.enter();
		ScriptableObject scope = c.initStandardObjects();

		// adding view model
		for (Entry<String, Object> e : context.getContextObjects().entrySet()) {
			Object wrappedObject = org.mozilla.javascript.Context.javaToJS(e.getValue(), scope);
			ScriptableObject.putProperty(scope, e.getKey(), wrappedObject);
		}
		
		c.evaluateString(scope, script, this.getClass().getSimpleName(), 1, null);
		org.mozilla.javascript.Context.exit();

	}

	@Override
	public <M> M interpretScript(Context<?> context, String script, Class<M> resultType) {
		throw new NullPointerException("Not yet implemented.");
	}

}
