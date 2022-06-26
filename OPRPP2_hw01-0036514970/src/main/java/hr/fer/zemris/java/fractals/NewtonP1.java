package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

public class NewtonP1 {


	public static void main(String[] args) {
		int workers = Runtime.getRuntime().availableProcessors();
		int tracks = 4* workers;
		if(args.length > 2) System.err.println("Program očekuje maksimalno 2 argumenta a uneseno je : "
				+ args.length + " argumenta");
		for (String arg : args) {
			String[] partArg = arg.split("=");
			int num;

			try {
				num = Integer.parseInt(partArg[1]);
				if(num < 1) throw new IllegalArgumentException("Broj radnika i dretvi mora biti baram 1");
			}catch (NumberFormatException e) {
				System.err.println(partArg[1] + " nije moguće parsirati kao Integer");
				return;
			}catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
				return;
			}

			switch (partArg[0]) {
			case "--workers", "-w" -> workers = num;
			case "--tracks", "-t" -> tracks = num;
			default -> System.err.println("Nepoznati argument: " + partArg[0]);
			}
		}
		System.out.println("workers = " + workers + ", tracks = " +tracks);
		Complex[] roots = Utils.UserInterfaceRoots();
		FractalViewer.show(new MyFractalProducer(workers, tracks, new ComplexRootedPolynomial(Complex.ONE, roots)));
	}
	
	public static class MyFractalProducer implements IFractalProducer {
		int wokers;
		int tracks;
		
		ComplexRootedPolynomial crp;
		ComplexPolynomial polynomial;
		ComplexPolynomial derived;
		
		ExecutorService pool;
		
		public MyFractalProducer(int wokers, int tracks, ComplexRootedPolynomial crp) {
			super();
			this.wokers = wokers;
			this.tracks = tracks;
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
			if(tracks > height) tracks = height;
			int brojYPoTraci = height / tracks;
			
			List<Future<?>> rezultati = new ArrayList<Future<?>>();
			for(int i = 0; i < tracks; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = i == tracks-1 ? yMax = height - 1 : (i+1)*brojYPoTraci-1;
				
				rezultati.add(pool.submit(new Runnable() {
					
					@Override
					public void run() {
						Utils.calculate(crp, polynomial, derived, reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel);
						
					}
				}));
			}
			
			for(Future<?> f : rezultati) {
				while(true) {
					try {
						f.get();
						break;
					} catch (InterruptedException e) {
					}
					catch (ExecutionException e) {
						System.err.println("Dogodila se neočekivana greška prekidam izvođenje");
						System.exit(1);
					}
				}
			
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
			
			
		}

		@Override
		public void setup() {
			pool = Executors.newFixedThreadPool(wokers);
			
		}
		
	}
}
