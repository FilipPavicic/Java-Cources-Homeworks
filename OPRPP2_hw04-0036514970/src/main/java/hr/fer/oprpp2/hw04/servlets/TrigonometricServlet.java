package hr.fer.oprpp2.hw04.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.oprpp2.hw04.Kljucevi;
import hr.fer.oprpp2.hw04.Utils;

@WebServlet(urlPatterns = "/trigonometric")
public class TrigonometricServlet extends HttpServlet {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String str_a = req.getParameter("a");
		String str_b = req.getParameter("b");
		Integer a;
		Integer b;
		try {
			a = (Integer) Utils.parseParametar("a", str_a, 0, o ->  Integer.parseInt((String)o));
			b = (Integer) Utils.parseParametar("b", str_b, 360, o ->  Integer.parseInt((String)o));
		}catch (IllegalArgumentException e) {
			Utils.sendErrorMessage(req, resp, e.getMessage());
			return;
		}
		if(a > b) {
			a = a + b;
			b = a - b;
			a = a - b;
		}
		if(b > a + 720) {
			b = a + 720;
		}
		
		req.setAttribute(Kljucevi.TRIGONOMETRIC_MAP,
				IntStream.range(a, b +1).mapToObj(br -> {
					Map<String,Double> internal_map = new HashMap<>();
					internal_map.put("kut", br * 1.0);
					internal_map.put("sin", Math.sin(Math.toRadians(br)));
					internal_map.put("cos", Math.cos(Math.toRadians(br)));
					return internal_map;
				}).collect(Collectors.toList())
		);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
		
	}

	
}
