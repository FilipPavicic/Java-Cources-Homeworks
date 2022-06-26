package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ComplexRootedPolynomial {
	
	private final Complex constnt;
	private final Complex[] roots;
	
	// constructor
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constnt = constant;
		this.roots = roots;
	}
	
	/**
	 * 
	 * computes polynomial value at given point z
	 * @param z
	 * @return
	 */
	public Complex apply(Complex z) {
		Complex[] subArray = new Complex[roots.length];
		for (int i = 0; i < roots.length; i++) {
			subArray[i] = z.sub(roots[i]);
		}
		Complex results = constnt;
		for (Complex complex : subArray) {
			results = results.multiply(complex);
		}
		return results;
	}
	
	/**
	 *  converts this representation to ComplexPolynomial type
	 * @return
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] factors = new Complex[roots.length+1];
		Arrays.fill(factors,Complex.ZERO);
		factors[0] = constnt;
		for (int i = 0; i < roots.length; i++) {
			for (int j = i + 1; j > 0; j--) {
				factors[j] = factors[j].multiply(roots[i]).add(factors[j - 1]); 
			}
			factors[0] = factors[0].multiply(roots[i]);
		}
		return new ComplexPolynomial(factors);
		
	}
	
	@Override
	public String toString() {
		return Arrays.stream(roots)
			.map(c -> "(z-" + c + ")")
			.collect(Collectors.joining("*", constnt + "*", ""));
	}
	
	
	// finds index of closest root for given complex number z that is within
	// treshold; if there is no such root, returns -1
	// first root has index 0, second index 1, etc
	public int indexOfClosestRootFor(Complex z, double treshold) {
		return IntStream.range(0, roots.length)
			.boxed()
			.filter(i -> roots[i].sub(z).module() <= treshold)
			.min(Comparator.comparingDouble(i -> roots[i].sub(z).module()))
			.orElse(-1);
	}

}
