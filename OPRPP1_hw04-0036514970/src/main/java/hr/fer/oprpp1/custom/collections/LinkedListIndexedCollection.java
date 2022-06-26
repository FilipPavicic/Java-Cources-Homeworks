package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;

/**
 * Razred predstavlja dvostruko povezanu listu koja ima pokazivač na početak i na kraj liste.
 * Dopuštene su duplicirane vrijednosti.
 * Nisu dopušteni <code>Null</code> reference.
 * 
 * @author Filip Pavicic
 *
 */
  class LinkedListIndexedCollection<E> implements List<E>{

	/**
	 * Razred predstavlja čvor koji se korsiti u razredu LinkedListIndexedCollection. 
	 * Svaki čvor sadrži pokazivač na sljedeći i prethodni element.
	 * 
	 * @author Filip Pavicic
	 *
	 */
	private static class ListNode<E> {
		E value;
		ListNode<E> next;
		ListNode<E> previos;

		/**
		 * Konstruktor za stvaranje <code>ListNode</code> čvora
		 * 
		 * @param value vrijednost koja se zapisuje u čvor
		 * @param next pokazivač na sljedeći element na kojeg će pokazavati novonastali čvor
		 * @param previos pokazivač na prethodni element na kojeg će pokazavati novonastali čvor
		 */
		public ListNode(E value, ListNode<E> next, ListNode<E> previos) {
			this.value = value;
			this.next = next;
			this.previos = previos;
		}


	}

	private int size;
	private ListNode<E> first;
	private ListNode<E> last;
	private long modificationCount = 0;

	/**
	 * Podrazumijevani konstrukor koji pokazivaće first i last postavlja na <code>null</code>
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
	public LinkedListIndexedCollection(Collection<? extends E> collection) {
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
	public void add(E value) {
		if(value==null) throw new NullPointerException("Argument value ne smije biti Null");

		ListNode<E> listNode = new ListNode<E>(value, null, last);
		if(size==0) {
			first=last=listNode;
			size++;
			return;
		}
		last.next=listNode;
		last=listNode;
		size++;
		modificationCount++;

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
				modificationCount++;

				ListNode<E> next=i.next;
				ListNode<E> previos=i.previos;
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
	public void clear() {
		first=last=null;
		modificationCount++;
		this.size=0;
	}

	public E get(int index) {
		if(index < 0) throw new IndexOutOfBoundsException("Index mora biti veći od 0, a unesen je "+index);
		if(index >= size) 
			throw new IndexOutOfBoundsException("Index mora biti manji od veličine trenute kolekcije "
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

		throw new IndexOutOfBoundsException("Neocekivana greška! - index je prošao sve provjere, ali svejedno nije nađen element na traženom mjestu");
	}

	public void insert(E value, int position) {
		if(value==null) throw new NullPointerException("Argument value ne smije biti Null");

		if(position < 0) throw new IndexOutOfBoundsException("Index mora biti veći od 0, a unesen je "+position);
		if(position > size) 
			throw new IndexOutOfBoundsException("Index mora biti manji ili jedank od veličine trenute kolekcije ("
					+size+"), a unesen je"+position);

		// ako je pozicija dodavanja na kraj to je ekvivalntno metodi add
		if(position==size) {
			this.add(value);
			return;
		}

		this.size++;

		if(position == 0) {
			ListNode<E> listNode = new ListNode<E>(value,first,null);
			first.previos=listNode;
			first=listNode;
			return;
		}

		int curr_index;
		if(size/2>(position+1)) {
			curr_index=size-1;
			for(var i=last;i!=null;i=i.previos) {
				if(curr_index==position) {
					ListNode<E> listNode = new ListNode<E>(value, i, i.previos);
					i.previos.next=listNode;
					i.previos=listNode;
				}
				curr_index--;
			}
		}else {
			curr_index=0;
			for (var i = first; i !=null; i=i.next) {
				if(curr_index==position) {
					ListNode<E> listNode = new ListNode<E>(value, i, i.previos);
					i.previos.next=listNode;
					i.previos=listNode;
				}
				curr_index++;
			}
		}
		modificationCount++;
	}

	public int indexOf(Object value) {
		int index=0;
		for (var i = first; i !=null; i=i.next) {
			if(i.value.equals(value)) return index;
			index++;
		}

		return -1;
	}

	public void remove(int index) {
		if(index < 0) throw new IndexOutOfBoundsException("Index mora biti veći od 0, a unesen je "+index);
		if(index >= size) 
			throw new IndexOutOfBoundsException("Index mora biti manji od veličine trenute kolekcije "
					+size+", a unesen je"+index);

		int j = 0;
		for (var i = first; i != null; i = i.next) {			
			if(index == j) {
				size--;
				modificationCount++;

				ListNode<E> next=i.next;
				ListNode<E> previos=i.previos;
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
	
	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new LinkedListIndexedCollectionElementsGetter();
	}
	
	private class LinkedListIndexedCollectionElementsGetter implements ElementsGetter<E>{
		
		private ListNode<E> position;
		private long savedModificationCount;
		
		public LinkedListIndexedCollectionElementsGetter() {
			position = first;
			savedModificationCount = modificationCount;
		}
		
		/**
		 * 
		 * @throws ConcurrentModificationException iznimka se baca ako se kolekcija strukturalno promijenila od poziva metode <code>createElementsGetter()</code>
		 */
		@Override
		public boolean hasNextElement() {
			isChange();
			
			return position != null ? true : false;
		}
		
		/**
		 * 
		 * @throws ConcurrentModificationException iznimka se baca ako se kolekcija strukturalno promijenila od poziva metode <code>createElementsGetter()</code>
		 */
		@Override
		public E getNextElement() {
			isChange();
			if(!hasNextElement()) ElementsGetter.super.getNextElement();
			
			E object = position.value;
			position = position.next;
			return object;
		}
		
		private void isChange() {
			if(savedModificationCount != modificationCount) throw new ConcurrentModificationException(
					"Kolekcija se promjenila od poziva metode createElementsGetter()");
		}
		
	}
}
