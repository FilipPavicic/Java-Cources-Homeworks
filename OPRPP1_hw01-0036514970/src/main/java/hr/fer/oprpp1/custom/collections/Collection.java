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
	 * @return <code>true</code> ako je kelekcija prazna, inaèe <code>false</code>.
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
	 * Ispituje da li kolekcija sadrži dani objekt.
	 * Moguæe je ispitati da li kolekcija sadrži <code>null</code>.
	 * Koristi se moteoda equals za ispitavanje.
	 * 
	 * @param value dani objekt za koji se ispituje postoji li u kolekciji
	 * @return <code>true</code> ako se u kolekciji nalazi dani objekt, inaèe <code>false</code>
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Isptuje postoji li dani element i ako postoji briše ga iz kolekcije
	 * 
	 * @param value dani objekt koji se briše iz kolekcije
	 * @return <code>true</code> ako je objekt pronaðen i uspješno obrisan, inaèe <code>false</code>
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Stvara novo polje koje je velièine kao i kolekcija.
	 * Popunjuje polje s elementima iz kolekcije.
	 * Ova metoda nikada ne vraæa <code>null</code>
	 * 
	 * @return polje koje sadrži elemente iz kolekcije.
	 * @throws	UnsupportedOperationException ako se metoda poziva,a da prije toga nije bila nasljeðena
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Metoda poziva metodu <code>process</code> iz razreda 
	 * <code>hr.fer.oprpp1.custom.collections.Processor</code> nad svakim elementom u kolekciji.
	 * 
	 * @param processor Processor koji se izvršava nad svakim elementom
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
	 * Briše sve elemente iz kolekcije.
	 */
	void clear() {

	}

}

