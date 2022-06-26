package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashSimpleHashtableTest {

	@Test
	public void testFormulaForInitSize() {
		int size = 17;
		double  log2Size = (Math.log(size * 1.0) / Math.log(2.0));
		if (Math.abs(log2Size - (int) log2Size) > 0.001 ) size =  (int ) Math.pow(2,(int) log2Size +1); 
		assertEquals(32, size);
		size = 64;
		log2Size = (Math.log(size * 1.0) / Math.log(2.0));
		if (Math.abs(log2Size - (int) log2Size) > 0.001 ) size =  (int ) Math.pow(2,(int) log2Size +1); 
		assertEquals(64, size);
	}


	@Test
	public void  testConstrucot() {
		SimpleHashtable<String, String> dic = new SimpleHashtable<String, String>();
		assertEquals(0, dic.size());
		assertEquals(true, dic.isEmpty());
	}

	@Test
	public void  testPut() {
		SimpleHashtable<String, String> dic = new SimpleHashtable<String, String>(2);
		String put1 = dic.put("Filip", "Dora"); // Hash 0
		assertEquals(null, put1);
		assertEquals(1, dic.size());
		String put2 = dic.put("Filip", "Marko"); // Hash 0
		assertEquals( "Dora", put2);
		assertEquals(1, dic.size());
		String put3 = dic.put("Ivana", "Filip"); // Hash 1
		assertEquals(null, put3);
		assertEquals(2, dic.size());
		String put4 = dic.put("Ante", "Filip"); // Hash 0
		assertEquals(null, put4);
		assertEquals(3, dic.size());
		String put5 = dic.put("Ante", "Pero");
		assertEquals("Filip", put5);
		assertEquals(3, dic.size());
		assertThrows(NullPointerException.class, () -> dic.put(null, null));
	}

	@Test
	public void  testGet() {
		SimpleHashtable<String, String> dic = new SimpleHashtable<String, String>(2);
		assertEquals(null, dic.get("Filip"));
		String put1 = dic.put("Filip", "Dora");
		assertEquals("Dora", dic.get("Filip"));
		String put3 = dic.put("Ivana", "Filip"); // Hash 1
		assertEquals("Filip", dic.get("Ivana"));
		assertEquals(null,dic.get(null));
	}

	@Test
	public void testConstainsKey() {
		SimpleHashtable<String, String> dic = new SimpleHashtable<String, String>(2);
		String put1 = dic.put("Filip", "Dora");
		String put3 = dic.put("Ivana", "Filip");
		String put4 = dic.put("Ante", "Filip");
		String put2 = dic.put("Filip", "Pero");
		assertEquals(true, dic.containsKey("Filip"));
		assertEquals(false ,dic.containsKey("Dora"));
		assertEquals(true,dic.containsKey("Ivana"));// Hash 1
		assertThrows(NullPointerException.class,() -> dic.containsKey(null));
	}

	@Test
	public void testConstainsValue() {
		SimpleHashtable<String, String> dic = new SimpleHashtable<String, String>(2);
		String put1 = dic.put("Filip", "Dora");
		String put3 = dic.put("Ivana", "Filip");
		String put4 = dic.put("Ante", "Filip");
		assertEquals(true, dic.containsValue("Dora"));
		String put2 = dic.put("Filip", "Pero");
		assertEquals(false ,dic.containsValue("Dora"));
		assertEquals(true,dic.containsValue("Filip"));
		assertEquals(false,dic.containsValue("Tamara"));
		assertEquals(false, dic.containsValue(null));
	}

	@Test
	public void testRemove() {
		SimpleHashtable<String, String> dic = new SimpleHashtable<String, String>(2);
		String put1 = dic.put("Filip", "Dora");
		String put3 = dic.put("Ivana", "Filip");
		String put4 = dic.put("Ante", "Filip");
		assertEquals(3, dic.size());
		assertEquals("Filip", dic.remove("Ante"));
		assertEquals(2, dic.size());
		assertEquals(null, dic.remove("Ante"));
		assertEquals(2, dic.size());
		assertEquals(null, dic.remove("Tamara"));
		assertEquals(2, dic.size());
		assertEquals(null, dic.remove(null));
	}

	@Test
	public void testToArray() {
		SimpleHashtable<String, String> dic = new SimpleHashtable<String, String>(2);
		String put1 = dic.put("Filip", "Dora");
		String put3 = dic.put("Ivana", "Filip");
		String put4 = dic.put("Ante", "Filip");
		String put5 = dic.put("Ante", "Marko");
		TableEntry<String, String>[] array = dic.toArray();
		assertEquals(new TableEntry<>("Filip", "Dora", null), array[0]);
		assertEquals(new TableEntry<>("Ivana", "Filip", null), array[2]);
		assertEquals(new TableEntry<>("Ante", "Marko", null), array[1]);
	}
	
	@Test
	public void testToString() {
		SimpleHashtable<String, String> dic = new SimpleHashtable<String, String>(2);
		String put1 = dic.put("Filip", "Dora");
		String put3 = dic.put("Ivana", "Filip");
		String put4 = dic.put("Ante", "Filip");
		String put5 = dic.put("Ante", "Marko");
		assertEquals("[Filip=Dora, Ante=Marko, Ivana=Filip]", dic.toString());
	}
	
	@Test
	public void testOptimization() {
		SimpleHashtable<String, String> dic = new SimpleHashtable<String, String>(2);
		assertEquals(0/2.0, dic.getOptimalLevel());
		String put1 = dic.put("Filip", "Dora");
		assertEquals(1/2.0, dic.getOptimalLevel());
		String put3 = dic.put("Ivana", "Filip");
		assertEquals(2/4.0, dic.getOptimalLevel());
		String put4 = dic.put("Ante", "Filip");
		assertEquals(3/8.0, dic.getOptimalLevel());
		String put5 = dic.put("Ante", "Marko");
		assertEquals(3/8.0, dic.getOptimalLevel());
		assertEquals("[Filip=Dora, Ante=Marko, Ivana=Filip]", dic.toString());
		
	}
	
	@Test
	public void testClear() {
		SimpleHashtable<String, String> dic = new SimpleHashtable<String, String>(2);
		String put1 = dic.put("Filip", "Dora");
		String put3 = dic.put("Ivana", "Filip");
		String put4 = dic.put("Ante", "Filip");
		String put5 = dic.put("Ante", "Marko");
		dic.clear();
		assertEquals(false, dic.containsValue("Dora"));
		assertEquals(false, dic.containsKey("Filip"));
		
	}
}
