package hr.fer.zemris.math;


import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;



public class Newton {
	
	public static class MojProducer extends NewtonRaphsonProducer {
		
		public MojProducer(ComplexRootedPolynomial crp) {
			super(crp);
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			short[] data = new short[width * height];
			
			calculate(reMin, reMax, imMin, imMax, width, height, 0, height-1, data, cancel);
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
		}
	
	}

	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
		FractalViewer.show(new MojProducer(new ComplexRootedPolynomial(Complex.ONE, ui.getRoots())));
	}


}
