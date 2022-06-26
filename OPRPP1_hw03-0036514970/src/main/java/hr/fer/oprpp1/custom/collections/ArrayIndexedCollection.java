package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;

/**
 * Razred koji na predstavlja kolekciju elemenata koji su indeksirani
 * Dopuštene su duplicirane vrijednosti.
 * Nisu dopušteni <code>Null</code> reference.
 * @author Filip Pavicic
 *
 */
public class ArrayIndexedCollection<E> implements List<E> {

	private int size;
	private Object[] elements;
	private long modificationCount = 0;

	/**
	 * Konstruktor stvara Kolekciju zadane veličine
	 * 
	 * @param initialCapacity veličina kolekcije koja se stvara
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		this.inicialization(initialCapacity);
	}

	/**
	 * Konstruktor bez parametara koji stvara kolekciju veličine 16 elemenata
	 * 
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * Konstrukor koji stvara kolekciju zadane veličine i popunjava je s elementima predane kolekcije
	 * Ako je veličina predane kolekcije veča od <code>initialCapacity</code> stvara se kolekcija 
	 * kojoj je veličina ista kao i predanoj kolekciji.
	 * 
	 * @param collection predana kolekcija kojom se puni novonastala kolekcija
	 * @param initialCapacity veličina kolekcije koja se stvara.
	 * @throws NullPointerException ako je argument <code>collection</code> jedank <code>null</code>
	 */

	public ArrayIndexedCollection(Collection<? extends E> collection, int initialCapacity) {
		if(collection == null) throw new NullPointerException("Predani argument collection ne smije biti null");

		if(initialCapacity < collection.size()) initialCapacity=collection.size();
		this.inicialization(initialCapacity);

		this.addAll(collection);	
	}

	/**
	 * Konstrukor koji stvara kolekciju veličine 16 i popunjava je s elementima predane kolekcije
	 * 
	 * @param collection predana kolekcija kojom se puni novonastala kolekcija
	 */
	public ArrayIndexedCollection(Collection<? extends E> collection) {
		this(collection,16);
	}

	/**
	 * Privatna metoda za inicjalizaciju ne statičkih varijabli.
	 * Poziva se u konstruktorima
	 * 
	 * @param initialCapacity inicjalna veličina kolekcije.
	 * @throws IllegalArgumentException ako je initialCapacity manji od 1. 
	 */
	private void inicialization(int initialCapacity) {
		if(initialCapacity < 1) throw new IllegalArgumentException("Veličina kolekcija mora biti minimalno 1");
		this.size=0;
		elements= new Object[initialCapacity];
	}

	/**
	 * Provjera veličinu polja prije dodavanja elementa i po potrebi ga povećava
	 */
	private void checkSizeOfArray() {
		if(size==elements.length) {
			modificationCount++;
			
			Object[] old_elements=this.elements;
			Object[] new_elements=new Object[2*old_elements.length];
			for (int i = 0; i < old_elements.length; i++) {
				new_elements[i]=old_elements[i];
			}
			this.elements=new_elements;
		}
	}

	@Override
	public int size() {
		return this.size;
	}

	/**
	 * @throws NullPointerException ako je argument value <code>null</code>
	 */
	@Override
	public void add(E value) {
		if(value==null) throw new NullPointerException("Argument value ne smije biti Null");

		this.checkSizeOfArray();

		this.elements[size]=value;
		size++;

	}

	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < size; i++) {
			if(value.equals(elements[i])) return true;
		}

		return false;
	}

	@Override
	public boolean remove(Object value) {
		Object[] tmp = new Object[this.elements.length];
		boolean contains = false;

		for (int i = 0,index=0; i < size; i++,index++) {
			if(value.equals(elements[i])) {
				i++;
				contains=true;
			}

			tmp[index]=elements[i];
		}
		this.elements=tmp;
		size--;
		modificationCount++;
		return contains;

	}

	@Override
	public Object[] toArray() {
		Object[] tmp=new Object[size];
		for (int i = 0; i < size; i++) {
			tmp[i]=this.elements[i];
		}
		return tmp;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i]=null;
		}
		this.size=0;
		modificationCount++;
	}
	
	@SuppressWarnings("unchecked")
	public E get(int index) {
		if(index < 0) throw new IndexOutOfBoundsException("Index mora biti veći od 0, a unesen je "+index);
		if(index >= size) 
			throw new IndexOutOfBoundsException("Index mora biti manji od veličine trenute kolekcije "
					+size+", a unesen je"+index);

		return (E) this.elements[index];
	}

	public void insert(E value, int position) {
		if(position < 0) throw new IndexOutOfBoundsException("Index mora biti veći od 0, a unesen je "+position);
		if(position > size) 
			throw new IndexOutOfBoundsException("Index mora biti manji ili jedank od veličine trenute kolekcije ("
					+size+"), a unesen je"+position);

		// ako je pozicija dodavanja na kraj to je ekvivalntno metodi add
		if(position==size) {
			this.add(value);
			return;
		}

		this.checkSizeOfArray();

		Object[] tmp=new Object[this.elements.length];
		this.size++;
		for (int i = 0,index=0; i < this.size; i++,index++) {
			if(position==i) {
				tmp[i]=value;
				i++;
			};
			tmp[i]=this.elements[index];
		}
		this.elements=tmp;
		modificationCount++;
	}

	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if(value.equals(elements[i])) return i;
		}
		return -1;
	}

	public void remove(int index) {
		if(index < 0) throw new IndexOutOfBoundsException("Index mora biti veći od 0, a unesen je "+index);
		if(index >= size) 
			throw new IndexOutOfBoundsException("Index mora biti manji od veličine trenute kolekcije "
					+size+", a unesen je"+index);

		remove(elements[index]);
	}
	
	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new ArrayIndexedCollectionElementsGetter();
	}
	
	private class ArrayIndexedCollectionElementsGetter implements ElementsGetter<E>{
		
		private int position;
		private long savedModificationCount;
		
		public ArrayIndexedCollectionElementsGetter() {
			position = 0;
			savedModificationCount = modificationCount;
		}
		
		/**
		 * 
		 * @throws ConcurrentModificationException iznimka se baca ako se kolekcija strukturalno promijenila od poziva metode <code>createElementsGetter()</code>
		 */
		@Override
		public boolean hasNextElement() {
			isChange();
			return position < size ? true : false;
		}
		
		/**
		 * 
		 * @throws ConcurrentModificationException iznimka se baca ako se kolekcija strukturalno promijenila od poziva metode <code>createElementsGetter()</code>
		 */
		@Override
		public E getNextElement() {
			isChange();
			
			if(!hasNextElement()) ElementsGetter.super.getNextElement();
			
			E object = get(position);
			position++;
			return object;
		}
		
		private void isChange() {
			if(savedModificationCount != modificationCount) throw new ConcurrentModificationException(
					"Kolekcija se promjenila od poziva metode createElementsGetter()");
		}
		
	}
	
}