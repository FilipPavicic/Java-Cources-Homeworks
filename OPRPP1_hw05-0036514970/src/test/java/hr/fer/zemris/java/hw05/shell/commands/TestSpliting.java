package hr.fer.zemris.java.hw05.shell.commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test; 

public class TestSpliting {
	
	@Test
	public void testQuotationSpliting() {
		assertArrayEquals(new String[] {"Pero","Marko"}, SplitingUtil.quotationSpliting("\"Pero\" Marko"));
		assertArrayEquals(new String[] {"Pero","Marko"}, SplitingUtil.quotationSpliting("\"Pero\" \"Marko\""));
		assertArrayEquals(new String[] {"Pero","Ma","rko"}, SplitingUtil.quotationSpliting("\"Pero\" Ma rko"));
		assertArrayEquals(new String[] {"Pe ro","Ma","rko"}, SplitingUtil.quotationSpliting("\"Pe ro\" Ma rko"));
		assertArrayEquals(new String[] {"Pero","Marko"}, SplitingUtil.quotationSpliting("Pero \"Marko\""));
		assertThrows(IllegalArgumentException.class, () -> SplitingUtil.quotationSpliting("\"Pero\"\"Marko\""));
	}
}
