package hr.fer.oprpp1.custom.collections;

/**
 * Razred koji na predstavlja kolekciju elemenata koji su indeksirani
 * Dopu�tene su duplicirane vrijednosti.
 * Nisu dopu�teni <code>Null</code> reference.
 * @author Filip Pavicic
 *
 */
public class ArrayIndexedCollection extends Collection {

	private int size;
	private Object[] elements;

	/**
	 * Konstruktor stvara Kolekciju zadane veli�ine
	 * 
	 * @param initialCapacity veli�ina kolekcije koja se stvara
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		this.inicialization(initialCapacity);
	}

	/**
	 * Konstruktor bez parametara koji stvara kolekciju veli�ine 16 elemenata
	 * 
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * Konstrukor koji stvara kolekciju zadane veli�ine i popunjava je s elementima predane kolekcije
	 * Ako je veli�ina predane kolekcije ve�a od <code>initialCapacity</code> stvara se kolekcija 
	 * kojoj je veli�ina ista kao i predanoj kolekciji.
	 * 
	 * @param collection predana kolekcija kojom se puni novonastala kolekcija
	 * @param initialCapacity veli�ina kolekcije koja se stvara.
	 * @throws NullPointerException ako je argument <code>collection</code> jedank <code>null</code>
	 */

	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		if(collection == null) throw new NullPointerException("Predani argument collection ne smije biti null");

		if(initialCapacity < collection.size()) initialCapacity=collection.size();
		this.inicialization(initialCapacity);

		this.addAll(collection);	
	}

	/**
	 * Konstrukor koji stvara kolekciju veli�ine 16 i popunjava je s elementima predane kolekcije
	 * 
	 * @param collection predana kolekcija kojom se puni novonastala kolekcija
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection,16);
	}

	/**
	 * Privatna metoda za inicjalizaciju ne stati�kih varijabli.
	 * Poziva se u konstruktorima
	 * 
	 * @param initialCapacity inicjalna veli�ina kolekcije.
	 * @throws IllegalArgumentException ako je initialCapacity manji od 1. 
	 */
	private void inicialization(int initialCapacity) {
		if(initialCapacity < 1) throw new IllegalArgumentException("Veli�ina kolekcija mora biti minimalno 1");
		this.size=0;
		elements=new Object[initialCapacity];
	}

	/**
	 * Provjera veli�inu polja prije dodavanja elementa i po potrebi ga pove�ava
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
	 * Vra�a element na zadanom indeksu
	 * 
	 * @param index indeks elementa koji se dohva�a
	 * @return vra�a <code>Object</code> na tra�enom indeksu
	 * @throws IndexOutOfBoundsException ako zadani index je manji od 0 ili ve�i od trenutne veli�ine kolekcije
	 */
	public Object get(int index) {
		if(index < 0) throw new IndexOutOfBoundsException("Index mora biti ve�i od 0, a unesen je "+index);
		if(index >= size) 
			throw new IndexOutOfBoundsException("Index mora biti manji od veli�ine trenute kolekcije "
					+size+", a unesen je"+index);

		return this.elements[index];
	}

	/**
	 * Dodava dani element na zadanu poziciju.
	 * @param value element koji se dodaje u kolekciju.
	 * @param position indeks na koji se dodaje element.
	 * @throws IndexOutOfBoundsException ako je zadana pozicija manji od 0 ili ve�i od trenutne veli�ine kolekcije+1
	 */
	public void insert(Object value, int position) {
		if(position < 0) throw new IndexOutOfBoundsException("Index mora biti ve�i od 0, a unesen je "+position);
		if(position > size) 
			throw new IndexOutOfBoundsException("Index mora biti manji ili jedank od veli�ine trenute kolekcije ("
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
	 * Vra�a poziciju danog objekta u kolekciji. Ako se objekt ne nalazi u kolekciji program vra�a -1.
	 * 
	 * @param value objekt za koej se tra�i pozicija
	 * @return pozicija objekta, ako objekta nema onda -1.s
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if(value.equals(elements[i])) return i;
		}
		return -1;
	}

	/**
	 * Dohva�a element na danom indeksu.
	 * 
	 * @param index
	 * @throws IndexOutOfBoundsException ako zadani index je manji od 0 ili ve�i od trenutne veli�ine kolekcije
	 */
	public void remove(int index) {
		if(index < 0) throw new IndexOutOfBoundsException("Index mora biti ve�i od 0, a unesen je "+index);
		if(index >= size) 
			throw new IndexOutOfBoundsException("Index mora biti manji od veli�ine trenute kolekcije "
					+size+", a unesen je"+index);

		remove(elements[index]);
	}









}
