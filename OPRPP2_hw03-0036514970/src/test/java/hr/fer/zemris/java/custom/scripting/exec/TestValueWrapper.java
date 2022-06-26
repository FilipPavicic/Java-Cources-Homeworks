package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestValueWrapper {
	
	@Test
	public void testBothNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		assertEquals((Integer)v1.value, 0);
	}
	@Test
	public void testStringInteger() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());
		assertEquals((Double)v3.value, 13);
	}
	@Test
	public void testDoubleInteger() {
		ValueWrapper v3 = new ValueWrapper(12.0);
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());
		assertEquals((Double)v3.value, 13);
	}
	@Test
	public void testDoubleDouble() {
		ValueWrapper v3 = new ValueWrapper(12.0);
		ValueWrapper v4 = new ValueWrapper(1.0);
		v3.add(v4.getValue());
		assertEquals((Double)v3.value, 13);
	}
	@Test
	public void testIntegrInteger() {
		ValueWrapper v3 = new ValueWrapper(12);
		ValueWrapper v4 = new ValueWrapper(1);
		v3.add(v4.getValue());
		assertEquals((Integer)v3.value, 13);
	}
	@Test
	public void testNullInteger() {
		ValueWrapper v3 = new ValueWrapper(null);
		ValueWrapper v4 = new ValueWrapper(1);
		v3.add(v4.getValue());
		assertEquals((Integer)v3.value, 1);
	}
	@Test
	public void testNullDouble() {
		ValueWrapper v3 = new ValueWrapper(null);
		ValueWrapper v4 = new ValueWrapper(1.0);
		v3.add(v4.getValue());
		assertEquals((Integer)v3.value, 1);
	}
	@Test
	public void testStringString() {
		ValueWrapper v3 = new ValueWrapper("1");
		ValueWrapper v4 = new ValueWrapper("1.2");
		v3.add(v4.getValue());
		assertEquals((Double)v3.value, 2.2);
	}
	@Test
	public void testStringError() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class,() -> v7.add(v8.getValue())); 
		
	}
	@Test
	public void testDoubleInfinity() {
		ValueWrapper v7 = new ValueWrapper(5.0);
		ValueWrapper v8 = new ValueWrapper(0.0);
		v7.divide(v8.getValue());
		assertEquals((Double)v7.value, Double.POSITIVE_INFINITY);
		
	}
	@Test
	public void testIntegerInfinity() {
		ValueWrapper v7 = new ValueWrapper(50);
		ValueWrapper v8 = new ValueWrapper(0);
		v7.divide(v8.getValue());
		assertEquals((Integer)v7.value, Integer.MAX_VALUE);
		
	}
}
