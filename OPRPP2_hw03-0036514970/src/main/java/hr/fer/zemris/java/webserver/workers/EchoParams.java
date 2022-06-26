package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class EchoParams implements IWebWorker {
	public static final String ECHO_PARAM = "echoh.smscr";

	@Override
	public void processRequest(RequestContext context) {
		Set<String> paramNames = context.getParameterNames();
		try {
			context.write("<html>");
			context.write("<head> <title>Tablica Parametara</title></head>");
			context.write("<body>");
			context.write("<h1>Tablica Parametara</h1>");
			context.write("<table><thead>");
			context.write(" <tr><th>Parametar</th><th>Vrijednost</th></tr>");
			context.write("</thead><tbody>");
			for (String name : paramNames) {
				context.write(" <tr><td>"+name+"</td><td>"+context.getParameter(name)+"</td></tr>");	
			}
			context.write(" </tbody></table></body></html>");
		}catch(IOException ex) {
			// Log exception to servers log...
			ex.printStackTrace();
		}
	}

}
