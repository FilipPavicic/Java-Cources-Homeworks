package hr.fer.zemris.java.p12;

import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.dao.DAOProvider;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/servleti/glasanje")
public class PollOptionsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String str_id = req.getParameter("pollID");
		long id;
		try {
			id = (long) Utils.parseParametar("pollID", str_id, null, e -> {
				long br = Long.parseLong((String)e);
				return br;
			});
		}catch (IllegalArgumentException e) {
			Utils.sendErrorMessage(req, resp, e.getMessage());
			return;
		}
		
		try {
			var poll = DAOProvider.getDao().getPoll(id);
			req.setAttribute(Kljucevi.POLL, poll);
		}catch (DAOException e) {
			Utils.sendErrorMessage(req, resp, e.getMessage());
			return;
		}
		
		try {
			var pollOptions = DAOProvider.getDao().getPollOptionsByPollID(id);
			req.setAttribute(Kljucevi.MAPA_POLLOPTIONS, pollOptions);
		}catch (DAOException e) {
			Utils.sendErrorMessage(req, resp, e.getMessage());
			return;
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/polloptions.jsp").forward(req, resp);
	}
	
}