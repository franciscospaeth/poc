package com.spaeth.appbase.core.view.assembling;

public interface ScriptInterpreter {

	public static final ScriptInterpreter DUMMY = new ScriptInterpreter() {
		@Override
		public <M> M interpretScript(Context<?> context, String script, Class<M> resultType) {
			return null;
		}
		
		@Override
		public void interpretScript(Context<?> context, String script) {
		}
	};
	
	void interpretScript(Context<?> context, String script);
	
	<M> M interpretScript(Context<?> context, String script, Class<M> resultType);
	
}
