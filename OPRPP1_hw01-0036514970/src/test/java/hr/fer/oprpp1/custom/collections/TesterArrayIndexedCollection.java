package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;




public class TesterArrayIndexedCollection {
	@Test
	public void testConstructorWithArgumentInitialCapacity() {
		int initialCapacity=5;
		Object[] expected=new Object[0];
		ArrayIndexedCollection result=new ArrayIndexedCollection(initialCapacity);
		assertArrayEquals(expected, result.toArray());
		assertEquals(0, result.size());
	}
	
	@Test
	public void testConstructorWithArgumentInitialCapacityIllegalArgumnet() {
		assertThrows(IllegalArgumentException.class,()->new ArrayIndexedCollection(0));
	}
	
	@Test
	public void testConstructorWithNoArgument() {
		Object[] expected=new Object[0];
		ArrayIndexedCollection result=new ArrayIndexedCollection();
		assertArrayEquals(expected, result.toArray());
		assertEquals(0, result.size());
	}
	
	@Test
	public void testMethodAdd() {
		var result=new ArrayIndexedCollection();
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		assertArrayEquals(expected, result.toArray());
		assertEquals(2, result.size());
	}
	
	@Test
	public void testMethodAddWhenArrayResized() {
		var result=new ArrayIndexedCollection(1);
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		assertArrayEquals(expected, result.toArray());
		assertEquals(2, result.size());
	}
	
	@Test
	public void testMethodAddArgumnetNull() {
		var result=new ArrayIndexedCollection(1);
		assertThrows(NullPointerException.class, ()->result.add(null));
	}
	
	@Test
	public void testMethodAddAll() {
		var coll=new ArrayIndexedCollection(1);
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		coll.add(expected[0]);
		coll.add(expected[1]);
		var result=new ArrayIndexedCollection();
		result.addAll(coll);
		assertArrayEquals(expected, result.toArray());
		assertEquals(2, result.size());
	}
	
	@Test
	public void testMethodAddAllFromLinkedListIndexedCollection() {
		var coll=new LinkedListIndexedCollection();
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		coll.add(expected[0]);
		coll.add(expected[1]);
		var result=new ArrayIndexedCollection();
		result.addAll(coll);
		assertArrayEquals(expected, result.toArray());
		assertEquals(2, result.size());
	}
	
	@Test
	public void testConstructorWithArgumentInitialCapacityAndCollection(){
		var coll=new ArrayIndexedCollection(1);
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		coll.add(expected[0]);
		coll.add(expected[1]);
		var result=new ArrayIndexedCollection(coll,5);
		assertArrayEquals(expected, result.toArray());
		assertEquals(2, result.size());
	}
	
	@Test
	public void testConstructorWithArgumentCollection(){
		var coll=new ArrayIndexedCollection(1);
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		coll.add(expected[0]);
		coll.add(expected[1]);
		var result=new ArrayIndexedCollection(coll);
		assertArrayEquals(expected, result.toArray());
		assertEquals(2, result.size());
	}
	
	@Test
	public void testMethodContains() {
		var result=new ArrayIndexedCollection(1);
		Object[] expected= new Object[16];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		assertEquals(true, result.contains("pero"));
		assertEquals(false, result.contains(4.7));
	}
	
	@Test
	public void testMethodGet() {
		var result=new ArrayIndexedCollection(1);
		Object[] expected= new Object[16];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		assertEquals("pero", result.get(0));
		assertEquals(4.78, result.get(1));
	}
	
	@Test
	public void testMethodRemove() {
		var result=new ArrayIndexedCollection(6);
		Object[] expected= new Object[6];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		assertEquals(true, result.remove("pero"));
		expected=new Object[1];
		expected[0]=4.78;
		assertArrayEquals(expected, result.toArray());
		assertEquals(1, result.size());
		assertEquals(false, result.remove("pero"));
		
	}
	
	@Test
	public void testMethodClear() {
		var result=new ArrayIndexedCollection(6);
		Object[] expected= new Object[6];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		result.clear();
		expected=new Object[0];
		assertArrayEquals(expected, result.toArray());
		assertEquals(0, result.size());
	}
	
	@Test
	public void testMethodGetIndexError() {
		var result=new ArrayIndexedCollection(1);
		Object[] expected= new Object[16];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		assertThrows(IndexOutOfBoundsException.class,()-> result.get(2));
		assertThrows(IndexOutOfBoundsException.class,()-> result.get(-1));
	}
	
	@Test
	public void testMethodInsertInTheMiddle() {
		var result=new ArrayIndexedCollection(6);
		Object[] expected= new Object[3];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		result.insert('c', 1);
		expected[0]="pero";
		expected[1]='c';
		expected[2]=4.78;
		assertArrayEquals(expected, result.toArray());
		assertEquals(3, result.size());
	}
	
	@Test
	public void testMethodInsertAtTheEnd() {
		var result=new ArrayIndexedCollection(6);
		Object[] expected= new Object[3];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		result.insert('c', 2);
		expected[0]="pero";
		expected[1]=4.78;
		expected[2]='c';
		assertArrayEquals(expected, result.toArray());
		assertEquals(3, result.size());
	}
	
	@Test
	public void testMethodInsertAtTheBeginning() {
		var result=new ArrayIndexedCollection(6);
		Object[] expected= new Object[3];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		result.insert('c', 0);
		expected[0]='c';
		expected[1]="pero";
		expected[2]=4.78;
		assertArrayEquals(expected, result.toArray());
		assertEquals(3, result.size());
	}
	
	@Test
	public void testMethodInsertArrayResized() {
		var result=new ArrayIndexedCollection(1);
		Object[] expected= new Object[2];
		expected[0]="pero";
		result.add(expected[0]);
		result.insert('c', 0);
		expected[0]='c';
		expected[1]="pero";
		assertArrayEquals(expected, result.toArray());
		assertEquals(2, result.size());
	}
	
	@Test
	public void testMethodInsertIndexError() {
		var result=new ArrayIndexedCollection(6);
		Object[] expected= new Object[6];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		assertThrows(IndexOutOfBoundsException.class,()-> result.insert('c', 3));
		assertThrows(IndexOutOfBoundsException.class,()-> result.insert('c', -1));
	}
	
	@Test void testMethodIndexOf() {
		var result=new ArrayIndexedCollection(6);
		Object[] expected= new Object[6];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		assertEquals(0, result.indexOf("pero"));
		assertEquals(1, result.indexOf(4.78));
		assertEquals(-1, result.indexOf("nisamUKolekciji"));
		
	}
	
	@Test
	public void testMethodRemoveAtIndex() {
		var result=new ArrayIndexedCollection(2);
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		result.remove(0);
		expected=new Object[1];
		expected[0]=4.78;
		assertArrayEquals(expected, result.toArray());
		assertEquals(1, result.size());	
	}
}
