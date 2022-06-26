package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptions;

@WebServlet(urlPatterns = "/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Object LOCK = new Object();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String str_id = req.getParameter("id");
		long id;
		try {
			id = (long) Utils.parseParametar("id", str_id, null, e -> {
				long br = Long.parseLong((String)e);
				return br;
			});
		}catch (IllegalArgumentException e) {
			Utils.sendErrorMessage(req, resp, e.getMessage());
			return;
		}
		PieDataset dataset = createDataset(req,id);
		JFreeChart chart = createChart(dataset, "rezultat glasnja");
		resp.setContentType("image/png");
		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 640, 480);

	}

	private  PieDataset createDataset(HttpServletRequest req, long id) {
		DefaultPieDataset result = new DefaultPieDataset();
		Map<Long,PollOptions> mapa = DAOProvider.getDao().getPollOptionsByPollID(id);
		mapa.entrySet().stream().forEach((e)-> {
			PollOptions po = e.getValue();
			result.setValue(po.getOptionTitle(), po.getVotesCount());
			
		});		
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
