package hr.fer.oprpp2.hw04.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.oprpp2.hw04.Kljucevi;
import hr.fer.oprpp2.hw04.Utils;

@WebServlet(urlPatterns = "/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Object LOCK = new Object();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PieDataset dataset = createDataset(req);
		JFreeChart chart = createChart(dataset, "rezultat glasnja");
		resp.setContentType("image/png");
		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 640, 480);

	}

	private  PieDataset createDataset(HttpServletRequest req) {
		DefaultPieDataset result = new DefaultPieDataset();
		String fileName_rez = req.getServletContext().getRealPath("/WEB-INF/glasanje/rezultati.txt");
		String fileName_bend = req.getServletContext().getRealPath("/WEB-INF/glasanje/glasanje-definicija.txt");
		List<Map<String, String>> bendovi_rez;
		List<Map<String, String>> bendovi;
		synchronized (LOCK) {
			bendovi_rez = Utils.OpenSimpleCSVFile(fileName_rez, "\t",false,"id","rezultat");
			bendovi = Utils.OpenSimpleCSVFile(fileName_bend, "\t",false,"id","bend","link");
		}
		Map<String,Map<String,String>> mapabendova = Utils.createMapFromCSVList(bendovi,"id");
		Collections.sort(bendovi_rez, ((o1,o2) -> Integer.valueOf(o2.get("rezultat")).compareTo(Integer.valueOf(o1.get("rezultat")))));
		for(Map<String,String> el : bendovi_rez) {
			result.setValue(mapabendova.get(el.get("id")).get("bend"), Integer.valueOf(el.get("rezultat")));
		}
		return result;

	}

	/**
	 * Creates a chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart(
				title,                  // chart title
				dataset,                // data
				true,                   // include legend
				true,
				false
				);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}
}
