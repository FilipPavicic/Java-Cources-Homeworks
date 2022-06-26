package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Complex {
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	private final double re;
	private final double im;
	
	public Complex() {
		this(ZERO.re, ZERO.im);
	}
	
	public Complex(double re, double im) {
		super();
		this.re = re;
		this.im = im;
	}
	
	/**
	 * 
	 *  returns module of complex number
	 */
	public double module() {
		return Math.hypot(re, im);
	}
	
	/**
	 * Metoda množi dani komleksni broj s trenutnim.
	 * 
	 * @param c komleksni broj s kojim se množi.
	 * @return nova instanca <code>ComplexNumber</code> koja predstavlja izračunati imaginarni broj
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.re * c.re - this.im * c.im, this.re * c.im + this.im * c.re);
	}
	
	/**
	 * Metoda dijeli trenutni komleksni broj s danim.
	 * 
	 * @param c komleksni broj s kojim se dijeli.
	 * @return nova instanca <code>Complex</code> koja predstavlja izračunati imaginarni broj
	 */
	public Complex divide(Complex c) {
		Complex mulByConjugate=this.multiply(new Complex(c.re, -c.im));
		return new Complex(mulByConjugate.re / Math.pow(c.module(),2) ,
				mulByConjugate.im / Math.pow(c.module(),2) );
	}
	
	/**
	 * Metoda zbroja trenuti i dani komleksni broj.
	 * 
	 * @param c komleksni broj koji se nadodaje trenutnom.
	 * @return nova instanca <code>Complex</code> koja predstavlja izračunati imaginarni broj
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}
	/**
	 * Metoda oduzima dani komleksni broj od trenutnog.
	 * 
	 * @param c komleksni broj s kojim se oduzima.
	 * @return nova instanca <code>Complex</code> koja predstavlja izračunati imaginarni broj
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}
	
	/**
	 * Metoda izračunva -this.
	 * 
	 * @return nova instanca <code>Complex</code> koja predstavlja izračunati imaginarni broj
	 */
	public Complex negate() {
		return this.multiply(ONE_NEG);
	}
	
	/**
	 * Metoda potencira trenutni komleksni broj na danu potenciju.
	 * 
	 * @param n potencija broja 
	 * @return nova instanca <code>Complex</code> koja predstavlja izračunati imaginarni broj
	 */
	public Complex power(int n) {
		if(n < 0) throw new IllegalArgumentException("n mora biti prirodan broj ili 0.");

		double magnitude = Math.pow(this.module(),n);
		double angle = (this.getAngle() * n) % (2 * Math.PI);
		return fromMagnitudeAndAngle(magnitude, angle);
	}
	
	// returns n-th root of this, n is positive integer
	public List<Complex> root(int n) {
		if(n <= 0) throw new IllegalArgumentException("n mora biti prirodan broj.");

		double magnitude = Math.pow(this.module(), 1.0 / n);

		List<Complex> roots = new ArrayList<Complex>();

		for (int i = 0; i < n; i++) {
			double angle=((this.getAngle() + 2 * Math.PI * i) / n) % (2 * Math.PI);
			roots.add(fromMagnitudeAndAngle(magnitude, angle));
		}

		return roots;
	}
	@Override
	public String toString() {
		
		String imString = im >=0 ? "+i" + form(im) : "-i" + form(-im);
		return "(" + form(re) + imString + ")";
	}
	
	private String form(double numb) {
		return String.format(Locale.ROOT ,"%.1f", numb);
	}
	
	/**
	 * Metode parsira string u Complex  broj
	 * 
	 * @param s String koji se parsira
	 * @return
	 */
	public static Complex parseComplex(String s) {
		String[] parts = s.split("\\+|(?=-)");
		Double im = null;
		Double re = null;
		if(parts.length == 0) throw new IllegalArgumentException();
		
		for (int i = 0; i < parts.length; i++) {
			parts[i] = parts[i].trim();
			if(i == 0 && parts[i].length() == 0 ) continue;
			if(parts[i].startsWith("i") || parts[i].startsWith("-i")) {
				if(im != null) throw new IllegalArgumentException();
				
				parts[i] = parts[i].replaceFirst("i", "");
				if(parts[i].isEmpty()) {
					im = 1.0;
					continue;
				}
				if(parts[i].equals("-")) {
					im = -1.0;
					continue;
				}
				im = Double.parseDouble(parts[i]);
				continue;
			}
			if(re != null) throw new IllegalArgumentException();
			re = Double.parseDouble(parts[i]);
			
		}
		if(re == null && im == null) throw new IllegalArgumentException();
		return new Complex(re != null ? re : 0, im != null ? im : 0);
	}

	private static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		if(angle < 0) throw new IllegalArgumentException("Angle mora biti veći ili jedank od 0 ("+angle+").");
		if(angle >= 2*Math.PI) throw new IllegalArgumentException("Angle mora biti manji od 2*PI ("+angle+").");

		double complex = magnitude * Math.sin(angle);
		double real = magnitude * Math.cos(angle);

		return new Complex(real,complex);
	}
	
	private double getAngle() {
		if(im == 0 && re == 0) return 0;
		
		double angle = Math.atan(im/re);

		if(angle < 0) angle = angle + 2 * Math.PI;

		if(re < 0) angle = angle + Math.PI;

		return angle % (2 * Math.PI);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Complex))
			return false;
		Complex other = (Complex) obj;
		if (Math.abs(this.im - other.im) > 0.0001)
			return false;
		if (Math.abs(this.re - other.re) > 0.0001)
			return false;
		return true;
	}
}



