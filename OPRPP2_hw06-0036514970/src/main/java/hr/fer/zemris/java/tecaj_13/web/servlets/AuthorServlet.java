package hr.fer.zemris.java.tecaj_13.web.servlets;
import hr.fer.zemris.java.tecaj_13.Utils;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {
	private static final Map<String, HttpServlet> NEXT_SERVLETS = new HashMap<>();
	static {
		NEXT_SERVLETS.put("new", new NewEntryServlet());
		NEXT_SERVLETS.put("edit",new EditEntryServlet());
		NEXT_SERVLETS.put("null",new BlogEntryServlet());
		
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String currentUserId =  (String) session.getAttribute(Kljucevi.CURRENT_USER_ID);
		req.setAttribute(Kljucevi.IS_USER_LOGGED, currentUserId != null);
		
		Pattern authorPattern = Pattern.compile("^("+req.getContextPath() + "/servleti/author/[^/]*[/])([^/]*)(.*)");
		Matcher m = authorPattern.matcher(req.getRequestURI());
		if(m.matches()) {
			String suffixOneLevel = m.group(2);
			
			if(suffixOneLevel != "") {
				HttpServlet servlet = NEXT_SERVLETS.get(suffixOneLevel);
				if(servlet == null) {
					servlet = NEXT_SERVLETS.get("null");
				}
				servlet.service(req, resp);
				return;
			}
		}
		req.getRequestDispatcher("/WEB-INF/pages/Author.jsp").forward(req, resp);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Pattern authorPattern = Pattern.compile("^("+req.getContextPath() + "/servleti/author/[^/]*[/])([^/]*)(.*)");
		Matcher m = authorPattern.matcher(req.getRequestURI());
		if(m.matches()) {
			String suffixOneLevel = m.group(2);
			
			if(suffixOneLevel != "") {
				HttpServlet servlet = NEXT_SERVLETS.get(suffixOneLevel);
				if(servlet == null) {
					servlet = NEXT_SERVLETS.get("null");
				}
				servlet.service(req, resp);
				return;
			}
		}
	}

}