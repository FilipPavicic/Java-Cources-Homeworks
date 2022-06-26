package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {
	public static final String FIRST_IMAGE = "images/audi.png";
	public static final String SECON_IMAGE = "images/jugo.png";

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String strVarA = context.getParameter("a");
		String strVarB = context.getParameter("b");
		Integer varA = strVarA == null ? 1 : Integer.valueOf(strVarA);
		Integer varB = strVarB == null ? 2 : Integer.valueOf(strVarB);
		context.setTemporaryParameter("zbroj", varA + varB + "");
		context.setTemporaryParameter("varA", varA+ "");
		context.setTemporaryParameter("varB",  varB + "");
		context.setTemporaryParameter("imgName", (varA + varB) % 2 == 0 ? FIRST_IMAGE: SECON_IMAGE);
		context.getDispater().dispatchRequest("/private/pages/calc.smscr");
		

	}

}
