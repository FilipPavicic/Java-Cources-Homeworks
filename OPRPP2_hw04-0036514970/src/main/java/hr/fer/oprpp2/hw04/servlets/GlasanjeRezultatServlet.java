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

@WebServlet(urlPatterns = "/glasanje-rezultati")
public class GlasanjeRezultatServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Object LOCK = new Object();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName_rez = req.getServletContext().getRealPath("/WEB-INF/glasanje/rezultati.txt");
		String fileName_bend = req.getServletContext().getRealPath("/WEB-INF/glasanje/glasanje-definicija.txt");
		List<Map<String, String>> bendovi_rez;
		List<Map<String, String>> bendovi;
		synchronized (LOCK) {
			bendovi_rez = Utils.OpenSimpleCSVFile(fileName_rez, "\t",false,"id","rezultat");
			bendovi = Utils.OpenSimpleCSVFile(fileName_bend, "\t",false,"id","bend","link");
		}
		Map<String,Map<String,String>> mapabendova = Utils.createMapFromCSVList(bendovi,"id");
		req.setAttribute(Kljucevi.MAPA_BENDOVA, mapabendova);
		req.setAttribute(Kljucevi.LISTA_BENDOVA_REZ, bendovi_rez);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
		return;
	}
}
