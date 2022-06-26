package hr.fer.oprpp2.hw04;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utils {
	private static final Object LOCK = new Object();
	static Random random = new Random();
	
	
	public static <R> Object parseParametar(String name, String value, Object defaultValue, Function<Object, R> parser) throws IOException, ServletException {
		if(value == null) {
			if(defaultValue != null) return defaultValue;
			throw new IllegalArgumentException("Obavezan parametar " + name + "nije zadan.");
		}
		if(parser == null) return value;
		try {
			R p_value = parser.apply(value);
			return (R) p_value;
		} catch (Exception e) {
			throw new IllegalArgumentException("Greška u parsiranju: " + e.getMessage());
		}
	}
	
	public static void sendErrorMessage(HttpServletRequest req, HttpServletResponse resp, String message) throws IOException, ServletException {
		req.setAttribute("PORUKA", message);
		req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
	}
	public static String randomHexColor() {
		String value;
		synchronized(LOCK) {
			value = random.ints(6, 0, 16).mapToObj(Integer::toHexString).collect(Collectors.joining());
		}
		return "#" + value;
	}
	public static String runningTime(Long time) {
		long dif = System.currentTimeMillis() - time;
		int days = (int) (dif / (1000*60*60*24));
		dif = dif - days * 1000*60*60*24;
		int hours = (int)( dif /(1000*60*60));
		dif = dif - hours * 1000*60*60;
		int minutes = (int) (dif / (1000*60));
		dif = dif - minutes * 1000*60;
		int seconds = (int) (dif /1000);
		return days + " dana, " + hours +" sati, " + minutes +" minuta, " + seconds + " sekundi.";
		
	}

	public static List<Map<String, String>> OpenSimpleCSVFile(String fileName,String delimiter, boolean header,String... zaglavlja ) {
		List<Map<String,String>> lista = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File(fileName),StandardCharsets.UTF_8);
			int i = 0;
			while (scanner.hasNextLine()) {
				if(i == 0 && header) {
					zaglavlja = scanner.nextLine().split(delimiter);
					i = 1;
					continue;
				}
				String[] vrijednosti = scanner.nextLine().split(delimiter);
				Map<String,String> mapa = new HashMap<String, String>();
				for (int j = 0; j < zaglavlja.length ; j++) {
					try {
						mapa.put(zaglavlja[j], vrijednosti[j]);
					}
					catch (ArrayIndexOutOfBoundsException e) {
						mapa.put(zaglavlja[j], null);
					}
				}
				lista.add(mapa);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return lista;
	}

	public static void WriteSimpleCSVFile(String fileName, List<Map<String, String>> data, String delimiter,
			boolean header_in,String... argOrder) {
		String data_write = "";
		if(header_in == true) {
			data_write = data.get(0).keySet().stream().collect(Collectors.joining(delimiter));
		}
		data_write += data.stream().
				map(l -> {
					List<String> s = new ArrayList<String>();
					for (String ord : argOrder) {
						s.add(l.get(ord));
					}
					return s.stream().collect(Collectors.joining(delimiter));
				}).
				collect(Collectors.joining("\n"));
		try {
			FileOutputStream writeStream = new FileOutputStream(new File(fileName), false);
			writeStream.write(data_write.getBytes());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static Map<String, Map<String, String>> createMapFromCSVList(List<Map<String, String>> lista,
			String key) {
		Map<String, Map<String, String>> return_map = new HashMap<String, Map<String,String>>();
		for(Map<String,String> el : lista) {
			if(!el.keySet().contains(key)) throw new IllegalArgumentException("u retku: " + el + "nije pronađen element pod oznakom" + key);
			Map<String,String> mapa = new HashMap<String, String>();
			String key_mapa = "";
			for (var map : el.entrySet()) {
				if(map.getKey().equals(key)) {
					key_mapa = map.getValue();
				}
				mapa.put(map.getKey(), map.getValue());
			}
			return_map.put(key_mapa, mapa);
		}
		return return_map;
	}
}
