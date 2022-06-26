package hr.fer.oprpp1.hw01;

/**
 * Razred koji predstavlja imlemntaciju kompleksnih brojeva.
 * 
 * @author Filip Pavicic
 *
 */
public class ComplexNumber {

	private final double real;
	private final double complex;

	/**
	 * Konstrukor koji kreira kompleksni broj.
	 * 
	 * @param real realni dio kompleksnog broja.
	 * @param complex imaginarni dio kompleksnog broja.
	 */
	public ComplexNumber(double real, double complex) {
		this.real = real;
		this.complex = complex;
	}

	/**
	 * Metoda stvara kompleksni broj kojem je dani realni dio, a imagnarni dio je 0.
	 * 
	 * @param real realni dio kompleksnog broja.
	 * @return nova instanca <code>ComplexNumber</code> koja predstavlja kreirani imaginarni broj.
	 */
	public static ComplexNumber fromReal (double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Metoda stvara kompleksni broj kojem je dani imaginarni dio, a realni dio je 0.
	 * 
	 * @param imaginary imaginarni dio kompleksnog broja.
	 * @return nova instanca <code>ComplexNumber</code> koja predstavlja kreirani imaginarni broj
	 */
	public static ComplexNumber fromImaginary (double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Metoda stvara <code>ComplexNumber</code> iz zapisa kompleksnog broja pomoću kuta i magitude.
	 * 
	 * @param magnitude magnituda kompleksnog broja.
	 * @param angle kut kompleksnog broja.
	 * @return nova instanca <code>ComplexNumber</code> koja predstavlja kreirani imaginarni broj
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if(angle < 0) throw new IllegalArgumentException("Angle mora biti veći ili jedank od 0 ("+angle+").");
		if(angle >= 2*Math.PI) throw new IllegalArgumentException("Angle mora biti manji od 2*PI ("+angle+").");

		double complex = magnitude * Math.sin(angle);
		double real = magnitude * Math.cos(angle);

		return new ComplexNumber(real,complex);
	}

	/**
	 * Metoda parsira dani <code>String</code> u komleksni broj.
	 * Praznine u izrazu nisu dozvoljene te isto tako imaginarana jedica "i" mora biti navedena poslije kompleksnog dijela broja.
	 * 
	 * @param s izraz koji se parsira u kompleksni broj.
	 * @return nova instanca <code>ComplexNumber</code> koja predstavlja kreirani imaginarni broj
	 * @throws IllegalArgumentException iznimka se baca ako je dani nekorektan izraz imaginarnog broja.
	 */
	public static ComplexNumber parse(String s) {
		String[] partOfNumber=s.replace("-", "+-").split("\\+");
		double real = 0;
		double complex = 0;

		if(partOfNumber.length > 3) throw new IllegalArgumentException("Pogrešan izraz! - detektirana su više od dva predznaka ili izraz sadrži"
				+ " nedozvoljene praznine");

		for (String part : partOfNumber) {
			if(part.length() == 0) continue;

			if(part.charAt(part.length()-1) == 'i') {
				if(complex != 0) throw new IllegalArgumentException("Pogrešan izraz! - detektirana su dva komleksna dijela što nije dozoljeno.");

				if(part.equals("i")) {
					complex = 1;
					continue;
				}
				if(part.equals("-i")) {
					complex = -1;
					continue;
				}

				try {
					complex = Double.parseDouble(part.substring(0, part.length()-1));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Pogrešan izraz! - nije moguće parsirati imaginarni dio broja ("
							+part.substring(0, part.length()-1)+").");
				}

			} else {
				if(real != 0) throw new IllegalArgumentException("Pogrešan izraz! - detektirana su dva realna dijela što nije dozoljeno.");

				try {
					real = Double.parseDouble(part);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Pogrešan izraz! - nije moguće parsirati realni dio broja ("
							+part+").");
				}
			}
		}

		return new ComplexNumber(real,complex);
	}

	/**
	 * Metoda vraća realni dio kompleksnog broja.
	 * 
	 * @return realni dio broja.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Metoda vraća imaginarni dio kompleksnog broja.
	 * 
	 * @return imaginarni dio broja.
	 */
	public double getImaginary() {
		return complex;
	}

	/**
	 * Metoda vraća magnitudu komleksnog broja u zapisu broja pomoću magnitude i kuta.
	 * 
	 * @return magnituda komleksnog broja.
	 */
	public double getMagnitude() {
		return Math.hypot(real, complex);
	}

	/**
	 * Metoda vraća kut komleksnog broja u zapisu broja pomoću magnitude i kuta.
	 * 
	 * @return kut komleksnog broja.
	 */
	public double getAngle() {
		double angle = Math.atan(complex/real);

		if(angle < 0) angle = angle + 2 * Math.PI;

		if(real < 0) angle = angle + Math.PI;

		return angle % (2 * Math.PI);
	}

	/**
	 * Metoda zbroja trenuti i dani komleksni broj.
	 * 
	 * @param c komleksni broj koji se nadodaje trenutnom.
	 * @return nova instanca <code>ComplexNumber</code> koja predstavlja izračunati imaginarni broj
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.complex + c.complex);
	}

	/**
	 * Metoda oduzima dani komleksni broj od trenutnog.
	 * 
	 * @param c komleksni broj s kojim se oduzima.
	 * @return nova instanca <code>ComplexNumber</code> koja predstavlja izračunati imaginarni broj
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.complex - c.complex);
	}

	/**
	 * Metoda množi dani komleksni broj s trenutnim.
	 * 
	 * @param c komleksni broj s kojim se množi.
	 * @return nova instanca <code>ComplexNumber</code> koja predstavlja izračunati imaginarni broj
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.real * c.real - this.complex * c.complex, this.real * c.complex + this.complex * c.real);
	}

	/**
	 * Metoda dijeli trenutni komleksni broj s danim.
	 * 
	 * @param c komleksni broj s kojim se dijeli.
	 * @return nova instanca <code>ComplexNumber</code> koja predstavlja izračunati imaginarni broj
	 */
	public ComplexNumber div(ComplexNumber c) {
		ComplexNumber mulByConjugate=this.mul(new ComplexNumber(c.real, -c.complex));
		return new ComplexNumber(mulByConjugate.real / Math.pow(c.getMagnitude(),2) ,
				mulByConjugate.complex / Math.pow(c.getMagnitude(),2) );
	}

	/**
	 * Metoda potencira trenutni komleksni broj na danu potenciju.
	 * 
	 * @param n potencija broja 
	 * @return nova instanca <code>ComplexNumber</code> koja predstavlja izračunati imaginarni broj
	 */
	public ComplexNumber power(int n) {
		if(n < 0) throw new IllegalArgumentException("n mora biti prirodan broj ili 0.");

		double magnitude = Math.pow(this.getMagnitude(),n);
		double angle = (this.getAngle() * n) % (2 * Math.PI);
		return fromMagnitudeAndAngle(magnitude, angle);
	}
	/**
	 * Metoda računa n-ti korijen od trenutnog komleksnog broja.
	 * 
	 * @param n n-ti korijen.
	 * @return polje <code>ComplexNumber</code> koje predstavlja izračunate komleksne brojeve.
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) throw new IllegalArgumentException("n mora biti prirodan broj.");

		double magnitude = Math.pow(this.getMagnitude(), 1.0 / n);

		ComplexNumber[] roots=new ComplexNumber[n];

		for (int i = 0; i < n; i++) {
			double angle=((this.getAngle() + 2 * Math.PI * i) / n) % (2 * Math.PI);
			roots[i]=fromMagnitudeAndAngle(magnitude, angle);
		}

		return roots;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ComplexNumber))
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		if (Math.abs(this.complex - other.complex) > 0.0001)
			return false;
		if (Math.abs(this.real - other.real) > 0.0001)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%.5f", real) + " " + String.format("%.5f", complex) +"i";
	}








}
