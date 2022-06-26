package hr.fer.zemris.java.fractals;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

public class NewtonP2 {


	public static void main(String[] args) {
		int mintracks = 16;
		if(args.length > 1) System.err.println("Program očekuje maksimalno 1 argumenta a uneseno je : "
				+ args.length + " argumenta");
		for (String arg : args) {
			String[] partArg = arg.split("=");
			int num;

			try {
				num = Integer.parseInt(partArg[1]);
				if(num < 1) throw new IllegalArgumentException("Broj redaka mora biti baram 1");
			}catch (NumberFormatException e) {
				System.err.println(partArg[1] + " nije moguće parsirati kao Integer");
				return;
			}catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
				return;
			}

			switch (partArg[0]) {
			case "--mintracks", "-m" -> mintracks = num;
			default -> System.err.println("Nepoznati argument: " + partArg[0]);
			}
		}
		System.out.println("mintracks = " + mintracks);
		Complex[] roots = Utils.UserInterfaceRoots();
		FractalViewer.show(new MyFractalProducer(mintracks, new ComplexRootedPolynomial(Complex.ONE, roots)));
	}
	
	public static class MyFractalProducer implements IFractalProducer {
		int mintracks;
		
		ComplexRootedPolynomial crp;
		ComplexPolynomial polynomial;
		ComplexPolynomial derived;
		
		ForkJoinPool pool;
		
		public MyFractalProducer(int mintracks, ComplexRootedPolynomial crp) {
			super();
			this.mintracks = mintracks;
			this.crp = crp;
			polynomial = crp.toComplexPolynom();
			derived = polynomial.derive();
		}

		@Override
		public void close() {
			pool.shutdown();
			
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			short[] data = new short[width * height];
			
			class Posao extends RecursiveAction {
				private static final long serialVersionUID = 1L;
				int yMin;
				int yMax;
				

				public Posao(int yMin, int yMax) {
					super();
					System.out.println("ymin: " + yMin + ", ymax: " + yMax);
					this.yMin = yMin;
					this.yMax = yMax;
				}


				@Override
				protected void compute() {
					
					if(yMax - yMin <= mintracks) {
						Utils.calculate(crp, polynomial, derived, reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel);
						return;
					}
					
					Posao p1 = new Posao(yMin, yMin + (yMax - yMin) / 2);
					Posao p2 = new Posao(yMin + (yMax - yMin) / 2 +1, yMax);
					invokeAll(p1, p2);
				}
			}
			Posao p = new Posao(0, height -1);
			pool.invoke(p);
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
			
			
		}

		@Override
		public void setup() {
			pool = new ForkJoinPool();
			
		}
		
	}
}
