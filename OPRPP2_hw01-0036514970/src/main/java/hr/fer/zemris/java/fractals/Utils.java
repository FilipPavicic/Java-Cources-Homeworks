package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Utils {
	final static double CONVERGANCE_TRESHOLD = 1E-3;
	final static int MAX_ITER = 16 * 16 * 16;

	public static Complex[]  UserInterfaceRoots() {
		List<Complex> roots = new ArrayList<Complex>();
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		int i = 1;
		while(true) {
			System.out.print("Root " + i + "> ");
			String sljedeci = sc.nextLine();
			if(sljedeci.equals("done")) break;
			try{
				Complex br = Complex.parseComplex(sljedeci);
				roots.add(br);
			}catch (IllegalArgumentException  e) {
				System.out.println("Zapis: "+ sljedeci + " nije moguće parsirati, provjerite unos i pokušajte ponovo.");
				continue;
			}
			i++;
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		return  roots.toArray(new Complex[] {Complex.ONE});
	}
	
	public static void calculate(ComplexRootedPolynomial crp, ComplexPolynomial polynomial, ComplexPolynomial derived, double reMin, double reMax, double imMin, double imMax,
			int width, int height, int yMin, int yMax, short[] data, AtomicBoolean cancel) {
		int offset = yMin * width;
		for(int y = yMin; y <= yMax; y++) {
			if(cancel.get()) break;
			for(int x = 0; x < width; x++) {
				Complex zn = map_to_complex_plain(x, y, width, height, reMin, reMax, imMin, imMax);
				int iters = 0;
				double module;
				do {
					Complex numerator = polynomial.apply(zn);
					Complex denominator = derived.apply(zn);
					Complex znold = zn;
					Complex fraction = numerator.divide(denominator);
					zn = zn.sub(fraction);
					module = znold.sub(zn).module();
					iters++;
				} while(module > CONVERGANCE_TRESHOLD && iters < MAX_ITER);
				short index = (short) crp.indexOfClosestRootFor(zn, 2 * CONVERGANCE_TRESHOLD);
				data[offset++]= (short) (index+1);

			}
		}
	}
	
	private static Complex map_to_complex_plain(int x, int y, int width,
			int height, double reMin, double reMax, double imMin,
			double imMax) {
		double cre = x / (width-1.0) * (reMax - reMin) + reMin;
		double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
		return new Complex(cre, cim);
	}
	
}
