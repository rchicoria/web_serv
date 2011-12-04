package com.sample.action;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class MessageActionHandler2 implements ActionHandler {

	private static final long serialVersionUID = 1L;
	String message;
	
	public void execute(ExecutionContext context) throws Exception 
	{
		System.out.println("MessageActionHandler2: " + message);
	}

}