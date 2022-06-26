package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje koje definiciju metoda get, insert, indexOf, remove
 * 
 * @author Filip Pavicic
 *
 */
public interface List extends Collection {
	

	/**
	 * Vraća element na zadanom indeksu
	 * 
	 * @param index indeks elementa koji se dohvača
	 * @return vraća <code>Object</code> na traženom indeksu
	 * @throws IndexOutOfBoundsException ako zadani index je manji od 0 ili veći od trenutne veličine kolekcije
	 */
	Object get(int index);

	/**
	 * Dodava dani element na zadanu poziciju.
	 * @param value element koji se dodaje u kolekciju.
	 * @param position indeks na koji se dodaje element.
	 * @throws IndexOutOfBoundsException ako je zadana pozicija manji od 0 ili veći od trenutne veličine kolekcije+1
	 */
	void insert(Object value, int position);

	/**
	 * Vraća poziciju danog objekta u kolekciji. Ako se objekt ne nalazi u kolekciji program vraća -1.
	 * 
	 * @param value objekt za koej se traži pozicija
	 * @return pozicija objekta, ako objekta nema onda -1.s
	 */
	int indexOf(Object value);

	/**
	 * Dohvaća element na danom indeksu.
	 * 
	 * @param index
	 * @throws IndexOutOfBoundsException ako zadani index je manji od 0 ili veći od trenutne veličine kolekcije
	 */
	void remove(int index);

	
}
