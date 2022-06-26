package hr.fer.zemris.math;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test; 
import java.util.stream.Collectors;

public class TstComplexRootedPolynomial {

	@Test
	public void testToComplexPolynomRealNumer() {
		Complex c1 = new Complex(3, 0);
		Complex c2 = new Complex(4, 0);
		Complex consta = new Complex(1, 0);
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(consta, new Complex[] {c1, c2});
		assertArrayEquals(new Complex[] {new Complex(12, 0), new Complex(7, 0), new Complex(1, 0)} , crp.toComplexPolynom().getFactors());
	}

	@Test
	public void testToComplexPolynomRealNumerConst() {
		Complex c1 = new Complex(3, 0);
		Complex c2 = new Complex(4, 0);
		Complex consta = new Complex(2, 0);
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(consta, new Complex[] {c1, c2});
		assertArrayEquals(new Complex[] {new Complex(24, 0), new Complex(14, 0), new Complex(2, 0)} , crp.toComplexPolynom().getFactors());
	}

	@Test
	public void testToComplexPolynom() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
		assertArrayEquals(new Complex[] {new Complex(-2, 0), new Complex(0, 0), new Complex(0, 0), new Complex(0, 0), new Complex(2, 0)} , crp.toComplexPolynom().getFactors());
	}
	@Test
	public void testIndexOfClosestRootFor() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
		assertEquals(0, crp.indexOfClosestRootFor(new Complex(0.9, 0), 0.2));
		assertEquals(1, crp.indexOfClosestRootFor(new Complex(-0.9, 0), 0.2));
		assertEquals(2, crp.indexOfClosestRootFor(new Complex(0, 0.9), 0.2));
		assertEquals(-1, crp.indexOfClosestRootFor(new Complex(0, 0.79), 0.2));

	}
}