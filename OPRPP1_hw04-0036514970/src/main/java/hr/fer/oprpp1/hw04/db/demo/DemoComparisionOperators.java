package hr.fer.oprpp1.hw04.db.demo;

import hr.fer.oprpp1.hw04.db.ComparisonOperators;
import hr.fer.oprpp1.hw04.db.IComparisonOperator;

public class DemoComparisionOperators {
	public static void main(String[] args) {
		IComparisonOperator oper = ComparisonOperators.LESS;
		System.out.println(oper.satisfied("Ana", "Jasna")); // true, since Ana < Jasna
		
		System.out.println();
		
		oper = ComparisonOperators.LIKE;
		System.out.println(oper.satisfied("Zagreb", "Aba*")); // false
		System.out.println(oper.satisfied("AAA", "AA*AA")); // false
		System.out.println(oper.satisfied("AAAA", "AA*AA")); // true


	}
}
