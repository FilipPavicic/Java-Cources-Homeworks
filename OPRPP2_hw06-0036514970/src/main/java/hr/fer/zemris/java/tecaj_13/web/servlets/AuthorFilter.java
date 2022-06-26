package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.Utils;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.User;

@WebFilter("/servleti/author/*")
public class AuthorFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) req;
		Pattern authorPattern = Pattern.compile("^("+hreq.getContextPath() + "/servleti/author/)([^/]*)(.*)");
		Matcher m = authorPattern.matcher(hreq.getRequestURI());
		
		String nick = null;
	    if (m.find()) {
	        nick = m.group(2);
	    }
		User user = DAOProvider.getDAO().getUserByNick(nick);
		if(user == null) {
			Utils.sendErrorMessage(req, resp, "Korisnik "+nick+" nije pronađen");
			return;
		}
		req.setAttribute(Kljucevi.USER, user);
		req.setAttribute(Kljucevi.NICK, nick);
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
	}
	
}