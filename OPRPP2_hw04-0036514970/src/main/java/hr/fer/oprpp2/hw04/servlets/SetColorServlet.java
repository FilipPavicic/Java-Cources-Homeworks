package hr.fer.oprpp2.hw04.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.oprpp2.hw04.Kljucevi;
import hr.fer.oprpp2.hw04.Utils;

@WebServlet(urlPatterns = "/setcolor")
public class SetColorServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String color;
		try{
			color= (String) Utils.parseParametar("color", req.getParameter(Kljucevi.KEY_USER_COLOR), null, (o)->{
				if(!Arrays.asList(Kljucevi.BG_COLORS).contains((String)o)) 
					throw new IllegalArgumentException(((String)o) +" se ne nalazi među ponuđenim bojama");
				return (String) o;
			});
		}catch (IllegalArgumentException e) {
			Utils.sendErrorMessage(req, resp, e.getMessage());
			return;
		}
		session.setAttribute(Kljucevi.KEY_USER_COLOR, color);
		resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath()+"/"));
	}
}
