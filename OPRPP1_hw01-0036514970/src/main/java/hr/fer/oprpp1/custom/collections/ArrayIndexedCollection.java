package hr.fer.oprpp1.custom.collections;

/**
 * Razred koji na predstavlja kolekciju elemenata koji su indeksirani
 * Dopuštene su duplicirane vrijednosti.
 * Nisu dopušteni <code>Null</code> reference.
 * @author Filip Pavicic
 *
 */
public class ArrayIndexedCollection extends Collection {

	private int size;
	private Object[] elements;

	/**
	 * Konstruktor stvara Kolekciju zadane velièine
	 * 
	 * @param initialCapacity velièina kolekcije koja se stvara
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		this.inicialization(initialCapacity);
	}

	/**
	 * Konstruktor bez parametara koji stvara kolekciju velièine 16 elemenata
	 * 
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * Konstrukor koji stvara kolekciju zadane velièine i popunjava je s elementima predane kolekcije
	 * Ako je velièina predane kolekcije veèa od <code>initialCapacity</code> stvara se kolekcija 
	 * kojoj je velièina ista kao i predanoj kolekciji.
	 * 
	 * @param collection predana kolekcija kojom se puni novonastala kolekcija
	 * @param initialCapacity velièina kolekcije koja se stvara.
	 * @throws NullPointerException ako je argument <code>collection</code> jedank <code>null</code>
	 */

	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		if(collection == null) throw new NullPointerException("Predani argument collection ne smije biti null");

		if(initialCapacity < collection.size()) initialCapacity=collection.size();
		this.inicialization(initialCapacity);

		this.addAll(collection);	
	}

	/**
	 * Konstrukor koji stvara kolekciju velièine 16 i popunjava je s elementima predane kolekcije
	 * 
	 * @param collection predana kolekcija kojom se puni novonastala kolekcija
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection,16);
	}

	/**
	 * Privatna metoda za inicjalizaciju ne statièkih varijabli.
	 * Poziva se u konstruktorima
	 * 
	 * @param initialCapacity inicjalna velièina kolekcije.
	 * @throws IllegalArgumentException ako je initialCapacity manji od 1. 
	 */
	private void inicialization(int initialCapacity) {
		if(initialCapacity < 1) throw new IllegalArgumentException("Velièina kolekcija mora biti minimalno 1");
		this.size=0;
		elements=new Object[initialCapacity];
	}

	/**
	 * Provjera velièinu polja prije dodavanja elementa i po potrebi ga poveæava
	 */
	private void checkSizeOfArray() {
		if(size==elements.length) {
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
	public void add(Object value) {
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
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i]=null;
		}
		this.size=0;
	}

	/**
	 * Vraæa element na zadanom indeksu
	 * 
	 * @param index indeks elementa koji se dohvaèa
	 * @return vraæa <code>Object</code> na traženom indeksu
	 * @throws IndexOutOfBoundsException ako zadani index je manji od 0 ili veæi od trenutne velièine kolekcije
	 */
	public Object get(int index) {
		if(index < 0) throw new IndexOutOfBoundsException("Index mora biti veæi od 0, a unesen je "+index);
		if(index >= size) 
			throw new IndexOutOfBoundsException("Index mora biti manji od velièine trenute kolekcije "
					+size+", a unesen je"+index);

		return this.elements[index];
	}

	/**
	 * Dodava dani element na zadanu poziciju.
	 * @param value element koji se dodaje u kolekciju.
	 * @param position indeks na koji se dodaje element.
	 * @throws IndexOutOfBoundsException ako je zadana pozicija manji od 0 ili veæi od trenutne velièine kolekcije+1
	 */
	public void insert(Object value, int position) {
		if(position < 0) throw new IndexOutOfBoundsException("Index mora biti veæi od 0, a unesen je "+position);
		if(position > size) 
			throw new IndexOutOfBoundsException("Index mora biti manji ili jedank od velièine trenute kolekcije ("
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
	}

	/**
	 * Vraæa poziciju danog objekta u kolekciji. Ako se objekt ne nalazi u kolekciji program vraæa -1.
	 * 
	 * @param value objekt za koej se traži pozicija
	 * @return pozicija objekta, ako objekta nema onda -1.s
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if(value.equals(elements[i])) return i;
		}
		return -1;
	}

	/**
	 * Dohvaæa element na danom indeksu.
	 * 
	 * @param index
	 * @throws IndexOutOfBoundsException ako zadani index je manji od 0 ili veæi od trenutne velièine kolekcije
	 */
	public void remove(int index) {
		if(index < 0) throw new IndexOutOfBoundsException("Index mora biti veæi od 0, a unesen je "+index);
		if(index >= size) 
			throw new IndexOutOfBoundsException("Index mora biti manji od velièine trenute kolekcije "
					+size+", a unesen je"+index);

		remove(elements[index]);
	}









}
