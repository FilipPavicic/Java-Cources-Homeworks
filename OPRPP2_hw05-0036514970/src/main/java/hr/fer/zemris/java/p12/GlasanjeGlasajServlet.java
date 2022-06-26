package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.dao.DAOProvider;


@WebServlet(urlPatterns = "/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Object LOCK = new Object();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String str_pollID = req.getParameter("pollID");
		long pollID;
		try {
			pollID = (long) Utils.parseParametar("pollID", str_pollID, null, e -> Long.parseLong((String)e));
		}catch (IllegalArgumentException e) {
			Utils.sendErrorMessage(req, resp, e.getMessage());
			return;
		}
		String str_id = req.getParameter("id");
		long id;
		try {
			id = (long) Utils.parseParametar("id", str_id, null, e -> Long.parseLong((String)e));
		}catch (IllegalArgumentException e) {
			Utils.sendErrorMessage(req, resp, e.getMessage());
			return;
		}
		synchronized (LOCK) {
			try {
				DAOProvider.getDao().incrementVotesPollOptions(pollID);
			} catch (DAOException e) {
				Utils.sendErrorMessage(req, resp, e.getMessage());
				return;
			}
			
		}
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?id="+id);
		return;
	}
}
