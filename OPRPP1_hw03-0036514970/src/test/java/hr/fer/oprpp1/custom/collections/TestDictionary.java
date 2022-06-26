package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestDictionary {

	 @Test
	 public void TestPutElements() {
		 Dictionary<Integer, String> dic = new Dictionary<>();
		 dic.put(1, "Dora");
		 assertEquals(1, dic.size());
		 dic.put(1, "Filip");
		 assertEquals(1, dic.size());
		 dic.put(2, "Marko");
		 assertEquals(2, dic.size());
	 }
	 
	 @Test
	 public void TestGetElements() {
		 Dictionary<Integer, String> dic = new Dictionary<>();
		 dic.put(1, "Dora");
		 assertEquals("Dora", dic.get(1));
		 assertEquals(null, dic.get(2));
		 dic.put(2, "Filip");
		 assertEquals("Filip", dic.get(2));
	 }
	 
	 @Test
	 public void TestRemoveElements() {
		 Dictionary<Integer, String> dic = new Dictionary<>();
		 dic.put(1, "Dora");
		 assertEquals("Dora", dic.remove(1));
		 assertEquals(null, dic.remove(1));
	 }
}
