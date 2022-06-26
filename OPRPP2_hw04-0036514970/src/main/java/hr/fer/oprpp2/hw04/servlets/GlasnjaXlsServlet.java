package hr.fer.oprpp2.hw04.servlets;

import java.io.FileOutputStream;
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

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.oprpp2.hw04.Kljucevi;
import hr.fer.oprpp2.hw04.Utils;

@WebServlet(urlPatterns = "/glasanje-xls")
public class GlasnjaXlsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Object LOCK = new Object();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HSSFWorkbook hwb = CreatePowerExcelTable(req);
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica_rezultata_glasanja.xls\"");
		hwb.write(resp.getOutputStream());

	}

	private HSSFWorkbook CreatePowerExcelTable(HttpServletRequest req) {
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
		int k = 1;
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("rezultati");
		HSSFRow rowhead=   sheet.createRow((short)0);
		rowhead.createCell((short) 0).setCellValue("Bend");
		rowhead.createCell((short) 1).setCellValue("Broj glasova");
		for(Map<String,String> el : bendovi_rez) {
			HSSFRow row = sheet.createRow((short)k);
			row.createCell((short) 0).setCellValue(mapabendova.get(el.get("id")).get("bend"));
			row.createCell((short) 1).setCellValue(Integer.valueOf(el.get("rezultat")));
			k++;
		}

		return hwb;
	}
}


