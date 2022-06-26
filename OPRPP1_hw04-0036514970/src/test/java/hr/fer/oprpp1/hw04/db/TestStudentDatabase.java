package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TestStudentDatabase {
	private static final IFilter FALSE_FILTER = c -> false;
	private static final IFilter TRUE_FILTER = c -> true;
	
	private List<String> dataBaseRows() throws IOException {
		return Files.readAllLines(
				 Paths.get("./database.txt"),
				 StandardCharsets.UTF_8
				);

	}
	
	@Test
	public void TestForJMBAG() {
		try {
			StudentDatabase sd = new StudentDatabase(dataBaseRows());
			StudentRecord sr_expected = new StudentRecord("0000000027", "Pero", "Perić", 4);
			assertEquals(sr_expected, sd.forJMBAG("0000000027"));
			assertEquals(null, sd.forJMBAG("9900000027"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void Testfillter() {
		try {
			StudentDatabase sd = new StudentDatabase(dataBaseRows());
			assertEquals(sd.getList(), sd.filter(TRUE_FILTER));
			assertEquals(true, sd.filter(FALSE_FILTER).isEmpty());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	

}
