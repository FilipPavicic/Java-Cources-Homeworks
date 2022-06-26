package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Razred predstavlja implementaciju jednostavne Hash mape
 * 
 * 
 * @author Filip Pavicic
 *
 * @param <K> KLjuč ne smije biti null
 * @param <V> Vrijednost smije biti null
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{

	/**
	 * Razred predstvlja Čvor koji se sprema u Hash tablicu
	 * te ima pokazivac na sljedeci element u retku hash tablice
	 * 
	 * @author Filip Pavicic
	 *
	 * @param <K> Ključ
	 * @param <V> Vrijednost
	 */
	public static class  TableEntry<K,V> {
		K key;
		V value;
		TableEntry<K, V> next;

		public TableEntry(K key, V value, TableEntry<K, V> next) {
			super();
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			if (obj instanceof TableEntry) {
				TableEntry<?,?> e = (TableEntry<?,?>)obj;
				if (Objects.equals(key, e.getKey()) &&
						Objects.equals(value, e.getValue()))
					return true;
			}
			return false;
		}

	}

	TableEntry<K,V>[] table;
	int size;
	long modificationCount;

	@SuppressWarnings("unchecked")
	public SimpleHashtable(int size) {
		if(size < 1) throw new IllegalArgumentException("Veličina mora biti barem 1");

		double  log2Size = (Math.log(size * 1.0) / Math.log(2.0));
		if (Math.abs(log2Size - (int) log2Size) > 0.001 ) size =  (int ) Math.pow(2,(int) log2Size +1); 
		this.size = 0;
		table = (TableEntry<K, V>[]) new TableEntry[size];
		modificationCount = 0;
	}

	public SimpleHashtable() {
		this(16);
	}

	/**
	 * Dodaje element u mapu, ako element s danim ključem
	 * potojii ažurira se njegova vrijednost.
	 * 
	 * @param key
	 * @param value
	 * @return <code>null</code> ako nije postojao element s danim ključem, inače stara vrijednost za dani kključ.
	 */
	public V put(K key, V value) {
		if(key == null) throw new NullPointerException("key ne može biti null");
		
		checkIfOptimal();
		
		int position = Math.abs(key.hashCode()) % table.length;

		if(table[position] == null) {
			table[position] = new TableEntry<K, V>(key, value, null);
			size++;
			return null;
		}
		TableEntry<K, V> previous = null;
		for(TableEntry<K, V> i = table[position]; i != null; i=i.next) {
			if(i.getKey().equals(key)) {
				V oldElement = i.getValue();
				i.setValue(value);
				return oldElement;
			}
			previous = i;
		}
		
		modificationCount++;
		previous.next =  new TableEntry<K, V>(key, value, null);
		size++;
		return null;
	}

	/**
	 * Dohvaća element za traženi ključ
	 * ako element s traženim ključem ne postoji vraća null
	 * @param key
	 * @return Vrijednsot da traženi ključ,inače <code>null</code>
	 */
	public V get(Object key) {
		if(key == null) return null;
		int position = Math.abs(key.hashCode()) % table.length;
		V oldElement = null;
		for(TableEntry<K, V> i = table[position]; i != null; i=i.next) {
			if(i.getKey().equals(key)) {
				oldElement = i.getValue();
				break;
			}
		}
		return oldElement;
	}

	/**
	 * Vraca btoj elemenata u mapi
	 * 
	 * @return
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Ipituje postoji li element u mapi s danim ključem
	 * @param key
	 * @return <code>true</code> ako postoji, inače <code>false</code>
	 */
	public boolean containsKey(Object key) {
		if(key == null) throw new NullPointerException("key ne može biti null");

		int position = Math.abs(key.hashCode()) % table.length;
		for(TableEntry<K, V> i = table[position]; i != null; i=i.next) {
			if(i.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Ipituje postoji li element u mapi s danom vrijednosti
	 * 
	 * @param value
	 * @return <code>true</code> ako postoji, inače <code>false</code>
	 */
	public boolean containsValue(Object value) {
		if(value == null) return false;

		for (TableEntry<K, V> tableEntry : table) {
			for(TableEntry<K, V> i = tableEntry; i != null; i=i.next) {
				if(i.getValue().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Briše element iz list ako postoji.
	 * 
	 * @param key
	 * @return vrijednsot elementa ako je uspješno obrisan, inače <code>null</code>
	 */
	public V remove(Object key) {
		if(key == null) return null;

		int position = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> last = null;
		for(TableEntry<K, V> i = table[position]; i != null; i=i.next) {
			if(i.getKey().equals(key)) {
				V oldValue = i.getValue();
				modificationCount++;
				if(last == null) table[position] = i.next;
				else last.next= i.next;
				size--;
				return oldValue;
			}
			last = i;
		}
		return null;
	}

	/**
	 * Ispituje li da li je mapa prazna.
	 * 
	 * @return true ako je mapa prazna, unače false
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	public String toString() {
		String string = "[";
		for (TableEntry<K, V> tableEntry : table) {
			for(TableEntry<K, V> i = tableEntry; i != null; i=i.next) {
				string += i.toString();
				string += ", ";	
			}
		}
		string = string.substring(0, string.length()-2);
		string +="]";
		return string;
	}

	/**
	 * HashMapu pretvara u polje Čvorova.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray(){
		TableEntry<K,V>[] array = (TableEntry<K, V>[]) new TableEntry[size];
		int index = 0;
		for (TableEntry<K, V> tableEntry : table) {
			for(TableEntry<K, V> i = tableEntry; i != null; i=i.next) {
				array[index] = i;
				index++;
			}
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	private void checkIfOptimal() {
		if((1 + size * 1.0) / table.length < 0.75) return;

		TableEntry<K, V>[] tmp  = this.toArray();
		int newTableLenght = 2 * table.length;
		size = 0;
		table = (TableEntry<K, V>[]) new TableEntry[newTableLenght];
		for (TableEntry<K, V> tableEntry : tmp) {
			this.put(tableEntry.getKey(), tableEntry.getValue());
		}

	}

	/**
	 * Metoda namjenjena samo za testove, nakon testiranja ju ukloniti
	 * 
	 * @return
	 */
	public  double getOptimalLevel() {
		return size * 1.0 / table.length ;
	}
	/**
	 * Brise sve elemente iz mape
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		int newTableLenght = table.length;
		size = 0;
		table = (TableEntry<K, V>[]) new TableEntry[newTableLenght];
	}

	private class IteratorImpl implements Iterator<TableEntry<K,V>> {
		int partition;
		TableEntry<K, V> current;
		TableEntry<K, V> next;
		long expectedModificationCount;
		
		

		public IteratorImpl() {
			expectedModificationCount = modificationCount;
			partition = 0;
			current = null;
			next = findNext();
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public TableEntry<K, V> next() {
            if (modificationCount != expectedModificationCount)
                throw new ConcurrentModificationException();
            if (next == null)
                throw new NoSuchElementException();
            current = next;
            next = null;
            if(current.next != null) 
            	next = current.next;
			else {
				partition++;
				next = findNext();
			}
            return current; // prvo ažuriram current i onda ga vraćam to jest vraćam iz prošlog next 
		}
				
		
		@Override
		public void remove() {
            if (current == null)
                throw new IllegalStateException();
            if (modificationCount != expectedModificationCount)
                throw new ConcurrentModificationException();
            SimpleHashtable.this.remove(current.key);
            current = null;
            expectedModificationCount = modificationCount;
		}
		
		private TableEntry<K, V> findNext() {
			TableEntry<K, V> next = null;
			for(;partition < table.length; partition++) {
				if(table[partition] != null) {
					next = table[partition];
					break;
				}
			}
			return next;
		}
		
	}
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
}
