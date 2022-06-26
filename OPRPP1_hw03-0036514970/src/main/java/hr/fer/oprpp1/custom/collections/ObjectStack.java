package hr.fer.oprpp1.custom.collections;

import java.util.EmptyStackException;

/**
 * Razred koji predstvalja imlementaciju Stoga koji može pohranjivati elemente tipa <code>Object</code>
 * 
 * @author Filip Pavicic
 *
 */
public class ObjectStack<E> {
	private ArrayIndexedCollection<E> collection;

	/**
	 * Konstukotr koji inicijalizira Stog inicijalne veličine 16.
	 * 
	 */
	public ObjectStack() {
		collection=new ArrayIndexedCollection<E>();
	}

	/**
	 * Konstruktor koji inicijalizira stog dane veličine.
	 * 
	 * @param initialCapacity veličina stoga.
	 */
	public ObjectStack(int initialCapacity) {
		collection=new ArrayIndexedCollection<E>(initialCapacity);
	}

	/**
	 * Ispituje da li je stog prazna.
	 * 
	 * @return <code>true</code> ako je kelekcija prazna, inače <code>false</code>.
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
	public void push(E value) {
		collection.add(value);
	}

	/**
	 * Uzima element s vrha stoga briše ga, te ga vraća.
	 * 
	 * @return <code>Object</code> s vrha stoga.
	 */
	public E pop() {
		if (collection.size() == 0) throw new EmptyStackException();

		E tmp=collection.get(collection.size()-1);
		collection.remove(collection.size()-1);
		return tmp;

	}

	/**
	 * Uzima element s vrha stoga te ga pri tome ne briše,te ga vraća.
	 * 
	 * @return <code>Object</code> s vrha stoga.
	 */
	public E peek() {
		if (collection.size() == 0) throw new EmptyStackException();

		return collection.get(collection.size()-1);
	}

	/**
	 * Briše sve elemente s stoga.
	 * 
	 */
	public void clear() {
		collection.clear();
	}

}
