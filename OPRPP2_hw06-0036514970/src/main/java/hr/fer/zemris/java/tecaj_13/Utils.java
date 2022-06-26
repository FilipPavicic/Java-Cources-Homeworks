package hr.fer.zemris.java.tecaj_13;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utils {
	public static String SHA1(String text) {

		String sha1 = "";
		try
		{
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(text.getBytes("UTF-8"));
			sha1 = byteToHex(crypt.digest());
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return sha1;
	}

	private static String byteToHex(final byte[] hash)
	{
		Formatter formatter = new Formatter();
		for (byte b : hash)
		{
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	public static void sendErrorMessage(ServletRequest req, ServletResponse resp, String message) throws IOException, ServletException {
		req.setAttribute("PORUKA", message);
		req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
	}
}
