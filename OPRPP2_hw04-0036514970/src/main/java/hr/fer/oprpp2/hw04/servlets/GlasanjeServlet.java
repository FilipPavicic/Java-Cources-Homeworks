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

@WebServlet(urlPatterns = "/glasanje")
public class GlasanjeServlet extends HttpServlet {
	
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
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje/glasanje-definicija.txt");
		List<Map<String,String>> bendovi;
		synchronized (LOCK) {
			bendovi = Utils.OpenSimpleCSVFile(fileName, "\t",false,"id","bend","link");
		}
		Collections.sort(bendovi, (o1,o2) -> o1.get("id").compareTo(o2.get("id")));
		req.setAttribute(Kljucevi.LISTA_BENDOVA, bendovi);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		return;
	}
}
