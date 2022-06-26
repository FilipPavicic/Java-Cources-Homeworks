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

@WebServlet("/servleti/addSrce")
public class AddSrceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String currentUserId =  (String) session.getAttribute(Kljucevi.CURRENT_USER_ID);
		req.setAttribute(Kljucevi.IS_USER_LOGGED, currentUserId != null);
		String nick = (String) req.getParameter(Kljucevi.NICK);
		long blogId = 0;
		try {
			blogId = Long.parseLong((String) req.getParameter(Kljucevi.BLOG_ENTRY_ID));
		}catch (NumberFormatException e) {
			Utils.sendErrorMessage(req, resp, "Nije moguće protumaciti zahtjev");
			return;
		}
		if(!nick.equals(session.getAttribute(Kljucevi.CURRENT_USER_NICK))) {
			Utils.sendErrorMessage(req, resp, "Nemate ovlasti za pristup ovoj stranici");
			return;
		}
		DAOProvider.getDAO().addSrce(DAOProvider.getDAO().getUserByNick(nick), blogId);
		
		resp.sendRedirect(req.getContextPath()+"/servleti/author/" + nick);
		
	}

	
}