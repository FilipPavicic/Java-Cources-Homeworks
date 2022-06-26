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

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptions;

@WebServlet(urlPatterns = "/servleti/glasanje-xls")
public class GlasnjaXlsServlet extends HttpServlet {

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
		HSSFWorkbook hwb = CreatePowerExcelTable(req,id);
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica_rezultata_glasanja.xls\"");
		hwb.write(resp.getOutputStream());

	}

	private HSSFWorkbook CreatePowerExcelTable(HttpServletRequest req,long id) {
		Map<Long,PollOptions> mapa = DAOProvider.getDao().getPollOptionsByPollID(id);
		int k = 1;
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("rezultati");
		HSSFRow rowhead=   sheet.createRow((short)0);
		rowhead.createCell((short) 0).setCellValue(id== 0 ? "Bend" : "Programski jezik");
		rowhead.createCell((short) 1).setCellValue("Broj glasova");
		for(PollOptions el : mapa.values()) {
			HSSFRow row = sheet.createRow((short)k);
			row.createCell((short) 0).setCellValue(el.getOptionTitle());
			row.createCell((short) 1).setCellValue(el.getVotesCount());
			k++;
		}

		return hwb;
	}
}


