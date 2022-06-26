package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestComplexParse {
	@Test
	public void testComplexParser() {
		assertEquals(new Complex(1, 1), Complex.parseComplex("1+i1"));
		assertEquals(new Complex(1, 1), Complex.parseComplex("1 + i1"));
		assertEquals(new Complex(1, 1), Complex.parseComplex("1 + i"));
		assertEquals(new Complex(0, 1), Complex.parseComplex("i"));
		assertEquals(new Complex(1, 0), Complex.parseComplex("1"));
		assertEquals(new Complex(0, 0), Complex.parseComplex("0"));
		assertEquals(new Complex(0, 0), Complex.parseComplex("0+i0"));
		assertEquals(new Complex(-1, -2), Complex.parseComplex("-1-i2"));
		assertEquals(new Complex(-1, -1), Complex.parseComplex("-1 -i"));
		assertEquals(new Complex(1, -1), Complex.parseComplex("+1 -i"));
		assertThrows(IllegalArgumentException.class ,() -> Complex.parseComplex("+1 -1"));
		assertThrows(IllegalArgumentException.class ,() -> Complex.parseComplex("+1i -1i"));
		assertThrows(IllegalArgumentException.class ,() -> Complex.parseComplex(""));
		assertThrows(IllegalArgumentException.class ,() -> Complex.parseComplex("1i"));
	}
}
