package hr.fer.oprpp1.custom.collections;

/**
 * Razred predstavlja generalnu kolekciju objekata
 * 
 * @author Filip Pavicic
 *
 */

public class Collection {

	protected Collection() {

	}
	/**
	 * Ispituje da li je kolekcija prazna.
	 * 
	 * @return <code>true</code> ako je kelekcija prazna, ina�e <code>false</code>.
	 */
	public boolean isEmpty() {
		if (this.size() != 0) {
			return false;
		}
		return true;
	}

	/**
	 * Vraca broj trenutno spremljenih objekata u kolekciji.
	 * 
	 * @return broj objekata u kolekciji.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Dodaje dani objekt u kolekciju.
	 * 
	 * @param value objekt koji se dodaje u kolekciju.
	 */
	public void add(Object value) {

	}

	/**
	 * Ispituje da li kolekcija sadr�i dani objekt.
	 * Mogu�e je ispitati da li kolekcija sadr�i <code>null</code>.
	 * Koristi se moteoda equals za ispitavanje.
	 * 
	 * @param value dani objekt za koji se ispituje postoji li u kolekciji
	 * @return <code>true</code> ako se u kolekciji nalazi dani objekt, ina�e <code>false</code>
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Isptuje postoji li dani element i ako postoji bri�e ga iz kolekcije
	 * 
	 * @param value dani objekt koji se bri�e iz kolekcije
	 * @return <code>true</code> ako je objekt prona�en i uspje�no obrisan, ina�e <code>false</code>
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Stvara novo polje koje je veli�ine kao i kolekcija.
	 * Popunjuje polje s elementima iz kolekcije.
	 * Ova metoda nikada ne vra�a <code>null</code>
	 * 
	 * @return polje koje sadr�i elemente iz kolekcije.
	 * @throws	UnsupportedOperationException ako se metoda poziva,a da prije toga nije bila naslje�ena
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Metoda poziva metodu <code>process</code> iz razreda 
	 * <code>hr.fer.oprpp1.custom.collections.Processor</code> nad svakim elementom u kolekciji.
	 * 
	 * @param processor Processor koji se izvr�ava nad svakim elementom
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Dodaje u trenutnu kolekciju sve elemente iz dane kolekcije
	 * 
	 * @param other
	 */
	public void addAll(Collection other) {

		/**
		 * Lokalni razred koji poziva metodu <code>add</code> iz razreda
		 * <code>hr.fer.oprpp1.custom.collections.Collections</code>.
		 * 
		 * @author Filip Pavicic
		 *
		 */
		class LocalProcessor extends Processor {

			@Override
			public void process(Object value) {
				add(value);
			}
		}

		other.forEach(new LocalProcessor());
	}

	/**
	 * Bri�e sve elemente iz kolekcije.
	 */
	void clear() {

	}

}

