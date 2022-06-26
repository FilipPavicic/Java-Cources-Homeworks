package hr.fer.oprpp1.custom.collections;

/**
 * Razred koji predstavlja imlementaciju Mape-e
 * 
 * @author Filip Pavicic
 *
 * @param <K>
 * @param <V>
 */
public class Dictionary<K,V> {
	
	private static class Pair<K,V>{
		final K key;
		V value;
		
		public Pair(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof Pair))
				return false;
			Pair<?,?> other = (Pair<?,?>) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "Pair [key=" + key + ", value=" + value + "]";
		}
		
		
		
		
		
		
		
	}
	
	private ArrayIndexedCollection<Pair<K,V>> data;

	public Dictionary() {
		this.data = new ArrayIndexedCollection<Dictionary.Pair<K,V>>();
	}
	
	public boolean isEmpty() {
		return data.isEmpty();
	}
	
	public int size() {
		return data.size();
	}
	
	public void clear() {
		data.clear();
	}
	
	/**
	 * Dodaje zapis u Mapu.
	 * Ako zapis s istim klučem već postoji vrijednost toga zapisa se mijenja s novom vrijednosti.
	 * 
	 * @param key Ključ zapisa koji se dodaje
	 * @param value Vrijednost koja se dodaje kljuću
	 * @return <code>null</code> ako zapis s danim ključem nije postojao prije, inače staru vrijednost za dani ključ 
	 */
	public V put(K key, V value) {
		if(key == null) throw new NullPointerException("key ne može biti null");
		
		int indexOf = data.indexOf(new Pair<K, V>(key, value));
		
		if( indexOf == -1) {
			data.add(new Pair<K,V>(key, value));
			return null;
		}
		
		V oldValue = data.get(indexOf).getValue();
		data.get(indexOf).setValue(value);
		return oldValue;
	}
	
	/**
	 * Dohvaća vrijednost za dani ključ.
	 * 
	 * @param key Kluč
	 * @return <code>null</code> ako zapis s danim ključem ne postoji, inače vrijednost za dani ključ 
	 */
	public V get(Object key) {
		int indexOf = data.indexOf(new Pair<Object, V>(key,null));
		if(indexOf == -1 ) return null;
		return data.get(indexOf).getValue();
	}
	
	/**
	 * Briše element iz mape ako postoji.
	 * 
	 * @param key ključ elementa koji se briše.
	 * @return <code>null</code> ako zapis s danim ključem ne postoji, inače vrijednost za dani ključ  
	 */
	public V remove(K key) {

		int indexOf = data.indexOf(new Pair<K, V>(key,null));
		if(indexOf == -1 ) return null;
		V oldValue = data.get(indexOf).getValue();
		data.remove(indexOf);
		return oldValue;
	}

	@Override
	public String toString() {
		return "Dictionary [data=" + data + "]";
	}
	
	
	
	

}
