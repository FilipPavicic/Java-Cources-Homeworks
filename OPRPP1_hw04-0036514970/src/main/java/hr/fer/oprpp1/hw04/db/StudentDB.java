package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Razred koji predstavlja program StudentDB
 * 
 * @author Filip Pavicic
 *
 */
public class StudentDB {
	
	/**
	 * Metoda pokreće program
	 * 
	 */
	public static void run() {
		StudentDatabase db = null;
		try {
			db = new StudentDatabase(dataBaseRows());
		} catch (IOException e) {
			System.err.println("Greška - Nije moguće učitati podatke za bazu");
			return;
		}
		Scanner sc = new Scanner(System.in);

		while(true) {
			System.out.print("> ");
			String sljedeci = sc.nextLine();
			if(sljedeci.equals("exit")) {
				System.out.println("Goodbye!");
				return;
			}
			if(sljedeci.matches("query .*")) {
				List<StudentRecord> records = new ArrayList<>();
				try {
					QueryParser parser = new QueryParser(sljedeci.substring(6));
					if(parser.isDirectQuery()) {
						System.out.println("Using index for record retrieval.");
						StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
						if(r != null) records.add(r);
					} else {
						for(StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
							records.add(r);
						}
					}
					printRecords(records);
				}catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
			else System.err.println("Nepoznata naredba");
		}
	}

	private static void printRecords(List<StudentRecord> records) {
		if(records.isEmpty()) {
			System.out.println("Records selected: 0");
			return;
		}

		int[] maxLength = {
				getMaxLength(records,c -> c.getJmbag()),
				getMaxLength(records,c -> c.getLastName()),
				getMaxLength(records,c -> c.getFirstName()),
				1 // ocjena zauzima 1 mjesto
		};

		String header = Arrays.stream(maxLength)
				.mapToObj(c -> {
					return IntStream.range(0, c + 2).mapToObj(i -> "=").collect(Collectors.joining());
				})
				.collect(Collectors.joining("+", "+", "+"));

		System.out.println(header);

		for (StudentRecord sr : records) {
			String[] atr = {
					sr.getJmbag(),
					sr.getLastName(),
					sr.getFirstName(),
					String.valueOf(sr.getFinalGrade())
			};
			String rowString = IntStream.range(0, 4).mapToObj(i -> {
						int length = maxLength[i];
						String value = atr[i];
						String part = " " + value + IntStream.range(value.length(), length + 1).mapToObj(o -> " ").collect(Collectors.joining());
						return part;
					})
					.collect(Collectors.joining("|", "|", "|"));

			System.out.println(rowString);
		}

		System.out.println(header);
		System.out.println("Records selected: " + records.size());
	}


	private static int getMaxLength(List<StudentRecord> records, Function<StudentRecord, String> function) {
		return records.stream()
				.map(function)
				.mapToInt(s -> s.length())
				.max()
				.getAsInt();

	}

	private static List<String> dataBaseRows() throws IOException {
		return Files.readAllLines(
				Paths.get("./database.txt"),
				StandardCharsets.UTF_8
				);

	}
}
