package hr.fer.zemris.java.tecaj_13.web.servlets;
import hr.fer.zemris.java.tecaj_13.Utils;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String currentUserId =  (String) session.getAttribute(Kljucevi.CURRENT_USER_ID);
		System.out.println(currentUserId);
		req.setAttribute(Kljucevi.IS_USER_LOGGED, currentUserId != null);
		req.setAttribute(Kljucevi.USERS,DAOProvider.getDAO().getAllUsers());
		req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String nick = req.getParameter(Kljucevi.NICK);
		String password = req.getParameter(Kljucevi.PASSWORD);
		Map<String,String> errors = new HashMap<>();
		Validators.validateValueWithValidators(nick,
				(s) -> errors.put(Kljucevi.NICK, s),
				Validators.EMPTY_VALIDATOR
			);
		Validators.validateValueWithValidators(password,
				(s) -> errors.put(Kljucevi.PASSWORD, s),
				Validators.EMPTY_VALIDATOR
			);
		if(!errors.isEmpty()) {
			req.setAttribute(Kljucevi.HAS_ERRORS, true);
			req.setAttribute(Kljucevi.ERRORS, errors);
			req.setAttribute(Kljucevi.NICK, nick);
			doGet(req, resp);
			return;
		}
		
		User user = DAOProvider.getDAO().getUserByNick(nick);
		
		if(user == null) {
			req.setAttribute(Kljucevi.HAS_ERRORS, true);
			req.setAttribute(Kljucevi.ERRORS, errors);
			req.setAttribute(Kljucevi.LOGIN_INCORRECT, true);
			doGet(req, resp);
			return;
		}
		if(!user.getPasswordHash().equals(Utils.SHA1(password))) {
			req.setAttribute(Kljucevi.HAS_ERRORS, true);
			req.setAttribute(Kljucevi.ERRORS, errors);
			req.setAttribute(Kljucevi.LOGIN_INCORRECT, true);
			doGet(req, resp);
			return;
		}
		
		session.setAttribute(Kljucevi.CURRENT_USER_ID, "" + user.getId());
		session.setAttribute(Kljucevi.CURRENT_USER_FN, user.getFirstName());
		session.setAttribute(Kljucevi.CURRENT_USER_LN, user.getLastName());
		session.setAttribute(Kljucevi.CURRENT_USER_NICK, user.getNick());
		doGet(req, resp);
	}

}