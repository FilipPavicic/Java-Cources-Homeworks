package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptions;

@WebServlet(urlPatterns = "/servleti/glasanje-rezultati")
public class GlasanjeRezultatServlet extends HttpServlet {
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String str_id = req.getParameter("id");
		long id;
		try {
			id = (long) Utils.parseParametar("id", str_id, null, e -> Long.parseLong((String)e));
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
//			Map<Long,PollOptions> sortmap = pollOptions.entrySet().stream().
//					sorted((o1,o2) ->o2.getValue().compareTo(o1.getValue())).
//					collect(Collectors.toMap(Entry::getKey, Entry::getValue));
//			req.setAttribute(Kljucevi.MAPA_POLLOPTIONS, sortmap);
			List<PollOptions> sortList = pollOptions.entrySet().stream().
					map(e -> e.getValue()).
					sorted((o1,o2) ->o2.compareTo(o1)).
					collect(Collectors.toList());
			req.setAttribute(Kljucevi.LIST_POLLOPTIONS, sortList);
		}catch (DAOException e) {
			Utils.sendErrorMessage(req, resp, e.getMessage());
			return;
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
		return;
	}
}
