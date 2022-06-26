package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje koje sadrži metode za rad s Kolekcijama
 * 
 * @author Filip Pavicic
 *
 */

public interface Collection {


	/**
	 * Ispituje da li je kolekcija prazna.
	 * 
	 * @return <code>true</code> ako je kelekcija prazna, inače <code>false</code>.
	 */
	default boolean isEmpty() {
		if (this.size() != 0) {
			return false;
		}
		return true;
	}

	/**
	 * Vraća broj trenutno spremljenih objekata u kolekciji.
	 * 
	 * @return broj objekata u kolekciji.
	 */
	int size();

	/**
	 * Dodaje dani objekt u kolekciju.
	 * 
	 * @param value objekt koji se dodaje u kolekciju.
	 */
	void add(Object value);
	
	/**
	 * Ispituje da li kolekcija sadrži dani objekt.
	 * Moguće je ispitati da li kolekcija sadrži <code>null</code>.
	 * Koristi se metoda equals za ispitavanje.
	 * 
	 * @param value dani objekt za koji se ispituje postoji li u kolekciji
	 * @return <code>true</code> ako se u kolekciji nalazi dani objekt, inače <code>false</code>
	 */
	boolean contains(Object value);

	/**
	 * Isptuje postoji li dani element i ako postoji briše ga iz kolekcije
	 * 
	 * @param value dani objekt koji se brise iz kolekcije
	 * @return <code>true</code> ako je objekt pronađen i uspješno obrisan, inače <code>false</code>
	 */
	boolean remove(Object value);

	/**
	 * Stvara novo polje koje je veličine kao i kolekcija.
	 * Popunjuje polje s elementima iz kolekcije.
	 * Ova metoda nikada ne vraća<code>null</code>
	 * 
	 * @return polje koje sadrži elemente iz kolekcije.
	 * 
	 */
	Object[] toArray();

	/**
	 * Metoda poziva metodu <code>process</code> iz razreda.
	 * <code>hr.fer.oprpp1.custom.collections.Processor</code> nad svakim elementom u kolekciji.
	 * 
	 * @param processor Processor koji se izvršava nad svakim elementom.
	 */
	default void forEach(Processor processor) {
		ElementsGetter elementsGetter = this.createElementsGetter();
		
		while(elementsGetter.hasNextElement()) {
			Object object = elementsGetter.getNextElement();
			processor.process(object);
		}
	}
	
	/**
	 * Dodaje u trenutnu kolekciju sve elemente iz dane kolekcije
	 * 
	 * @param other kolekcija čiji se elementi dodaju.
	 */
	default void addAll(Collection other) {

		/**
		 * Lokalni razred koji poziva metodu <code>add</code> iz razreda
		 * <code>hr.fer.oprpp1.custom.collections.Collections</code>.
		 * 
		 * @author Filip Pavicic
		 *
		 */
		class LocalProcessor implements Processor {

			@Override
			public void process(Object value) {
				add(value);
			}
		}

		other.forEach(new LocalProcessor());
	}

	/**
	 * Briše sve elemente iz kolekcije.
	 */
	void clear();
	
	/**
	 * Stvara objekt koji može korisniku vračati element po element
	 * 
	 * @return Objekt koji implementira <code>ElementsGetter</code>
	 */
	ElementsGetter createElementsGetter();
	
	/**
	 * Metoda za svaki element iz predane kolekcije poziva tester 
	 * i ako je test istinit dodaje element u trenutnu kolekciju.
	 * 
	 * @param col kolekcija elemenata iz koje se svaki element podvrgava testu.
	 * @param tester test koji se izvodi nad elementima polja,
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter elementsGetter = col.createElementsGetter();
		while(elementsGetter.hasNextElement()) {
				Object object=elementsGetter.getNextElement();
			if(tester.test(object)) this.add(object);
		}
	}
}

