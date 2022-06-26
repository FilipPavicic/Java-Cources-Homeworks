package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class BgColorWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String mess = "Boja nije uspješno promijenjena";
		String color = context.getParameter("bgcolor");
		if(color == null) {

		}else if(color.length() == 6 && checkHexString(color) == true) {
			context.setPersistentParameter("bgcolor",color);
			mess = "Boja uspješno promijenjena";
		}
		else if(color.length() == 9 && color.charAt(0) == '%' && checkHexString(color.substring(1)) == true) {
			context.setPersistentParameter("bgcolor",color.substring(3));
			mess = "Boja uspješno promijenjena";
		}
		context.setPersistentParameter("message",mess);
		context.setTemporaryParameter("redirectlink","./index2.html");
		context.getDispater().dispatchRequest("/private/pages/redirect.smscr");
		context.removeTemporaryParameter("redirectlink");
		return;
	}

	private boolean checkHexString(String color) {
		return color.chars().allMatch(e -> {
			char c = (char)e;
			if(c >= 'A' && c <= 'F' ) return true;
			if(c >= 'a' && c <= 'f' ) return true;
			if(c >= '0' && c <= '9' ) return true;
			return false;
		});
	}

}
