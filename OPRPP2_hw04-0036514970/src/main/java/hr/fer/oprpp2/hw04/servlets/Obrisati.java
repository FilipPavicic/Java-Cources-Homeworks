package hr.fer.oprpp2.hw04.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/srs")
public class Obrisati extends HttpServlet {
 

	/**
	 *
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String str_id = req.getParameter("cookie");
		System.out.println(str_id);
		resp.setStatus(200);
		PrintWriter writer=resp.getWriter();
		writer.append("Ovo je dobiveni cookie:" + str_id+ "\n");
		writer.append("S ovim podacima mogu lagano preuzeti kontrolu nad sjednicom\n");
		resp.flushBuffer();
	}
}
