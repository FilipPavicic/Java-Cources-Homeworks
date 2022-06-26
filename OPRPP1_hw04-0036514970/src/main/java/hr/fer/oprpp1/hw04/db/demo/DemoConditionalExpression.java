package hr.fer.oprpp1.hw04.db.demo;

import hr.fer.oprpp1.hw04.db.ComparisonOperators;
import hr.fer.oprpp1.hw04.db.ConditionalExpression;
import hr.fer.oprpp1.hw04.db.FieldValueGetters;
import hr.fer.oprpp1.hw04.db.IComparisonOperator;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class DemoConditionalExpression {
	public static void main(String[] args) {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.FIRST_NAME,
				"F*l*p",
				ComparisonOperators.LIKE
				);
		StudentRecord record = new StudentRecord("0012345678", "Pavičić", "Filip", 5);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), // returns lastName from given record
				expr.getStringLiteral() // returns "Bos*"
				);
		System.out.println(recordSatisfies);
	}
}
