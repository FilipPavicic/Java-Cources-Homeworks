package hr.fer.zemris.math;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;



public class NewtonParallel {
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
		UserInterface ui = new UserInterface();
		FractalViewer.show(new MojProducer(workers, tracks, new ComplexRootedPolynomial(Complex.ONE, ui.getRoots())));
	}

	public static class MojProducer extends NewtonRaphsonProducer {
		int workers;
		int tracks;

		public MojProducer(int workers, int tracks, ComplexRootedPolynomial crp) {
			super(crp);
			this.workers = workers;
			this.tracks = tracks;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			short[] data = new short[width * height];
			if(tracks > height) tracks = height;
			int brojYPoTraci = height / tracks;

			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[workers];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}

			for(int i = 0; i < tracks; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i == tracks-1) {
					yMax = height - 1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(this, reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
		}

	}

	public static class PosaoIzracuna implements Runnable {
		NewtonRaphsonProducer nrp;
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		short[] data;
		AtomicBoolean cancel;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();

		private PosaoIzracuna() {
		}

		public PosaoIzracuna(NewtonRaphsonProducer nrp, double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				short[] data, AtomicBoolean cancel) {
			super();
			this.nrp = nrp;
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			this.cancel = cancel;
		}

		@Override
		public void run() {

			nrp.calculate(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel);

		}
	}
}
