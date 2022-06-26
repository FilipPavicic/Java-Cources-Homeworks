package hr.fer.oprpp2.hw04.servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

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

@WebServlet(urlPatterns = "/powers")
public class PowersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String str_a = req.getParameter("a");
		String str_b = req.getParameter("b");
		String str_n = req.getParameter("n");
		Integer a;
		Integer b;
		Integer n;
		try {
			a = (Integer) Utils.parseParametar("a", str_a, null, o ->  {
				Integer i =  Integer.parseInt((String)o);
				if( i < -100 || i > 100) throw new IllegalArgumentException("Vrijednost mora biti u intervalu [-100 i 100]");
				return i;
			});
			b = (Integer) Utils.parseParametar("b", str_b, null, o ->  {
				Integer i =  Integer.parseInt((String)o);
				if( i < -100 || i > 100) throw new IllegalArgumentException("Vrijednost mora biti u intervalu [-100 i 100]");
				return i;
			});
			n = (Integer) Utils.parseParametar("n", str_n, null, o ->  {
				Integer i =  Integer.parseInt((String)o);
				if( i < 1 || i > 5) throw new IllegalArgumentException("Vrijednost mora biti u intervalu [1 i 5]");
				return i;
			});
		}catch (IllegalArgumentException e) {
			Utils.sendErrorMessage(req, resp, e.getMessage());
			return;
		}
		HSSFWorkbook hwb = CreatePowerExcelTable(a,b,n);
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		hwb.write(resp.getOutputStream());

	}

	private HSSFWorkbook CreatePowerExcelTable(Integer a, Integer b, Integer n) {
		if(a > b) {
			a = a + b;
			b = a - b;
			b = a - b;
		}
		HSSFWorkbook hwb=new HSSFWorkbook();
		int k = 1;
		for(int i = 1; i <= n; i++) {
			HSSFSheet sheet =  hwb.createSheet("sheet" + i);
			HSSFRow rowhead=   sheet.createRow((short)0);
			rowhead.createCell((short) 0).setCellValue("Broj");
			rowhead.createCell((short) 1).setCellValue("Broj^" + i);
			for (int j = a; j <= b; j++, k++) {
				HSSFRow row = sheet.createRow((short)k);
				row.createCell((short) 0).setCellValue(j);
				row.createCell((short) 1).setCellValue(Math.pow(j, i));
				
			}
			k = 1;	
		}
		return hwb;
	}

}
