package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.*;

public class Demo {

	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection(); // npr. new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		//System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println();
		
		col = new LinkedListIndexedCollection(); 
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		getter = col.createElementsGetter();
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		//System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println();
		
		col = new LinkedListIndexedCollection(); ; // npr. new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter1 = col.createElementsGetter();
		ElementsGetter getter2 = col.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());

		
		System.out.println();
		
		col =new ArrayIndexedCollection(); // npr. new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		getter1 = col.createElementsGetter();
		getter2 = col.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		
		System.out.println();
		
		Collection col1 = new ArrayIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");
		getter1 = col1.createElementsGetter();
		getter2 = col1.createElementsGetter();
		ElementsGetter getter3 = col2.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());

		System.out.println();
		
		col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		col.clear();
		//System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println();
		
		col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		col.clear();
		//System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println();
		
		col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		getter = col.createElementsGetter();
		getter.getNextElement();
		getter.processRemaining(System.out::println);
		
		System.out.println();
		
		col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		getter = col.createElementsGetter();
		getter.getNextElement();
		getter.processRemaining(System.out::println);
		
		System.out.println();

		class EvenIntegerTester implements Tester {
			 public boolean test(Object obj) {
				 if(!(obj instanceof Integer)) return false;
				 Integer i = (Integer)obj;
				 return i % 2 == 0;
			 }
		}
			
		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));

		System.out.println();
		
		col1 = new LinkedListIndexedCollection();
		col2 = new ArrayIndexedCollection();
		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		col2.add(12);
		col2.addAllSatisfying(col1, new EvenIntegerTester());
		col2.forEach(System.out::println);
		
		System.out.println();
		
		List col11 = new ArrayIndexedCollection();
		List col22 = new LinkedListIndexedCollection();
		col11.add("Ivana");
		col22.add("Jasna");
		Collection col3 = col11;
		Collection col4 = col22;
		col11.get(0);
		col22.get(0);
		//col3.get(0); // neće se prevesti! Razumijete li zašto? Zato što gledamo kroz naočale Collection koja ne zna za metodu get
		//col4.get(0); // neće se prevesti! Razumijete li zašto?
		col11.forEach(System.out::println); // Ivana
		col22.forEach(System.out::println); // Jasna
		col3.forEach(System.out::println); // Ivana
		col4.forEach(System.out::println); // Jasna

		
	}

}
