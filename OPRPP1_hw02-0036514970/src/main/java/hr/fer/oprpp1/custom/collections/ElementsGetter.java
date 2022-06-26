package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

/**
 * Sucelje defnira metode za provjeru i vraćanje elementa po elementa
 * 
 * @author Filip Pavicic
 *
 */
public interface ElementsGetter {
	
	/**
	 * Provjera ima li još elemenata u kolekciji za vratiti koriniku
	 * 
	 * @return <code>true</code> ako im ajoš elemenata, inače <code>false</code> 
	 */
	boolean hasNextElement();
	
	/**
	 * Vraća sljedeći element kolekcije korisniku.
	 * 
	 * @return sljedeći element
	 * @throws	NoSuchElementException ako nema sljedećeg elementa u kolekciji.
	 * 
	 */
	default Object getNextElement() {		
		throw new NoSuchElementException("Nema više elemenata u kolekciji");
	}
	
	/**
	 * Poziva dani processor za svaki element u kolekciji.
	 * 
	 * @param p <code>Proccesor</code> koji se poziva.
	 */
	default void processRemaining(Processor p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}

}
