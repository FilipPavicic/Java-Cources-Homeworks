package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
	private Complex[] roots;

	public UserInterface() {
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
		this.roots = roots.toArray(new Complex[] {Complex.ONE});
	}

	public Complex[] getRoots() {
		return roots;
	}



}
