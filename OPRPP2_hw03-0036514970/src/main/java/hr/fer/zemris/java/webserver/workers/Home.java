package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class Home implements IWebWorker{
	public static final String DEFAULT_COLOR = "7F7F7F"; 

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getPersistentParameter("bgcolor");
		context.setTemporaryParameter("background", color == null ? DEFAULT_COLOR: color);
		context.getDispater().dispatchRequest("/private/pages/home.smscr");
		context.removePersistentParameter("message");

		
		
	}

}
