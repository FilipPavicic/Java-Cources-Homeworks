package hr.fer.zemris.java.tecaj_13.web.servlets;
import hr.fer.zemris.java.tecaj_13.Utils;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.User;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditEntryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String currentUserId =  (String) session.getAttribute(Kljucevi.CURRENT_USER_ID);
		req.setAttribute(Kljucevi.IS_USER_LOGGED, currentUserId != null);
		String nick = (String) req.getAttribute(Kljucevi.NICK);
		if(!nick.equals(session.getAttribute(Kljucevi.CURRENT_USER_NICK))) {
			Utils.sendErrorMessage(req, resp, "Nemate ovlasti za pristup ovoj stranici");
		}
		long idBlogEntry = Long.parseLong(req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1));
		BlogEntry blogEntry= DAOProvider.getDAO().getBlogEntry(idBlogEntry);
		req.setAttribute(Kljucevi.TITLE, blogEntry.getTitle());
		req.setAttribute(Kljucevi.TEXT, blogEntry.getText());
		req.setAttribute(Kljucevi.BLOG_ENTRY_ID, blogEntry.getId());
		req.getRequestDispatcher("/WEB-INF/pages/NewEntry.jsp").forward(req, resp);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String title = req.getParameter(Kljucevi.TITLE);
		String text = req.getParameter(Kljucevi.TEXT);
		User user = (User) req.getAttribute(Kljucevi.USER);
		
		if(!user.getNick().equals(session.getAttribute(Kljucevi.CURRENT_USER_NICK))) {
			Utils.sendErrorMessage(req, resp, "Nemate ovlasti za pristup ovoj stranici");
		}
		long idBlogEntry = Long.parseLong(req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1));
		Map<String,String> errors = new HashMap<>();
		Validators.validateValueWithValidators(title,
				(s) -> errors.put(Kljucevi.NICK, s),
				Validators.EMPTY_VALIDATOR, Validators.maxValidatorforLength(200)
			);
		Validators.validateValueWithValidators(text,
				(s) -> errors.put(Kljucevi.PASSWORD, s),
				Validators.EMPTY_VALIDATOR, Validators.maxValidatorforLength(4096)
			);
		if(!errors.isEmpty()) {
			req.setAttribute(Kljucevi.HAS_ERRORS, true);
			req.setAttribute(Kljucevi.ERRORS, errors);
			req.setAttribute(Kljucevi.TITLE, title);
			req.setAttribute(Kljucevi.TEXT, text);
			doGet(req, resp);
			return;
		}
		
		DAOProvider.getDAO().editBlogEntry(idBlogEntry, new Date(), title, text);
		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + user.getNick());
	}

}