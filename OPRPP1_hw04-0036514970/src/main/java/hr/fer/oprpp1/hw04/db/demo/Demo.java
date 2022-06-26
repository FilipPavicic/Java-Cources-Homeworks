package hr.fer.oprpp1.hw04.db.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.oprpp1.hw04.db.QueryFilter;
import hr.fer.oprpp1.hw04.db.QueryParser;
import hr.fer.oprpp1.hw04.db.StudentDatabase;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class Demo {
	public static void main(String[] args) {
		StudentDatabase db = null;
		try {
			db = new StudentDatabase(dataBaseRows());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryParser parser = new QueryParser("firstName LIKE \"*a*\" and lastName=\"Zadro\"");
		if(parser.isDirectQuery()) {
			StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			System.out.println(r);
		} else {
			for(StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
				System.out.println(r);
			}
		}
	}
	private static List<String> dataBaseRows() throws IOException {
		return Files.readAllLines(
				Paths.get("./database.txt"),
				StandardCharsets.UTF_8
				);

	}			

}
