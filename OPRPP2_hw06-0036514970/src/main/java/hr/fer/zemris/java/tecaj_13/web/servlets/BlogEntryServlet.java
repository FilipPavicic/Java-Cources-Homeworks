package hr.fer.zemris.java.tecaj_13.web.servlets;
import hr.fer.zemris.java.tecaj_13.Utils;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
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

public class BlogEntryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String currentUserId =  (String) session.getAttribute(Kljucevi.CURRENT_USER_ID);
		req.setAttribute(Kljucevi.IS_USER_LOGGED, currentUserId != null);
		String nick = (String) req.getAttribute(Kljucevi.NICK);
		long idBlogEntry = Long.parseLong(req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1));
		BlogEntry blogEntry= DAOProvider.getDAO().getBlogEntry(idBlogEntry);
		req.setAttribute(Kljucevi.TITLE, blogEntry.getTitle());
		req.setAttribute(Kljucevi.TEXT, blogEntry.getText());
		req.setAttribute(Kljucevi.COMMENTS, blogEntry.getComments());
		req.setAttribute(Kljucevi.BLOG_ENTRY_ID, blogEntry.getId());
		req.getRequestDispatcher("/WEB-INF/pages/BlogDetails.jsp").forward(req, resp);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String comment = req.getParameter(Kljucevi.COMMENT);
		long idBlogEntry = Long.parseLong(req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1));
		Map<String,String> errors = new HashMap<>();
		Validators.validateValueWithValidators(comment,
				(s) -> errors.put(Kljucevi.COMMENT, s),
				Validators.EMPTY_VALIDATOR, Validators.maxValidatorforLength(4096)
			);
		if(!errors.isEmpty()) {
			req.setAttribute(Kljucevi.HAS_ERRORS, true);
			req.setAttribute(Kljucevi.ERRORS, errors);
			req.setAttribute(Kljucevi.COMMENT, comment);
			doGet(req, resp);
			return;
		}
		String nick = (String) session.getAttribute(Kljucevi.CURRENT_USER_NICK);
		String email = "Aninimni korinsnik";
		if(nick != null) {
			User user = DAOProvider.getDAO().getUserByNick(nick);
			email = user.getEmail();
		}
		
		DAOProvider.getDAO().addCommentInBlogEntry(idBlogEntry, comment, email);	
		resp.sendRedirect(req.getRequestURI());
	}

}