package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TesterLinkedListIndexedCollection {
	@Test
	public void testConstructorWithNoArgument() {
		LinkedListIndexedCollection result=new LinkedListIndexedCollection();
		assertEquals(0, result.size());
	}
	
	@Test
	public void testMethodAdd() {
		var result=new LinkedListIndexedCollection();
		Object[] expected= new Object[3];
		expected[0]="pero";
		expected[1]=4.78;
		expected[2]='c';
		result.add(expected[0]);
		result.add(expected[1]);
		result.add(expected[2]);
		assertArrayEquals(expected, result.toArray());
		assertEquals(3, result.size());
	}

	@Test
	public void testMethodAddArgumnetNull() {
		var result=new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, ()->result.add(null));
	}
	
	
	@Test
	public void testMethodAddAll() {
		var coll=new LinkedListIndexedCollection();
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		coll.add(expected[0]);
		coll.add(expected[1]);
		var result=new LinkedListIndexedCollection();
		result.addAll(coll);
		assertArrayEquals(expected, result.toArray());
		assertEquals(2, result.size());
	}
	
	@Test
	public void testMethodAddAllFromArrayIndexedCollection() {
		var coll=new ArrayIndexedCollection(2);
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		coll.add(expected[0]);
		coll.add(expected[1]);
		var result=new LinkedListIndexedCollection();
		result.addAll(coll);
		assertArrayEquals(expected, result.toArray());
		assertEquals(2, result.size());
	}
	
	@Test
	public void testConstructorWithArgumentLinkedListIndexedCollection(){
		var coll=new LinkedListIndexedCollection();
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		coll.add(expected[0]);
		coll.add(expected[1]);
		var result=new LinkedListIndexedCollection(coll);
		assertArrayEquals(expected, result.toArray());
		assertEquals(2, result.size());
	}
	
	@Test
	public void testConstructorWithArgumentArrayIndexedCollection(){
		var coll=new ArrayIndexedCollection(4);
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		coll.add(expected[0]);
		coll.add(expected[1]);
		var result=new LinkedListIndexedCollection(coll);
		assertArrayEquals(expected, result.toArray());
		assertEquals(2, result.size());
	}

	@Test
	public void testMethodContains() {
		var result=new LinkedListIndexedCollection();
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		assertEquals(true, result.contains("pero"));
		assertEquals(false, result.contains(4.7));
	}
	
	@Test
	public void testMethodGet() {
		var result=new LinkedListIndexedCollection();
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		assertEquals("pero", result.get(0));
		assertEquals(4.78, result.get(1));
	}
	
	@Test
	public void testMethodRemove() {
		var result=new LinkedListIndexedCollection();
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		assertEquals(true, result.remove("pero"));
		expected= new Object[1];
		expected[0]=4.78;
		assertArrayEquals(expected, result.toArray());
		assertEquals(1, result.size());
		assertEquals(false, result.remove("pero"));
		
	}
	
	@Test
	public void testMethodRemoveAllElements() {
		var result=new LinkedListIndexedCollection();
		Object[] expected= new Object[3];
		expected[0]="pero";
		expected[1]='c';
		expected[2]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		result.add(expected[2]);
		assertEquals(true, result.remove(Character.valueOf('c')));
		assertEquals(2, result.size());
		assertEquals(true, result.remove("pero"));
		assertEquals(1, result.size());
		assertEquals(true, result.remove(4.78));
		expected= new Object[0];
		assertArrayEquals(expected, result.toArray());
		assertEquals(0, result.size());
		
	}
	
	
	@Test
	public void testMethodClear() {
		var result=new LinkedListIndexedCollection();
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
		var result=new LinkedListIndexedCollection();
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
		var result=new LinkedListIndexedCollection();
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
		var result=new LinkedListIndexedCollection();
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
		var result=new LinkedListIndexedCollection();
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
	public void testMethodInsertIndexError() {
		var result=new LinkedListIndexedCollection();
		Object[] expected= new Object[2];
		expected[0]="pero";
		expected[1]=4.78;
		result.add(expected[0]);
		result.add(expected[1]);
		assertThrows(IndexOutOfBoundsException.class,()-> result.insert('c', 3));
		assertThrows(IndexOutOfBoundsException.class,()-> result.insert('c', -1));
	}
	
	@Test void testMethodIndexOf() {
		var result=new LinkedListIndexedCollection();
		Object[] expected= new Object[2];
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
		var result=new LinkedListIndexedCollection();
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
