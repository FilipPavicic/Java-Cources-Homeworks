package hr.fer.zemris.java.fractals;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ComplexPolynomial {
	private final Complex[] factors;
	
	// constructor
	public ComplexPolynomial(Complex ...factors) {
		this.factors = factors;
	}
	
	
	
	public Complex[] getFactors() {
		return factors;
	}

	/**
	 *  returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	 * @return
	 */
	public short order() {
		return (short) (factors.length - 1); 
	}
	/**
	 * 
	 *  computes a new polynomial this*p
	 * @param p
	 * @return
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] factors = new Complex[this.order() + p.order()];
		Arrays.fill(factors,Complex.ZERO);
		for (int i = 0; i < this.factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				factors[i + j] = factors[i + j].add(this.factors[i].multiply(p.factors[j]));
			}
		}
		return new ComplexPolynomial(factors);
	}
	
	/**
	 *  computes first derivative of this polynomial; for example, for
	 *  (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * @return
	 */
	public ComplexPolynomial derive() {
		Complex[] derivatedfactors = new Complex[this.order()];
		Arrays.fill(derivatedfactors,Complex.ZERO);
		for (int i = 1; i < this.factors.length; i++) {
			derivatedfactors[i - 1] = this.factors[i].multiply(new Complex(i, 0));
		}
		return new ComplexPolynomial(derivatedfactors);
	}
	/**
	 *  computes polynomial value at given point z
	 * @param z
	 * @return
	 */
	public Complex apply(Complex z) {
		Complex comp = Complex.ZERO;
		for(int i = 0; i < this.factors.length; i++) {
			comp = comp.add(z.power(i).multiply(factors[i]));
		}
		return comp;
	}
	
	@Override
	public String toString() {
		return IntStream.range(0, factors.length)
				.boxed()
				.sorted(Collections.reverseOrder())
				.map(i -> {
					if(i == 0) return factors[i].toString();
					return factors[i].toString() + "z^" + i;
				})
				.collect(Collectors.joining("+"));
	}
	
}
