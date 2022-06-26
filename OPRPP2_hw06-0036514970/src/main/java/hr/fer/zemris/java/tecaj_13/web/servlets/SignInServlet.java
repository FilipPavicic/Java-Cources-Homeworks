package hr.fer.zemris.java.tecaj_13.web.servlets;
import hr.fer.zemris.java.tecaj_13.Utils;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/servleti/signin")
public class SignInServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/SignIn.jsp").forward(req, resp);
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String firstName = (String) req.getParameter(Kljucevi.FIRST_NAME);
		String lastName = (String) req.getParameter(Kljucevi.LAST_NAME);
		String email = (String) req.getParameter(Kljucevi.EMAIL);
		String nick = (String) req.getParameter(Kljucevi.NICK);
		String password = (String) req.getParameter(Kljucevi.PASSWORD);

		Map<String,String> errors = new HashMap<>();
		Validators.validateValueWithValidators(
				firstName,
				(s) -> errors.put(Kljucevi.FIRST_NAME, s), 
				Validators.EMPTY_VALIDATOR
				);
		Validators.validateValueWithValidators(
				lastName,
				(s) -> errors.put(Kljucevi.LAST_NAME, s),
				Validators.EMPTY_VALIDATOR
				);
		Validators.validateValueWithValidators(
				email,
				(s) -> errors.put(Kljucevi.EMAIL, s), 
				Validators.EMPTY_VALIDATOR,Validators.EMAIL_VALIDATOR
				);
		Validators.validateValueWithValidators(nick,
				(s) -> errors.put(Kljucevi.NICK, s),
				Validators.EMPTY_VALIDATOR, Validators.USER_WITH_NICK_EXITS
				);
		Validators.validateValueWithValidators(password,
				(s) -> errors.put(Kljucevi.PASSWORD, s),
				Validators.EMPTY_VALIDATOR,Validators.PASSWORD_VALIDATOR
				);
		if(!errors.isEmpty()) {
			req.setAttribute(Kljucevi.HAS_ERRORS, true);
			req.setAttribute(Kljucevi.ERRORS, errors);
			req.setAttribute(Kljucevi.FIRST_NAME, firstName);
			req.setAttribute(Kljucevi.LAST_NAME, lastName);
			req.setAttribute(Kljucevi.EMAIL, email);
			req.setAttribute(Kljucevi.NICK, nick);
			doGet(req, resp);
			return;
		}

		User user = DAOProvider.getDAO().addUser(firstName, lastName, email, nick, Utils.SHA1(password));
		session.setAttribute(Kljucevi.CURRENT_USER_ID, "" + user.getId());
		session.setAttribute(Kljucevi.CURRENT_USER_FN, user.getFirstName());
		session.setAttribute(Kljucevi.CURRENT_USER_LN, user.getLastName());
		session.setAttribute(Kljucevi.CURRENT_USER_NICK, user.getNick());
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}

}