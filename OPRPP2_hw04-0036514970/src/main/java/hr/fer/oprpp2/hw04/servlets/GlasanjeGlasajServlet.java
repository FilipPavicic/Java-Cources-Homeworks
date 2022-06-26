package hr.fer.oprpp2.hw04.servlets;

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

import hr.fer.oprpp2.hw04.Kljucevi;
import hr.fer.oprpp2.hw04.Utils;

@WebServlet(urlPatterns = "/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Object LOCK = new Object();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Boolean isUserVoted = (Boolean) session.getAttribute(Kljucevi.IS_USER_VOTED);
		if(isUserVoted != null && isUserVoted == true) {
			req.getRequestDispatcher("/WEB-INF/pages/alreadyVoted.jsp").forward(req, resp);
			return;
		}
		String str_id = req.getParameter("id");
		String id;
		try {
			id = (String) Utils.parseParametar("id", str_id, null, e -> {
				Integer.parseInt((String)e);
				return (String)e;
			});
		}catch (IllegalArgumentException e) {
			Utils.sendErrorMessage(req, resp, e.getMessage());
			return;
		}
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje/rezultati.txt");
		List<Map<String,String>> bendovi_rez;
		synchronized (LOCK) {
			bendovi_rez = Utils.OpenSimpleCSVFile(fileName, "\t",false,"id","rezultat");
		}
		boolean find_id = false;
		for(Map<String,String> bend : bendovi_rez) {
			if(bend.get("id").equals(id)) {
				find_id = true;
				bend.put("rezultat",String.valueOf(Integer.parseInt(bend.get("rezultat")) + 1));
				break;
			}
		}
		if(find_id == false) {
			Utils.sendErrorMessage(req, resp, "Nije pronađena stavka s id: " + id);
			return;
		}
		synchronized (LOCK) {
			Utils.WriteSimpleCSVFile(fileName, bendovi_rez, "\t", false,"id","rezultat");
		}
		session.setAttribute(Kljucevi.IS_USER_VOTED, true);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
		return;
	}
}
