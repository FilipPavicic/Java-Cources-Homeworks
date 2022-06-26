package hr.fer.zemris.math;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;

public abstract class NewtonRaphsonProducer implements IFractalProducer{
	final double CONVERGANCE_TRESHOLD = 1E-3;
	final int MAX_ITER = 16 * 16 * 16;
	ComplexRootedPolynomial crp;
	ComplexPolynomial polynomial;
	ComplexPolynomial derived;
	
	
	public NewtonRaphsonProducer(ComplexRootedPolynomial crp) {
		this.crp = crp;
		polynomial = crp.toComplexPolynom();
		derived = polynomial.derive();
	}

	public void calculate(double reMin, double reMax, double imMin, double imMax,
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
	
	private Complex map_to_complex_plain(int x, int y, int width,
			int height, double reMin, double reMax, double imMin,
			double imMax) {
		double cre = x / (width-1.0) * (reMax - reMin) + reMin;
		double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
		return new Complex(cre, cim);
	}
}
