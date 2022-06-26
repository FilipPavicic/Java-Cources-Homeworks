package hr.fer.zemris.java.p12;

import hr.fer.zemris.java.p12.dao.DAOProvider;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servleti/index.html")
public class PollsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		var polls = DAOProvider.getDao().getAllPolls();
		req.setAttribute(Kljucevi.MAPA_POLLS, polls);
		
		req.getRequestDispatcher("/WEB-INF/pages/polls.jsp").forward(req, resp);
	}
	
}