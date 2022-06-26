package hr.fer.oprpp1.custom.collections;

import java.util.EmptyStackException;

/**
 * Razred koji predstvalja imlementaciju Stoga koji mo�e pohranjivati elemente tipa <code>Object</code>
 * 
 * @author Filip Pavicic
 *
 */
public class ObjectStack {
	private ArrayIndexedCollection collection;

	/**
	 * Konstukotr koji inicijalizira Stog inicijalne veli�ine 16.
	 * 
	 */
	public ObjectStack() {
		collection=new ArrayIndexedCollection();
	}

	/**
	 * Konstruktor koji inicijalizira stog dane veli�ine.
	 * 
	 * @param initialCapacity veli�ina stoga.
	 */
	public ObjectStack(int initialCapacity) {
		collection=new ArrayIndexedCollection(initialCapacity);
	}

	/**
	 * Ispituje da li je stog prazna.
	 * 
	 * @return <code>true</code> ako je kelekcija prazna, ina�e <code>false</code>.
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	/**
	 * Vraca broj trenutno spremljenih objekata na stogu.
	 * 
	 * @return broj objekata u kolekciji.
	 */
	public int size() {
		return collection.size();
	}

	/**
	 * Stavlja element na vrh stoga.
	 * 
	 * @param value Objekt koji se dodaje na stog.
	 */
	public void push(Object value) {
		collection.add(value);
	}

	/**
	 * Uzima element s vrha stoga bri�e ga, te ga vra�a.
	 * 
	 * @return <code>Object</code> s vrha stoga.
	 */
	public Object pop() {
		if (collection.size() == 0) throw new EmptyStackException();

		Object tmp=collection.get(collection.size()-1);
		collection.remove(collection.size()-1);
		return tmp;

	}

	/**
	 * Uzima element s vrha stoga te ga pri tome ne bri�e,te ga vra�a.
	 * 
	 * @return <code>Object</code> s vrha stoga.
	 */
	public Object peek() {
		if (collection.size() == 0) throw new EmptyStackException();

		return collection.get(collection.size()-1);
	}

	/**
	 * Bri�e sve elemente s stoga.
	 * 
	 */
	public void clear() {
		collection.clear();
	}

}
