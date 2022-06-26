package hr.fer.oprpp1.custom.collections;

/**
 * Razred predstavlja dvostruko povezanu listu koja ima pokaziva� na po�etak i na kraj liste.
 * Dopu�tene su duplicirane vrijednosti.
 * Nisu dopu�teni <code>Null</code> reference.
 * 
 * @author Filip Pavicic
 *
 */
public class LinkedListIndexedCollection  extends Collection{

	/**
	 * Razred predstavlja �vor koji se korsiti u razredu LinkedListIndexedCollection. 
	 * Svaki �vor sadr�i pokaziva� na sljede�i i prethodni element.
	 * 
	 * @author Filip Pavicic
	 *
	 */
	private static class ListNode {
		Object value;
		ListNode next;
		ListNode previos;

		/**
		 * Konstruktor za stvaranje <code>ListNode</code> �vora
		 * 
		 * @param value vrijednost koja se zapisuje u �vor
		 * @param next pokaziva� na sljede�i element na kojeg �e pokazavati novonastali �vor
		 * @param previos pokaziva� na prethodni element na kojeg �e pokazavati novonastali �vor
		 */
		public ListNode(Object value, ListNode next, ListNode previos) {
			this.value = value;
			this.next = next;
			this.previos = previos;
		}


	}

	private int size;
	private ListNode first;
	private ListNode last;

	/**
	 * Podrazumijevani konstrukor koji pokaziva�e first i last postavlja na <code>null</code>
	 * 
	 */
	public LinkedListIndexedCollection() {
		size = 0;
		first = last = null;

	}

	/**
	 * Konstruktor koji stvara Objekt tipa  <code>LinkedListIndexedCollection</code> i
	 * popunjava ga elementima iz dane kolekcije.
	 * 
	 * @param collection kolekcija kojom se popunjava novonastala kolekcija.
	 */
	public LinkedListIndexedCollection(Collection collection) {
		this();
		this.addAll(collection);
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

		ListNode listNode = new ListNode(value, null, last);
		if(size==0) {
			first=last=listNode;
			size++;
			return;
		}
		last.next=listNode;
		last=listNode;
		size++;

	}

	@Override
	public boolean contains(Object value) {
		for (var i = first; i !=null; i=i.next) {
			if(i.value.equals(value)) return true;
		}

		return false;
	}

	@Override
	public boolean remove(Object value) {	
		for (var i = first; i != null; i = i.next) {			
			if(i.value.equals(value)) {
				size--;

				ListNode next=i.next;
				ListNode previos=i.previos;
				if(size == 0) {
					first = last = null;
					return true;
				}

				if(next == null) {
					previos.next = null;
					last = previos;
					return true;
				}
				if(previos == null) {
					next.previos = null;
					first = next;
					return true;
				}

				next.previos=previos;
				previos.next=next;
				return true;
			}
		}

		return false;
	}

	@Override
	public Object[] toArray() {
		Object[] tmp=new Object[size];
		int index=0;
		for (var i = first; i !=null; i=i.next) {		
			tmp[index]=i.value;	
			index++;
		}

		return tmp;
	}

	@Override
	public void forEach(Processor processor) {
		for (var i = first; i !=null; i=i.next) {
			processor.process(i.value);
		}
	}

	@Override
	public void clear() {
		first=last=null;
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

		int curr_index;
		if(size/2>(index+1)) {
			curr_index=size-1;
			for(var i=last;i!=null;i=i.previos) {
				if(curr_index==index) return i.value;
				curr_index--;
			}
		}else {
			curr_index=0;
			for (var i = first; i !=null; i=i.next) {
				if(curr_index==index) return i.value;
				curr_index++;
			}
		}

		throw new IndexOutOfBoundsException("Neocekivana gre�ka! - index je pro�ao sve provjere, ali svejedno nije na�en element na tra�enom mjestu");
	}

	/**
	 * Dodava dani element na zadanu poziciju.
	 * @param value element koji se dodaje u kolekciju.
	 * @param position indeks na koji se dodaje element.
	 * @throws IndexOutOfBoundsException ako je zadana pozicija manji od 0 ili ve�i od trenutne veli�ine kolekcije+1
	 * @throws NullPointerException ako je argument value <code>null</code>
	 */
	public void insert(Object value, int position) {
		if(value==null) throw new NullPointerException("Argument value ne smije biti Null");

		if(position < 0) throw new IndexOutOfBoundsException("Index mora biti ve�i od 0, a unesen je "+position);
		if(position > size) 
			throw new IndexOutOfBoundsException("Index mora biti manji ili jedank od veli�ine trenute kolekcije ("
					+size+"), a unesen je"+position);

		// ako je pozicija dodavanja na kraj to je ekvivalntno metodi add
		if(position==size) {
			this.add(value);
			return;
		}

		this.size++;

		if(position == 0) {
			ListNode listNode = new ListNode(value,first,null);
			first.previos=listNode;
			first=listNode;
			return;
		}

		int curr_index;
		if(size/2>(position+1)) {
			curr_index=size-1;
			for(var i=last;i!=null;i=i.previos) {
				if(curr_index==position) {
					ListNode listNode = new ListNode(value, i, i.previos);
					i.previos.next=listNode;
					i.previos=listNode;
				}
				curr_index--;
			}
		}else {
			curr_index=0;
			for (var i = first; i !=null; i=i.next) {
				if(curr_index==position) {
					ListNode listNode = new ListNode(value, i, i.previos);
					i.previos.next=listNode;
					i.previos=listNode;
				}
				curr_index++;
			}
		}
	}

	/**
	 * Vra�a poziciju danog objekta u kolekciji. Ako se objekt ne nalazi u kolekciji program vra�a -1.
	 * 
	 * @param value objekt za koej se tra�i pozicija
	 * @return pozicija objekta, ako objekta nema onda -1.s
	 */
	public int indexOf(Object value) {
		int index=0;
		for (var i = first; i !=null; i=i.next) {
			if(i.value.equals(value)) return index;
			index++;
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

		int j = 0;
		for (var i = first; i != null; i = i.next) {			
			if(index == j) {
				size--;

				ListNode next=i.next;
				ListNode previos=i.previos;
				if(size == 0) {
					first = last = null;
					return;
				}

				if(next == null) {
					previos.next = null;
					last = previos;
					return;
				}
				if(previos == null) {
					next.previos = null;
					first = next;
					return;
				}

				next.previos=previos;
				previos.next=next;
				j++;
				return;
			}
		}
	}	
}
